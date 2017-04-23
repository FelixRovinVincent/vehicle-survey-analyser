package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;

public class InterVehicularDistanceReport extends AReport {
	
	NumberFormat formatter = new DecimalFormat("#00.00");
	
	public InterVehicularDistanceReport() {
		super("InterVehicularDistanceReport.txt");
	}
	
	/**
	 * This method calculates distances between each pair of vehicles taken in
	 * sequence from first to the last vehicle.
	 * 
	 * @param vehiclesList
	 *            List of vehicles.
	 * @return ArrayList<Double> List containing inter-vehicular distance.
	 */
	private ArrayList<Double> calculateDistances(List<Vehicle> vehiclesList) {
		// TODO
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#calculationForEachIntervalInPeriodMethod(java.time.LocalTime, java.time.LocalTime, java.util.List)
	 */
	@Override
	protected void calculationForEachIntervalInPeriodMethod(LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList) {
		// TODO
	}
	
	/**
	 * Finds average of the distances input as a list.
	 * 
	 * @param distancesList
	 *            input list.
	 * @return average
	 */
	private Double findAverage(ArrayList<Double> distancesList) {
		// TODO
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#
	 * preCalculationForAPeriodMethod(java.lang.String)
	 */
	@Override
	protected void preCalculationForAPeriodMethod(String strPeriodName) {
		// TODO
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.citygovernment.vehiclesurvey.analyser.display.report.AReport#show(com
	 * .citygovernment.vehiclesurvey.analyser.analysis.Analysis)
	 */
	@Override
	public void show(Analysis analysis) {
		// TODO
	}
	
}
