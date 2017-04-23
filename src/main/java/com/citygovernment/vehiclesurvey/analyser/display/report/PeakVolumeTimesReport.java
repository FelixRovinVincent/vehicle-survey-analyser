package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;

/**
 * Report to output Peak volume times.
 * 
 * @author lordlion
 *
 */
public class PeakVolumeTimesReport extends AReport {
	
	
	/**
	 * Peak Volume Time.
	 * @author lordlion
	 *
	 */
	private class PeakTime {
		private LocalTime	intervalStart;
		private LocalTime	intervalStop;
		private long		vehiclesPassedInInterval;
		
		/**
		 * Constructor with parameters.
		 * @param intervalStart Interval start.
		 * @param intervalStop Interval stop.
		 * @param vehiclesPassedInInterval Vehicles passed in interval.
		 */
		public PeakTime(LocalTime intervalStart, LocalTime intervalStop, long vehiclesPassedInInterval) {
			this.intervalStart = intervalStart;
			this.intervalStop = intervalStop;
			this.vehiclesPassedInInterval = vehiclesPassedInInterval;
		}
	}
	
	/**
	 * Analysis instance kept to find number of days.
	 */
	private Analysis analysis;
	
	HashMap<String, PeakTime> peakVolumeTotalPeriodMap = new HashMap<>();
	
	/**
	 * Constructor with report file name as parameter.
	 */
	public PeakVolumeTimesReport() {
		super("PeakVolumeTimesReport.txt");
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
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#
	 * calculationForEachIntervalInPeriodMethod(java.time.LocalTime,
	 * java.time.LocalTime, java.util.List)
	 */
	@Override
	protected void calculationForEachIntervalInPeriodMethod(LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList) {
		// TODO
		}
	
	/**
	 * This method checks if the input is peak and if so, records it.
	 * 
	 * @param intervalStart
	 *            Start of interval.
	 * @param intervalStop
	 *            End of interval.
	 * @param vehiclesPassedInInterval
	 *            Vehicles passed in interval.
	 * @param strDirection
	 *            direction.
	 */
	private void recordPeakTime(LocalTime intervalStart, LocalTime intervalStop, long vehiclesPassedInInterval, String strDirection) {
		// TODO
	}
	
	/**
	 * Method containing the logic to find the peak volumes of averages of all
	 * days.
	 * 
	 * @param intervalStart
	 *            Start of time interval.
	 * @param intervalStop
	 *            Stop of time interval.
	 * @param vehiclesList
	 *            List of vehicles.
	 */
	private void findAveragePeakVolumes(LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList) {
		// TODO
				}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#
	 * postCalculationForAPeriodMethod()
	 */
	@Override
	protected Void postCalculationForAPeriodMethod() {
		// TODO
		return null;
	}
	
	/**
	 * Counts number of vehicles in the provided list.
	 * 
	 * @param vehiclesList
	 *            List of vehicles.
	 * @return Count of vehicles.
	 */
	private long findCount(List<Vehicle> vehiclesList) {		
		// TODO
		return 0;
	}
	
	/**
	 * Finds average number of vehicles in the provided list.
	 * @param vehiclesList List of vehicles.
	 * @return Count of vehicles.
	 */
	private long findAverageCount(List<Vehicle> vehiclesList, int numberOfDays) {
		// TODO
				return 0;
	}
	
}
