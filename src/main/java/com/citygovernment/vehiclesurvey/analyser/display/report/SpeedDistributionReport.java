package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;

/**
 * Report to output the (rough) speed distribution of traffic.
 * 
 * @author lordlion
 *
 */
public class SpeedDistributionReport extends AReport {
	
	/**
	 * Data model to hold percentages of vehicles.
	 * 
	 * @author lordlion
	 *
	 */
	protected class PercentageOfVehiclesWithinLimit {
		private final Double	lowerThreshold;
		private final Double	percentage;
		private final Double	upperThreshold;
		
		/**
		 * Constructor for PercentageOfVehiclesWithinLimit.
		 * 
		 * @param percentage
		 *            Percentage of vehicles within thresholds.
		 * @param lowerThreshold
		 *            Lower speed threshold.
		 * @param upperThreshold
		 *            Upper speed threshold.
		 */
		public PercentageOfVehiclesWithinLimit(Double percentage, Double lowerThreshold, Double upperThreshold) {
			super();
			this.percentage = percentage;
			this.lowerThreshold = lowerThreshold;
			this.upperThreshold = upperThreshold;
		}
		
		public Double getLowerThreshold() {
			return lowerThreshold;
		}
		
		public Double getPercentage() {
			return percentage;
		}
		
		public Double getUpperThreshold() {
			return upperThreshold;
		}
		
	}
	
	/**
	 * Class to represent Data containing a list of
	 * PercentageOfVehiclesWithinLimit records.
	 * 
	 * @author lordlion
	 *
	 */
	protected class SpeedDistribution {
		private ArrayList<PercentageOfVehiclesWithinLimit> percentageList = new ArrayList<>();
		
		public ArrayList<PercentageOfVehiclesWithinLimit> getPercentageList() {
			return percentageList;
		}
	}
	
	/**
	 * Number formatter with two decimal places.
	 */
	private final NumberFormat formatter = new DecimalFormat("#00.00");
	
	/**
	 * Constructor with report file name as parameter.
	 */
	public SpeedDistributionReport() {
		super("SpeedDistributionReport.txt");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#
	 * calculationForEachIntervalInPeriodMethod(java.time.LocalTime,
	 * java.time.LocalTime, java.util.List)
	 */
	@Override
	protected void calculationForEachIntervalInPeriodMethod(LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList) {
		// TODO
	}
	
	/**
	 * Finds the speed distribution of vehicles in percentage of vehicles within
	 * speed intervals.
	 * 
	 * @param vehicleList
	 *            List of vehicles.
	 * @param speedInterval
	 *            Speed interval.
	 * @param speedUpto
	 *            The last threshold to be considered.
	 * @return SpeedDistribution
	 */
	private SpeedDistribution findSpeedDistribution(ArrayList<Vehicle> vehicleList, Double speedInterval, Double speedUpto) {
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
