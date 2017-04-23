package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;

public class AverageVehicleCountReportTest {
	Class<?>	cls;
	Object		obj;
	
	/**
	 * Since createDataRecord method is private, we have to use reflection to
	 * invoke the same from test class.
	 */
	@BeforeClass
	public void beforeClass() {
		try {
			cls = Class.forName("com.citygovernment.vehiclesurvey.analyser.display.report.AverageVehicleCountReport");
			obj = cls.newInstance();
		} catch (Exception e) {
			Reporter.log("Could not perform operations to be done before tests in AverageVehicleCountReportTest.");
		}
	}
	
	/**
	 * Tests average counting of vehicles.
	 * 
	 * @param vehiclesList
	 *            List of vehicles.
	 * @param count
	 *            vehicle count.
	 */
	@Test(dataProvider = "findAverageCountTestData", enabled = true)
	public void findAverageCount(List<Vehicle> vehiclesList, int numberOfDays, long actualcount) {
		try {
			Method method = cls.getDeclaredMethod("findAverageCount", List.class,int.class);
			method.setAccessible(true);
			long averageCountOfVehicles = (long) method.invoke(obj, vehiclesList, numberOfDays);
			
			Reporter.log("<br/>Verify average count with actual - "+actualcount, true);
			Assert.assertEquals(averageCountOfVehicles, actualcount);
		} catch (Exception e) {
			Reporter.log(e.getStackTrace().toString(), true);
			e.printStackTrace();
		}
	}
	
	/**
	 * Few sample vehicle lists and their actual counts.
	 * 
	 * @return Object[][] Test Data.
	 */
	@DataProvider
	public Object[][] findAverageCountTestData() {
		ArrayList<Vehicle> vehicleList1 = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			vehicleList1.add(new Vehicle());
		}
		
		ArrayList<Vehicle> vehicleList2 = new ArrayList<>();
		for (int i = 0; i < 25; i++) {
			vehicleList2.add(new Vehicle());
		}
		
		ArrayList<Vehicle> vehicleList3 = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			vehicleList3.add(new Vehicle());
		}
		return new Object[][] {
				new Object[] {
						vehicleList1, 1, 4
				}, {
						vehicleList2, 1, 25
				}, {
						vehicleList3, 1, 100
				}, {
						vehicleList1, 2, 2
				}, {
						vehicleList2, 2, 12
				}, {
						vehicleList3, 4, 25
				}, {
						vehicleList3, 5, 20
				}
		};
	}
}
