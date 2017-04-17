package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.Direction;
import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;
import com.citygovernment.vehiclesurvey.analyser.display.ReportPeriod;

/**
 * Base class for all reports
 * @author Felix Rovin Vincent
 *
 */
public abstract class AReport {
		
	/**
	 * Compute a calculation (method passed as parameter) for all periods in the Enum ReportPeriod. 
	 * @param vehiclesList List of vehicles
	 * @param calculationMethod method to perform calculation for a single period
	 * @param numberOfDays Number of days data was recorded
	 */
	protected void computeForAllOtherPeriods(List<Vehicle> vehiclesList, Function<Integer, Function<ReportPeriod, Function<LocalTime, Function<LocalTime, Consumer<List<Vehicle>>>>>> calculationMethod,
			Integer numberOfDays) {}

	/**
	 * Compute for Morning and Evening.
	 * 
	 * @param vehicleList VehicleList to be processed
	 * @param direction Direction
	 * @param numberOfDays total number of days
	 * @return List List of vehicles per direction
	 */
	protected List<Vehicle> computeForMorningEvening(List<Vehicle> vehicleList, Direction direction, int numberOfDays) {
		List<Vehicle> directionWiseVehiclesList = new ArrayList();
		
		return directionWiseVehiclesList;
	}
	
	/**
	 * Every report must have a method to show the same.
	 * @param analysis the Analysis instance to display a report for.
	 */
	public abstract void show(Analysis analysis);
}
