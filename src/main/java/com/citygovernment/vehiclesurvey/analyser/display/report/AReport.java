package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.citygovernment.vehiclesurvey.analyser.Application;
import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;
import com.citygovernment.vehiclesurvey.analyser.display.ReportPeriod;

/**
 * Base class for all reports.
 * 
 * @author Felix Rovin Vincent
 *
 */
public abstract class AReport {
	
	/**
	 * Path for the final report file output.
	 */
	Path		outputFilePath;
	/**
	 * Output stream used to write report to file.
	 */
	PrintStream	printStream;
	
	public AReport(String reportFileName) {
		try {
			File dir = new File(Application.REPORT_FOLDER_NAME);
			dir.mkdirs();
			File tmp = new File(dir, reportFileName);
			if (!tmp.createNewFile()) {
				Application.LOGGER.log(Level.INFO, () ->String.format("File create failed - %s file may be already existing.", reportFileName));
			}
			this.outputFilePath = Paths.get(tmp.getPath());
			
		} catch (IOException e) {
			Application.LOGGER.log(Level.SEVERE, "Could not create Report file.", e);
		}
	}
	
	/**
	 * Compute a calculation (method passed as parameter) for Morning (Midnight
	 * to Noon).
	 * 
	 * @param vehiclesList
	 *            List of vehicles
	 * @param calculationMethod
	 *            method to perform calculation for a single period
	 */
	protected void computeForMorning(List<Vehicle> vehiclesList, Function<LocalTime, Function<LocalTime, Consumer<List<Vehicle>>>> calculationMethod) {
		List<Vehicle> vehiclesInMorning = vehiclesList.stream().filter(v -> v.getPassingTime().isBefore(LocalTime.NOON)).collect(Collectors.toList());
		calculationMethod.apply(LocalTime.MIN).apply(LocalTime.NOON).accept(vehiclesInMorning);
	}
	
	/**
	 * Compute a calculation (method passed as parameter) for Evening (06:00 PM
	 * to Midnight).
	 * 
	 * @param vehiclesList
	 *            List of vehicles
	 * @param calculationMethod
	 *            method to perform calculation for a single period
	 */
	protected void computeForEvening(List<Vehicle> vehiclesList, Function<LocalTime, Function<LocalTime, Consumer<List<Vehicle>>>> calculationMethod) {
		List<Vehicle> vehiclesInEvening = vehiclesList.stream().filter(v -> v.getPassingTime().isAfter(LocalTime.of(18, 00))).collect(Collectors.toList());
		calculationMethod.apply(LocalTime.of(18, 00)).apply(LocalTime.MAX).accept(vehiclesInEvening);
	}
	
	/**
	 * Compute a calculation (method passed as parameter) for all periods in the
	 * Enum ReportPeriod.
	 * 
	 * @param vehiclesList
	 *            List of vehicles
	 * @param preCalculationForAPeriodMethod
	 *            Method which will be executed each time before a calculation
	 *            is done for a period.
	 * @param calculationForEachIntervalInPeriodMethod
	 *            Method to perform a calculation for each interval in a period.
	 * @param postCalculationForAPeriodMethod
	 *            Method which will be executed each time after a calculation is
	 *            done for a period.
	 */
	protected void computeForAllOtherPeriods(List<Vehicle> vehiclesList, Consumer<String> preCalculationForAPeriodMethod,
			Function<LocalTime, Function<LocalTime, Consumer<List<Vehicle>>>> calculationForEachIntervalInPeriodMethod, Supplier<Void> postCalculationForAPeriodMethod) {
		for (ReportPeriod period : ReportPeriod.values()) {
			
			preCalculationForAPeriodMethod.accept(period.name());
			
			LocalTime reportTime = LocalTime.MIN;
			long newDuration;
			do {
				final LocalTime intervalStart = LocalTime.ofNanoOfDay(reportTime.toNanoOfDay());
				
				newDuration = Duration.between(reportTime, reportTime.plus(period.getDuration())).toMillis();
				final LocalTime intervalStop;
				if (newDuration > 0) {
					intervalStop = intervalStart.plus(period.getDuration());
				} else {
					intervalStop = LocalTime.MAX;
				}
				
				List<Vehicle> vehiclesDuringPeriod = vehiclesList.stream().filter(v -> v.getPassingTime().isAfter(intervalStart) && v.getPassingTime().isBefore(intervalStop))
						.collect(Collectors.toList());
				
				calculationForEachIntervalInPeriodMethod.apply(intervalStart).apply(intervalStop).accept(vehiclesDuringPeriod);
				
				reportTime = reportTime.plus(period.getDuration());
			} while (newDuration > 0);
			
			postCalculationForAPeriodMethod.get();
		}
		
	}
	
	/**
	 * Logic to be performed before calculation for each period.
	 * 
	 * @param strPeriodName
	 *            String representing the period name.
	 */
	protected abstract void preCalculationForAPeriodMethod(String strPeriodName);
	
	/**
	 * Method containing the logic to perform calculation for each interval in a
	 * period.
	 * 
	 * @param intervalStart
	 *            Start of time interval.
	 * @param intervalStop
	 *            Stop of time interval.
	 * @param vehiclesList
	 *            List of vehicles.
	 */
	protected abstract void calculationForEachIntervalInPeriodMethod(LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList);
	
	/**
	 * Logic to be performed after calculation for each period. Child report
	 * classes has to override if any logic is to be applied.
	 * 
	 * @return null nothing be returned.
	 */
	protected Void postCalculationForAPeriodMethod() {
		return null;
	}
	
	/**
	 * Every report must have a method to show the same.
	 * 
	 * @param analysis
	 *            the Analysis instance to display a report for.
	 */
	public abstract void show(Analysis analysis);
}
