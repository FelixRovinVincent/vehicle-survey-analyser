package com.citygovernment.vehiclesurvey.analyser.parser;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.citygovernment.vehiclesurvey.analyser.data.Sensor;
import com.citygovernment.vehiclesurvey.analyser.data.SensorData;
import com.citygovernment.vehiclesurvey.analyser.data.SensorDataRecord;

public class PneumaticDataFileParserTest {
	
	Class<?>	cls;
	Object		obj;
	
	/**
	 * Since createDataRecord method is private, we have to use reflection to
	 * invoke the same from test class.
	 */
	@BeforeClass
	public void beforeClass() {
		try {
			cls = Class.forName("com.citygovernment.vehiclesurvey.analyser.parser.PneumaticDataFileParser");
			obj = cls.newInstance();
		} catch (Exception e) {
			Reporter.log("Could not perform operations to be done before tests in PneumaticDataFileParserTest.");
		}
	}
	
	/**
	 * Data records mentioned in Problem statement with time.
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] sampleLinesWithTime() {
		return new Object[][] {
				new Object[] {
						"A268981", new SensorDataRecord(Sensor.A, LocalTime.of(00, 04, 28))
				}, {
						"A604957", new SensorDataRecord(Sensor.A, LocalTime.parse("00:10:04"))
				}
		};
	}
	
	/**
	 * Tests if parsing of each line is done correctly.
	 * 
	 * @param line
	 *            String data to be parsed.
	 * @param sensorDataRecord
	 *            Parsed data to record instance
	 */
	@Test(dataProvider = "sampleLinesWithTime", enabled = true)
	public void createDataRecord(String line, SensorDataRecord sensorDataRecord) {
		try {
			Method method = cls.getDeclaredMethod("createDataRecord", String.class);
			method.setAccessible(true);
			SensorDataRecord parsedLineAsRecord = (SensorDataRecord) method.invoke(obj, line);
			
			Assert.assertEquals(parsedLineAsRecord.getSensor(), sensorDataRecord.getSensor());
			Assert.assertEquals(parsedLineAsRecord.getLocalTime().getHour(), sensorDataRecord.getLocalTime().getHour());
			Assert.assertEquals(parsedLineAsRecord.getLocalTime().getMinute(), sensorDataRecord.getLocalTime().getMinute());
			Assert.assertEquals(parsedLineAsRecord.getLocalTime().getSecond(), sensorDataRecord.getLocalTime().getSecond());
			
		} catch (Exception e) {
			Reporter.log(e.getStackTrace().toString(), true);
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests if parsing a stream of multiple lines works as expected.
	 * 
	 * @param stream
	 */
	@Test(dataProvider = "sampleStream")
	public void parseDataStream(Stream<String> stream) {
		try {
			Method method = cls.getDeclaredMethod("parseDataStream", Stream.class);
			method.setAccessible(true);
			SensorData parsedStream = (SensorData) method.invoke(obj, stream);
			
			Assert.assertEquals(parsedStream.getDataRecordList().get(1).getSensor(), Sensor.A);
			Assert.assertEquals(parsedStream.getDataRecordList().get(4).getSensor(), Sensor.B);
			
		} catch (Exception e) {
			Reporter.log(e.getStackTrace().toString(), true);
			e.printStackTrace();
		}
	}
	
	@DataProvider
	public Object[][] sampleStream() throws IOException {
		return new Object[][] {
				new Object[] {
						Arrays.asList("A269123", "A604957", "B604960", "A605128", "B605132", "A1089807", "B1089810", "A1089948", "B1089951").stream()
				}
		};
	}
}
