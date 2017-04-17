package com.citygovernment.vehiclesurvey.analyser;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.DataAnalyser;
import com.citygovernment.vehiclesurvey.analyser.analysis.Direction;
import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;
import com.citygovernment.vehiclesurvey.analyser.data.SensorData;

public class ApplicationTest {
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
	 * This method tests whether the application differentiates data of
	 * different days
	 * 
	 * @param strDataMultipleDays
	 */
	@Test(dataProvider = "sampleDataWithTimeDropAfterDay")
	public void testGroupingDataIntoDays(Stream<String> stream) {
		try {
			Method method = cls.getDeclaredMethod("parseDataStream", Stream.class);
			method.setAccessible(true);
			
			SensorData sensorData = (SensorData) method.invoke(obj, stream);
			
			Analysis analysisResult = new DataAnalyser().analyse(sensorData);
			//Assert that there are two days identified
			Assert.assertEquals(analysisResult.getDailyAnalysisList().size(), 2);
			
		} catch (Exception e) {
			Reporter.log(e.getStackTrace().toString(), true);
		}
	}
	
	/**
	 * This method tests the following scenario:-
	 * 
	 * 
	 * The first line above represents a pair of tires driving by at 12:04:28am.
	 * 
	 * The second line represents another pair of tires going by 142ms later
	 * (almost certainly the 2nd axle of the car).
	 * 
	 * Lines 3-6 above represent a second car going in the other direction.
	 * 
	 * The first set of tires hit the 'A' sensor at 12:10:04am, and then hit the
	 * 'B' sensor 3ms later.
	 * 
	 * The second set of tires then hit the 'A' sensor 171ms later, and then the
	 * 'B' sensor 4ms later.
	 * 
	 * @param strData
	 */
	@Test(dataProvider = "sampleLines", enabled =false)
	public void testSampleDataInterpretation(Stream<String> stream) {
		try {
			Method method = cls.getDeclaredMethod("parseDataStream", Stream.class);
			method.setAccessible(true);
			
			SensorData sensorData = (SensorData) method.invoke(obj, stream);
			
			Analysis analysisResult = new DataAnalyser().analyse(sensorData);
			
			ArrayList<Vehicle> vehicleList = analysisResult.getDailyAnalysisList().get(0).getVehiclesPassed();
			
			// Assert that there were two vehicles
			Assert.assertEquals(vehicleList.size(), 2);
			// Assert that the direction of first vehicle was North
			Assert.assertEquals(vehicleList.get(0).getDirection(), Direction.NORTH);
			// Assert that the direction of second vehicle was South
			Assert.assertEquals(vehicleList.get(1).getDirection(), Direction.SOUTH);
			
		} catch (Exception e) {
			Reporter.log(e.getStackTrace().toString(), true);
		}
	}
	
	@DataProvider
	public Object[][] sampleLines() {
		return new Object[][] {
				new Object[] {
						Arrays.asList("A268981", "A269123", "A604957", "B604960", "A605128", "B605132", "A1089807", "B1089810", "A1089948", "B1089951").stream()
				}
		};
	}
	
	@DataProvider
	public Object[][] sampleDataWithTimeDropAfterDay() {
		return new Object[][] {
				new Object[] {
						
						Arrays.asList("A86328771", "B86328774", "A86328899", "B86328902", "A582668", "B582671", "A582787", "B582789").stream()
				}
		};
	}
	
}
