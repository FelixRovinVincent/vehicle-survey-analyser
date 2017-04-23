package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
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
 * Report to output Day-wise vehicle counts in each direction: morning versus evening, per hour, per half hour, per 20 minutes, and per 15 minutes.
 * @author lordlion
 *
 */
public class DayWiseVehicleCountReport extends AReport {
	
	/**
	 * Constructor with report file name as parameter.
	 */
	public DayWiseVehicleCountReport() {
		super("DayWiseVehicleCountReport.txt");
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
		try (OutputStream os = new FileOutputStream(outputFilePath.toFile()); PrintStream printStream = new PrintStream(os);) {
			Application.LOGGER.info("Generate report file - " + outputFilePath);
			this.printStream = printStream;
			printStream.print("\t*** Day-wise Vehicle Count Report ***\n");
			for (Iterator<DailyAnalysis> iterator = analysis.getDailyAnalysisList().iterator(); iterator.hasNext();) {
				DailyAnalysis dayWiseAnalysis = iterator.next();
				
				Integer vehicleCountOnDay = dayWiseAnalysis.getVehiclesPassed().size();
				printStream.print("\n\t\tTotal vehicle count on Day " + dayWiseAnalysis.getDay() + " = " + vehicleCountOnDay);
				
				printStream.print("\n\t\t\tMorning");
				computeForMorning(dayWiseAnalysis.getVehiclesPassed(), b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v));
				printStream.print("\n\t\t\tEvening");
				computeForEvening(dayWiseAnalysis.getVehiclesPassed(), b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v));
				
				computeForAllOtherPeriods(dayWiseAnalysis.getVehiclesPassed(), s -> preCalculationForAPeriodMethod(s), b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v), this::postCalculationForAPeriodMethod);
			}
		} catch (Exception e2) {
			Application.LOGGER.log(Level.SEVERE, "Could not show Report.", e2);
		}
	}


	/* (non-Javadoc)
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#preCalculationForAPeriodMethod(java.lang.String)
	 */
	@Override
	protected void preCalculationForAPeriodMethod(String strPeriodName) {
		printStream.print("\n\t\t\t" + strPeriodName);
	}


	/* (non-Javadoc)
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#calculationForEachIntervalInPeriodMethod(java.time.LocalTime, java.time.LocalTime, java.util.List)
	 */
	@Override
	protected void calculationForEachIntervalInPeriodMethod(LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList) {
		List<Vehicle> vehiclesDuringPeriod = vehiclesList.stream().filter(v -> v.getPassingTime().isAfter(intervalStart) && v.getPassingTime().isBefore(intervalStop)).collect(Collectors.toList());
		printStream.print("\n\t\t\t\tTotal vehicle count (" + Application.DATE_TIME_FORMATTER.format(intervalStart) + " to " + Application.DATE_TIME_FORMATTER.format(intervalStop) + ") = "
				+ findCount(vehiclesDuringPeriod));
		
		List<Vehicle> vehiclesNorthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.NORTH)).collect(Collectors.toList());
		printStream.print("\n\t\t\t\t\tNorthbound vehicle count (" + Application.DATE_TIME_FORMATTER.format(intervalStart) + " to " + Application.DATE_TIME_FORMATTER.format(intervalStop) + ") = "
				+ findCount(vehiclesNorthbound));
		
		List<Vehicle> vehiclesSouthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.SOUTH)).collect(Collectors.toList());
		printStream.print("\n\t\t\t\t\tSouthbound vehicle count (" + Application.DATE_TIME_FORMATTER.format(intervalStart) + " to " + Application.DATE_TIME_FORMATTER.format(intervalStop) + ") = "
				+ findCount(vehiclesSouthbound));
		
	}

	/**
	 * Counts number of vehicles in the provided list.
	 * @param vehiclesList List of vehicles.
	 * @return Count of vehicles.
	 */
	private long findCount(List<Vehicle> vehiclesList) {
		long count = vehiclesList.size();
		return count;
	}
	
}
