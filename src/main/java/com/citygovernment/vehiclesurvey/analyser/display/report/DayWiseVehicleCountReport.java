package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.time.LocalTime;
import java.util.List;

import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;
import com.citygovernment.vehiclesurvey.analyser.display.ReportPeriod;

/**
 * Class representing day-wise vehicle count report.
 * 
 * @author Felix Rovin Vincent
 *
 */
public class DayWiseVehicleCountReport extends AReport {
	
	/* (non-Javadoc)
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#show(com.citygovernment.vehiclesurvey.analyser.analysis.Analysis)
	 */
	@Override
	public void show(Analysis analysis) {
		System.out.println("\n\t*** Day-wise Vehicle Count Report***");		
	//TODO
		
	}
	
	/**
	 * Method to calculate the count per day.
	 * @param numberOfDays Number of days data is recorded.
	 * @param period report period.
	 * @param intervalStart Interval Start.
	 * @param intervalStop Interval Stop.
	 * @param vehiclesList List of vehicles on which count is performed.
	 */
	private void count(long numberOfDays, ReportPeriod period, LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList) {}
}
