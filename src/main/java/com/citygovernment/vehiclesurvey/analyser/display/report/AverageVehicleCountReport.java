package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.time.LocalTime;
import java.util.List;

import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;

/**
 * Report to output average vehicle counts across all the days in each direction: morning versus evening, per hour, per half hour, per 20 minutes, and per 15 minutes.
 * 
 * @author lordlion
 *
 */
public class AverageVehicleCountReport extends AReport{

	/**
	 * Analysis instance kept to find number of days.
	 */
	private Analysis analysis;

	/**
	 * Constructor with report file name as parameter.
	 */
	public AverageVehicleCountReport() {
		super("AverageVehicleCountReport.txt");
	}
	
	
	/* (non-Javadoc)
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#calculationForEachIntervalInPeriodMethod(java.time.LocalTime, java.time.LocalTime, java.util.List)
	 */
	@Override
	protected void calculationForEachIntervalInPeriodMethod(LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList) {

		}
	
	
	/**
	 * Finds average number of vehicles in the provided list.
	 * @param vehiclesList List of vehicles.
	 * @return Count of vehicles.
	 */
	private long findAverageCount(List<Vehicle> vehiclesList, int numberOfDays) {
				//TODO
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#preCalculationForAPeriodMethod(java.lang.String)
	 */
	@Override
	protected void preCalculationForAPeriodMethod(String strPeriodName) {
		// TODO
	}

	/* (non-Javadoc)
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#show(com.citygovernment.vehiclesurvey.analyser.analysis.Analysis)
	 */
	@Override
	public void show(Analysis analysis) {
		// TODO
	}
}
