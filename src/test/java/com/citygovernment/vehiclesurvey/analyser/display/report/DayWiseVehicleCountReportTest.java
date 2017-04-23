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

public class DayWiseVehicleCountReportTest {
	Class<?>	cls;
	Object		obj;
	
	/**
	 * Since createDataRecord method is private, we have to use reflection to
	 * invoke the same from test class.
	 */
	@BeforeClass
	public void beforeClass() {
		try {
			cls = Class.forName("com.citygovernment.vehiclesurvey.analyser.display.report.DayWiseVehicleCountReport");
			obj = cls.newInstance();
		} catch (Exception e) {
			Reporter.log("Could not perform operations to be done before tests in DayWiseVehicleCountReportTest.");
		}
	}
	
	/**
	 * Tests counting of vehicles.
	 * 
	 * @param vehiclesList
	 *            List of vehicles.
	 * @param count
	 *            vehicle count.
	 */
	@Test(dataProvider = "findCountTestData", enabled = true)
	public void findCount(List<Vehicle> vehiclesList, long actualcount) {
		try {
			Method method = cls.getDeclaredMethod("findCount", List.class);
			method.setAccessible(true);
			long countOfVehicles = (long) method.invoke(obj, vehiclesList);
			
			Reporter.log("<br/>Verify count with actual.", true);
			Assert.assertEquals(countOfVehicles, actualcount);
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
	public Object[][] findCountTestData() {		
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
						vehicleList1, 4
				}, {
						vehicleList2, 25
				}, {
					vehicleList3, 100
			}
		};
	}
}
