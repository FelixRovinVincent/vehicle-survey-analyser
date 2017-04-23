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

import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;
import com.citygovernment.vehiclesurvey.analyser.display.report.PeakVolumeTimesReport.PeakTime;

public class PeakVolumeTimesReportTest {
	
	Class<?>				cls;
	PeakVolumeTimesReport	peakVolumeTimesReport;
	
	/**
	 * Since createDataRecord method is private, we have to use reflection to
	 * invoke the same from test class.
	 */
	@BeforeClass
	public void beforeClass() {
		try {
			cls = Class.forName("com.citygovernment.vehiclesurvey.analyser.display.report.PeakVolumeTimesReport");
			
			peakVolumeTimesReport = (PeakVolumeTimesReport) cls.newInstance();
		} catch (Exception e) {
			Reporter.log("Could not perform operations to be done before tests in PeakVolumeTimesReportTest.");
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
			Method method = cls.getDeclaredMethod("findAverageCount", List.class, int.class);
			method.setAccessible(true);
			long averageCountOfVehicles = (long) method.invoke(peakVolumeTimesReport, vehiclesList, numberOfDays);
			
			Reporter.log("<br/>Verify count with actual.", true);
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
			long countOfVehicles = (long) method.invoke(peakVolumeTimesReport, vehiclesList);
			
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
	
	/**
	 * This method checks if recording peak times is correct.
	 * 
	 * @param intervalStart
	 *            Start of interval.
	 * @param intervalStop
	 *            End of interval.
	 * @param vehiclesPassedInInterval
	 *            Vehicles passed in interval.
	 * @param strDirection
	 *            direction.
	 * @param actualcount
	 *            Actual peak.
	 */
	@Test(dataProvider = "recordPeakTimeTestData", enabled = true)
	public void recordPeakTime(LocalTime intervalStart, LocalTime intervalStop, long vehiclesPassedInInterval, String strDirection, PeakTime actualPeak) {
		try {
			Method method = cls.getDeclaredMethod("recordPeakTime", LocalTime.class, LocalTime.class, long.class, String.class);
			method.setAccessible(true);
			method.invoke(peakVolumeTimesReport, intervalStart, intervalStop, vehiclesPassedInInterval, strDirection);
			
			PeakTime peakTime = peakVolumeTimesReport.peakVolumeTotalPeriodMap.get(strDirection);
			
			Reporter.log("<br/>Verify peak with actual.", true);
			Assert.assertEquals(peakTime.getIntervalStart(), actualPeak.getIntervalStart());
			Assert.assertEquals(peakTime.getIntervalStop(), actualPeak.getIntervalStop());
			Assert.assertEquals(peakTime.getVehiclesPassedInInterval(), actualPeak.getVehiclesPassedInInterval());
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
	public Object[][] recordPeakTimeTestData() {
		return new Object[][] {
				new Object[] {
						LocalTime.MIN, LocalTime.NOON, 7, "Northbound", peakVolumeTimesReport.new PeakTime(LocalTime.MIN, LocalTime.NOON, 7)
				}, {
						LocalTime.NOON, LocalTime.MIDNIGHT, 70, "Northbound", peakVolumeTimesReport.new PeakTime(LocalTime.NOON, LocalTime.MIDNIGHT, 70)
				}, {
						LocalTime.MIN, LocalTime.NOON, 10, "Northbound", peakVolumeTimesReport.new PeakTime(LocalTime.NOON, LocalTime.MIDNIGHT, 70)
				}, {
						LocalTime.MIN, LocalTime.NOON, 7, "Southbound", peakVolumeTimesReport.new PeakTime(LocalTime.MIN, LocalTime.NOON, 7)
				}, {
						LocalTime.NOON, LocalTime.MIDNIGHT, 70, "Southbound", peakVolumeTimesReport.new PeakTime(LocalTime.NOON, LocalTime.MIDNIGHT, 70)
				}, {
						LocalTime.MIN, LocalTime.NOON, 10, "Southbound", peakVolumeTimesReport.new PeakTime(LocalTime.NOON, LocalTime.MIDNIGHT, 70)
				}, {
						LocalTime.MIN, LocalTime.NOON, 1, "Northbound", peakVolumeTimesReport.new PeakTime(LocalTime.NOON, LocalTime.MIDNIGHT, 70)
				}, {
						LocalTime.MIN, LocalTime.NOON, 100, "Northbound", peakVolumeTimesReport.new PeakTime(LocalTime.MIN, LocalTime.NOON, 100)
				}
		};
	}
	
}
