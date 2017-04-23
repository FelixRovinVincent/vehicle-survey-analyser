package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;
import com.citygovernment.vehiclesurvey.analyser.display.report.SpeedDistributionReport.SpeedDistribution;

public class SpeedDistributionReportTest {
	
	Class<?>				cls;
	SpeedDistributionReport	obj;
	
	/**
	 * Since createDataRecord method is private, we have to use reflection to
	 * invoke the same from test class.
	 */
	@BeforeClass
	public void beforeClass() {
		try {
			cls = Class.forName("com.citygovernment.vehiclesurvey.analyser.display.report.SpeedDistributionReport");
			obj = (SpeedDistributionReport) cls.newInstance();
		} catch (Exception e) {
			Reporter.log("Could not perform operations to be done before tests in SpeedDistributionReportTest.");
		}
	}
	
	/**
	 * Tests calculation of speed distribution of vehicles.
	 * 
	 * @param vehiclesList
	 *            List of vehicles.
	 * @param speedInterval
	 *            Speed interval.
	 * @param speedUpto
	 *            Speed max threshold above which is calculated as one single
	 *            percentage.
	 * @param actualSpeedDistribution
	 *            The actual Speed Distribution.
	 */
	@Test(dataProvider = "findSpeedDistributionTestData", enabled = true)
	public void findSpeedDistribution(ArrayList<Vehicle> vehicleList, Double speedInterval, Double speedUpto, SpeedDistribution actualSpeedDistribution) {
		try {
			Method method = cls.getDeclaredMethod("findSpeedDistribution", ArrayList.class, Double.class, Double.class);
			method.setAccessible(true);
			SpeedDistribution speedDistribution = (SpeedDistribution) method.invoke(obj, vehicleList, speedInterval, speedUpto);
			
			Reporter.log("<br/>Verify distances calculated with actual - " + actualSpeedDistribution.getPercentageList(), true);
			Assert.assertEquals(speedDistribution.getPercentageList(), actualSpeedDistribution.getPercentageList());
		} catch (Exception e) {
			Reporter.log(e.getStackTrace().toString(), true);
			e.printStackTrace();
		}
	}
	
	/**
	 * Few sample data along with the actual speed distribution.
	 * 
	 * @return Object[][] Test Data.
	 */
	@DataProvider
	public Object[][] findSpeedDistributionTestData() {
		ArrayList<Vehicle> vehicleList1 = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			Vehicle vehicle = new Vehicle();
			vehicle.setSpeed((i * 5f) - 1);
			vehicleList1.add(vehicle);
		}
		SpeedDistribution actualSpeedDistribution = obj.new SpeedDistribution();
		actualSpeedDistribution.getPercentageList().add(obj.new PercentageOfVehiclesWithinLimit(10d, 0d, 5d));
		actualSpeedDistribution.getPercentageList().add(obj.new PercentageOfVehiclesWithinLimit(10d, 5d, 10d));
		actualSpeedDistribution.getPercentageList().add(obj.new PercentageOfVehiclesWithinLimit(10d, 10d, 15d));
		actualSpeedDistribution.getPercentageList().add(obj.new PercentageOfVehiclesWithinLimit(10d, 15d, 20d));
		actualSpeedDistribution.getPercentageList().add(obj.new PercentageOfVehiclesWithinLimit(60d, 100d, null));
		
		return new Object[][] {
				new Object[] {
						vehicleList1, 5d, 20d, actualSpeedDistribution
				}
		};
		
	}
}
