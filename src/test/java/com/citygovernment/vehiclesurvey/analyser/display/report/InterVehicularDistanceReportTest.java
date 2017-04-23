package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.citygovernment.vehiclesurvey.analyser.analysis.Direction;
import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;

public class InterVehicularDistanceReportTest {
	
	Class<?>	cls;
	Object		obj;
	
	/**
	 * Since createDataRecord method is private, we have to use reflection to
	 * invoke the same from test class.
	 */
	@BeforeClass
	public void beforeClass() {
		try {
			cls = Class.forName("com.citygovernment.vehiclesurvey.analyser.display.report.InterVehicularDistanceReport");
			obj = cls.newInstance();
		} catch (Exception e) {
			Reporter.log("Could not perform operations to be done before tests in InterVehicularDistanceReportTest.");
		}
	}
	
	/**
	 * Tests calculation of distances between vehicles.
	 * 
	 * @param vehiclesList
	 *            List of vehicles.
	 * @param actualDistances
	 *            Actual distances.
	 */
	@Test(dataProvider = "calculateDistancesTestData", enabled = true)
	public void calculateDistances(List<Vehicle> vehiclesList, ArrayList<Double> actualDistances) {
		try {
			Method method = cls.getDeclaredMethod("calculateDistances", List.class);
			method.setAccessible(true);
			ArrayList<Double> distances = (ArrayList<Double>) method.invoke(obj, vehiclesList);
			
			Reporter.log("<br/>Verify distances calculated with actual - " + actualDistances, true);
			Assert.assertEquals(distances, actualDistances);
		} catch (Exception e) {
			Reporter.log(e.getStackTrace().toString(), true);
			e.printStackTrace();
		}
	}
	
	/**
	 * Few sample vehicle lists and the actual distances between adjacent
	 * vehicles.
	 * 
	 * @return Object[][] Test Data.
	 */
	@DataProvider
	public Object[][] calculateDistancesTestData() {
		ArrayList<Vehicle> vehicleList1 = new ArrayList<>();
		Vehicle vehicle1 = new Vehicle();
		vehicle1.setDirection(Direction.NORTH);
		vehicle1.setPassingTime(LocalTime.NOON);
		vehicle1.setSpeed(60f);
		vehicleList1.add(vehicle1);
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setDirection(Direction.NORTH);
		vehicle2.setPassingTime(LocalTime.NOON.plusHours(1));
		vehicle2.setSpeed(70f);
		vehicleList1.add(vehicle2);
		Vehicle vehicle3 = new Vehicle();
		vehicle3.setDirection(Direction.NORTH);
		vehicle3.setPassingTime(LocalTime.NOON.plusHours(2).plusMinutes(30));
		vehicle3.setSpeed(80f);
		vehicleList1.add(vehicle3);
		ArrayList<Double> distanceList = new ArrayList<>();
		distanceList.add(60d);
		distanceList.add(105d);
		
		return new Object[][] {
				new Object[] {
						vehicleList1, distanceList
				}
		};
	}
	
	/**
	 * Tests calculation of average of distances between vehicles.
	 * 
	 * @param distancesList
	 *            List of distances between vehicles.
	 * @param actualAverage
	 *            Actual average.
	 */
	@Test(dataProvider = "findAverageTestData", enabled = true)
	public void findAverage(ArrayList<Double> distancesList, Double actualAverage) {
		try {
			Method method = cls.getDeclaredMethod("findAverage", ArrayList.class);
			method.setAccessible(true);
			
			Double average = (Double) method.invoke(obj, distancesList);
			
			Reporter.log("<br/>Verify average with actual - " + actualAverage, true);
			Assert.assertEquals(average, actualAverage);
		} catch (Exception e) {
			Reporter.log(e.getStackTrace().toString(), true);
			e.printStackTrace();
		}
	}
	
	/**
	 * Few sample vehicle lists and the actual distances between adjacent
	 * vehicles.
	 * 
	 * @return Object[][] Test Data.
	 */
	@DataProvider
	public Object[][] findAverageTestData() {
		ArrayList<Double> distancesList1 = new ArrayList<>();
		distancesList1.add(10d);
		distancesList1.add(20d);
		distancesList1.add(30d);
		
		ArrayList<Double> distancesList2 = new ArrayList<>();
		distancesList2.add(1d);
		distancesList2.add(2d);
		distancesList2.add(3d);
		distancesList2.add(4d);
		distancesList2.add(7d);
		return new Object[][] {
				new Object[] {
						distancesList1, 20d
				}, {
						distancesList2, 3.4d
				}
		};
		
	}
}
