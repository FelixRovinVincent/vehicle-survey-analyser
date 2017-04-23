package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.citygovernment.vehiclesurvey.analyser.Application;
import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.DailyAnalysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.Direction;
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
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#show(com.citygovernment.vehiclesurvey.analyser.analysis.Analysis)
	 */
	@Override
	public void show(Analysis analysis) {
		
		this.analysis = analysis;
		try (OutputStream os = new FileOutputStream(outputFilePath.toFile()); PrintStream printStream = new PrintStream(os);) {
			Application.LOGGER.info("Generate report file - " + outputFilePath);
			this.printStream = printStream;
			printStream.print("\t*** Average Vehicle Count Report ***\n");
			ArrayList<Vehicle> vehicleList = new ArrayList<>();
			for (Iterator<DailyAnalysis> iterator = analysis.getDailyAnalysisList().iterator(); iterator.hasNext();) {
				DailyAnalysis dayWiseAnalysis = iterator.next();
				
				vehicleList.addAll(dayWiseAnalysis.getVehiclesPassed());
			}
			Integer AverageVehicleCount = vehicleList.size();
			printStream.print("\n\t\tAverage vehicle count across all the days = " + AverageVehicleCount/this.analysis.getDailyAnalysisList().size());
			
			printStream.print("\n\t\t\tMorning");
			computeForMorning(vehicleList,  b -> e -> v -> calculationForEachIntervalInPeriodMethod( b, e, v));
			printStream.print("\n\t\t\tEvening");
			computeForEvening(vehicleList,  b -> e -> v -> calculationForEachIntervalInPeriodMethod( b, e, v));
			
			computeForAllOtherPeriods(vehicleList, s -> preCalculationForAPeriodMethod(s),  b -> e -> v -> calculationForEachIntervalInPeriodMethod( b, e, v), this::postCalculationForAPeriodMethod);
			
		} catch (Exception e2) {
			Application.LOGGER.log(Level.SEVERE, "Could not show Report.", e2);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#preCalculationForAPeriodMethod(java.lang.String)
	 */
	@Override
	protected void preCalculationForAPeriodMethod(String strPeriodName) {
		printStream.print("\n\t\t\t"+strPeriodName);
	}

	/* (non-Javadoc)
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#calculationForEachIntervalInPeriodMethod(java.time.LocalTime, java.time.LocalTime, java.util.List)
	 */
	@Override
	protected void calculationForEachIntervalInPeriodMethod(LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList) {
		List<Vehicle> vehiclesDuringPeriod = vehiclesList.stream().filter(v -> v.getPassingTime().isAfter(intervalStart) && v.getPassingTime().isBefore(intervalStop)).collect(Collectors.toList());
		printStream.print("\n\t\t\t\tAverage vehicle count (" + Application.DATE_TIME_FORMATTER.format(intervalStart) + " to " + Application.DATE_TIME_FORMATTER.format(intervalStop) + ") = "
				+ findAverageCount(vehiclesDuringPeriod,this.analysis.getDailyAnalysisList().size()));
		
		List<Vehicle> vehiclesNorthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.NORTH)).collect(Collectors.toList());
		printStream.print("\n\t\t\t\t\tNorthbound average vehicle count (" + Application.DATE_TIME_FORMATTER.format(intervalStart) + " to " + Application.DATE_TIME_FORMATTER.format(intervalStop) + ") = "
				+ findAverageCount(vehiclesNorthbound,this.analysis.getDailyAnalysisList().size()));
		List<Vehicle> vehiclesSouthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.SOUTH)).collect(Collectors.toList());
		printStream.print("\n\t\t\t\t\tSouthbound average vehicle count (" + Application.DATE_TIME_FORMATTER.format(intervalStart) + " to " + Application.DATE_TIME_FORMATTER.format(intervalStop) + ") = "
				+ findAverageCount(vehiclesSouthbound,this.analysis.getDailyAnalysisList().size()));
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
