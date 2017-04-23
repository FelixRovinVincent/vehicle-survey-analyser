package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.citygovernment.vehiclesurvey.analyser.Application;
import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.DailyAnalysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.Direction;
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
	 * 
	 * @author lordlion
	 *
	 */
	protected class PeakTime {
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
			this.vehiclesPassedInInterval =vehiclesPassedInInterval;
		}

		public long getVehiclesPassedInInterval() {
			return vehiclesPassedInInterval;
		}

		public LocalTime getIntervalStart() {
			return intervalStart;
		}

		public LocalTime getIntervalStop() {
			return intervalStop;
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
		this.analysis = analysis;
		try (OutputStream os = new FileOutputStream(outputFilePath.toFile()); PrintStream printStream = new PrintStream(os);) {
			Application.LOGGER.log(Level.INFO, () ->String.format("Generate report file - %s", outputFilePath));
			this.printStream = printStream;
			printStream.print("\t*** Peak Volume Times Report ***");
			int count = 1;
			List<Vehicle> averageVehiclesPassedAcrossAllDays = new ArrayList<>();
			for (Iterator<DailyAnalysis> iterator = analysis.getDailyAnalysisList().iterator(); iterator.hasNext();) {
				DailyAnalysis dayWiseAnalysis = iterator.next();
				averageVehiclesPassedAcrossAllDays.addAll(dayWiseAnalysis.getVehiclesPassed());
				printStream.print("\n\n\t\t*** Day " + (count++) + "***\n");
				preCalculationForAPeriodMethod("Morning/Evening");
				computeForMorning(dayWiseAnalysis.getVehiclesPassed(), b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v));
				computeForEvening(dayWiseAnalysis.getVehiclesPassed(), b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v));
				postCalculationForAPeriodMethod();
				
				computeForAllOtherPeriods(dayWiseAnalysis.getVehiclesPassed(), s -> preCalculationForAPeriodMethod(s), b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v),
						this::postCalculationForAPeriodMethod);
			}
			
			printStream.print("\n\n\t\t*** Average Peak Times across all days ***\n");
			
			preCalculationForAPeriodMethod("Morning/Evening");
			computeForMorning(averageVehiclesPassedAcrossAllDays, b -> e -> v -> findAveragePeakVolumes(b, e, v));
			computeForEvening(averageVehiclesPassedAcrossAllDays, b -> e -> v -> findAveragePeakVolumes(b, e, v));
			postCalculationForAPeriodMethod();
			
			computeForAllOtherPeriods(averageVehiclesPassedAcrossAllDays, s -> preCalculationForAPeriodMethod(s), b -> e -> v -> findAveragePeakVolumes(b, e, v),
					this::postCalculationForAPeriodMethod);
			
		} catch (Exception e2) {
			Application.LOGGER.log(Level.SEVERE, "Could not show Report.", e2);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#
	 * preCalculationForAPeriodMethod(java.lang.String)
	 */
	@Override
	protected void preCalculationForAPeriodMethod(String strPeriodName) {
		printStream.print("\n\t\t\t" + strPeriodName);
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
		List<Vehicle> vehiclesDuringPeriod = vehiclesList.stream().filter(v -> v.getPassingTime().isAfter(intervalStart) && v.getPassingTime().isBefore(intervalStop)).collect(Collectors.toList());
		long vehiclesPassedInInterval = findCount(vehiclesDuringPeriod);
		String strDirection = "Total";
		recordPeakTime(intervalStart, intervalStop, vehiclesPassedInInterval, strDirection);
		
		List<Vehicle> vehiclesNorthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.NORTH)).collect(Collectors.toList());
		long vehiclesPassedInMorningOfInterval = findCount(vehiclesNorthbound);
		strDirection = "Northbound";
		recordPeakTime(intervalStart, intervalStop, vehiclesPassedInMorningOfInterval, strDirection);
		
		List<Vehicle> vehiclesSouthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.SOUTH)).collect(Collectors.toList());
		long vehiclesPassedInEveningOfInterval = findCount(vehiclesSouthbound);
		strDirection = "Southbound";
		recordPeakTime(intervalStart, intervalStop, vehiclesPassedInEveningOfInterval, strDirection);
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
		if (peakVolumeTotalPeriodMap.get(strDirection) != null) {
			if (peakVolumeTotalPeriodMap.get(strDirection).vehiclesPassedInInterval < vehiclesPassedInInterval) {
				peakVolumeTotalPeriodMap.put(strDirection, new PeakTime(intervalStart, intervalStop, vehiclesPassedInInterval));
			}
		} else {
			peakVolumeTotalPeriodMap.put(strDirection, new PeakTime(intervalStart, intervalStop, vehiclesPassedInInterval));
		}
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
		List<Vehicle> vehiclesDuringPeriod = vehiclesList.stream().filter(v -> v.getPassingTime().isAfter(intervalStart) && v.getPassingTime().isBefore(intervalStop)).collect(Collectors.toList());
		long vehiclesPassedInInterval = findAverageCount(vehiclesDuringPeriod,this.analysis.getDailyAnalysisList().size());
		String strDirection = "Total";
		recordPeakTime(intervalStart, intervalStop, vehiclesPassedInInterval, strDirection);
		
		List<Vehicle> vehiclesNorthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.NORTH)).collect(Collectors.toList());
		long vehiclesPassedInMorningOfInterval = findAverageCount(vehiclesNorthbound,this.analysis.getDailyAnalysisList().size());
		strDirection = "Northbound";
		recordPeakTime(intervalStart, intervalStop, vehiclesPassedInMorningOfInterval, strDirection);
		
		List<Vehicle> vehiclesSouthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.SOUTH)).collect(Collectors.toList());
		long vehiclesPassedInEveningOfInterval = findAverageCount(vehiclesSouthbound,this.analysis.getDailyAnalysisList().size());
		strDirection = "Southbound";
		recordPeakTime(intervalStart, intervalStop, vehiclesPassedInEveningOfInterval, strDirection);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#
	 * postCalculationForAPeriodMethod()
	 */
	@Override
	protected Void postCalculationForAPeriodMethod() {
		for (Entry<String, PeakTime> entry : peakVolumeTotalPeriodMap.entrySet()) {
			String directionalPeriodName = entry.getKey();
			PeakTime peakTime = entry.getValue();
			printStream.print("\n\t\t\t\t" + directionalPeriodName + " vehicles Peak volume time (" + Application.DATE_TIME_FORMATTER.format(peakTime.intervalStart) + " to "
					+ Application.DATE_TIME_FORMATTER.format(peakTime.intervalStop) + ") = " + peakTime.vehiclesPassedInInterval);
		}
		
		peakVolumeTotalPeriodMap.clear();
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
		long count = vehiclesList.size();
		return count;
	}
	
	/**
	 * Finds average number of vehicles in the provided list.
	 * @param vehiclesList List of vehicles.
	 * @return Count of vehicles.
	 */
	private long findAverageCount(List<Vehicle> vehiclesList, int numberOfDays) {
		long averageCount = vehiclesList.size()/numberOfDays;
		return averageCount;
	}
	
}
