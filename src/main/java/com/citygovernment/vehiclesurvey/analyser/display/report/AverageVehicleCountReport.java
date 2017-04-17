package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.time.LocalTime;
import java.util.List;

import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;
import com.citygovernment.vehiclesurvey.analyser.display.ReportPeriod;

/**
 * Total vehicle counts in each direction: averages across all the days.
 * 
 * @author Felix Rovin Vincent
 *
 */
public class AverageVehicleCountReport extends AReport{

	/* (non-Javadoc)
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#show(com.citygovernment.vehiclesurvey.analyser.analysis.Analysis)
	 */
	@Override
	public void show(Analysis analysis) {
		
		System.out.println("\n\t*** Average Vehicle Count Report ***");
		}
	
	/**
	 * Method to calculate the average count across all days.
	 * @param numberOfDays Number of days data is recorded.
	 * @param period report period.
	 * @param intervalStart Interval Start.
	 * @param intervalStop Interval Stop.
	 * @param vehiclesList List of vehicles on which count is performed.
	 */
	private void count(long numberOfDays, ReportPeriod period, LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList) {}
}
