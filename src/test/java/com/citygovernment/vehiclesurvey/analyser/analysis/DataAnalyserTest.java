package com.citygovernment.vehiclesurvey.analyser.analysis;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.citygovernment.vehiclesurvey.analyser.data.Sensor;
import com.citygovernment.vehiclesurvey.analyser.data.SensorData;
import com.citygovernment.vehiclesurvey.analyser.data.SensorDataRecord;

public class DataAnalyserTest {
	
	static final SensorData sensorData = new SensorData();
	
	@BeforeClass
	public void beforeClass() {
		List<SensorDataRecord> testData = Arrays.asList(
				new SensorDataRecord(Sensor.A, LocalTime.MIDNIGHT.plus(268981, ChronoUnit.MILLIS)),
				new SensorDataRecord(Sensor.A, LocalTime.MIDNIGHT.plus(269123, ChronoUnit.MILLIS)), 
				new SensorDataRecord(Sensor.A, LocalTime.MIDNIGHT.plus(604957, ChronoUnit.MILLIS)),
				new SensorDataRecord(Sensor.B, LocalTime.MIDNIGHT.plus(604960, ChronoUnit.MILLIS)), 
				new SensorDataRecord(Sensor.A, LocalTime.MIDNIGHT.plus(605128, ChronoUnit.MILLIS)),
				new SensorDataRecord(Sensor.B, LocalTime.MIDNIGHT.plus(605132, ChronoUnit.MILLIS))
				);
		sensorData.getDataRecordList().addAll(testData);		
	}
	
	@Test(enabled = true)
	public void analyse() {
		DataAnalyser dataAnalyser = new DataAnalyser();
		Analysis analysis = dataAnalyser.analyse(sensorData);
		Reporter.log("<br/>Test Data = "+ sensorData+"<br/>", true);
		
		Reporter.log("<br/>Verify that result contains analysis for only one day.", true);
		Assert.assertEquals(analysis.getDailyAnalysisList().size(), 1);
		ArrayList<Vehicle> vehicleList = analysis.getDailyAnalysisList().get(0).getVehiclesPassed();
		Reporter.log("<br/>Verify that there are two vehicles identified for that day", true);
		Assert.assertEquals(vehicleList.size(), 2);
		Reporter.log("<br/>Verify that the first vehicle is northbound", true);
		Assert.assertEquals(vehicleList.get(0).getDirection(), Direction.NORTH);
		Reporter.log("<br/>Verify that the second vehicle is southbound", true);
		Assert.assertEquals(vehicleList.get(1).getDirection(), Direction.SOUTH);
		Reporter.log("<br/>Average Speed can be calculated by using the formula: V = D/T", true);
		Reporter.log("<br/>Verify that speed of first vehicle is (2.5m/142ms)*3600 = 63.38 KM/hr.", true);
		Assert.assertEquals(vehicleList.get(0).getSpeed(), 63.38f);
		Reporter.log("<br/>Verify that speed of second vehicle is (2.5m/172ms)*3600 = 52.33 KM/hr.", true);
		Assert.assertEquals(vehicleList.get(1).getSpeed(), 52.33f);
	}
	
}
