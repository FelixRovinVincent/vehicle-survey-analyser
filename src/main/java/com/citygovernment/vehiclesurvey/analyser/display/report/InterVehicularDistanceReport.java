package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
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
		final ArrayList<Double> interVehicularDistanceList = new ArrayList<Double>();
		try {
			final Vehicle lastVehicle = new Vehicle();
			vehiclesList.forEach(vehicle -> {
				if (Math.abs(lastVehicle.getSpeed() - 0f) > 0.001f) {
					double timeGapBetweenVehicles = (double) Duration.between(lastVehicle.getPassingTime(), vehicle.getPassingTime()).toMillis() / 1000 / 60 / 60;
					
					Double interVehicularDistance = timeGapBetweenVehicles * lastVehicle.getSpeed();
					interVehicularDistanceList.add(interVehicularDistance);
				}
				lastVehicle.setDirection(vehicle.getDirection());
				lastVehicle.setPassingTime(vehicle.getPassingTime());
				lastVehicle.setSpeed(vehicle.getSpeed());
			});
		} catch (Exception e) {
			Application.LOGGER.log(Level.SEVERE, "Could not calculate inter-vehicular distances.", e);
		}
		return interVehicularDistanceList;
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
		try {
			List<Vehicle> vehiclesDuringPeriod = vehiclesList.stream().filter(v -> v.getPassingTime().isAfter(intervalStart) && v.getPassingTime().isBefore(intervalStop)).collect(Collectors.toList());
			printStream.print(
					"\n\t\t\t\tRough distance between vehicles during (" + Application.DATE_TIME_FORMATTER.format(intervalStart) + " to " + Application.DATE_TIME_FORMATTER.format(intervalStop) + ")");
			
			List<Vehicle> vehiclesNorthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.NORTH)).collect(Collectors.toList());
			ArrayList<Double> interVehicularDistanceList = calculateDistances(vehiclesNorthbound);
			printStream.print("\n\t\t\t\t\tNorthbound vehicles  \t= " + formatter.format(findAverage(interVehicularDistanceList)) + " km");
			
			List<Vehicle> vehiclesSouthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.SOUTH)).collect(Collectors.toList());
			interVehicularDistanceList = calculateDistances(vehiclesSouthbound);
			printStream.print("\n\t\t\t\t\tSouthbound vehicles \t= " + formatter.format(findAverage(interVehicularDistanceList)) + " km");
		} catch (Exception e) {
			Application.LOGGER.log(Level.SEVERE, "Could not find average inter-vehicular distance.", e);
		}
	}
	
	/**
	 * Finds average of the distances input as a list.
	 * 
	 * @param distancesList
	 *            input list.
	 * @return average
	 */
	private Double findAverage(ArrayList<Double> distancesList) {
		Double average = null;
		try {
			average = distancesList.stream().mapToDouble(v -> v).average().orElse(0d);
		} catch (Exception e) {
			Application.LOGGER.log(Level.SEVERE, "Could not calculate average of distances.", e);
		}
		return average;
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
	 * @see
	 * com.citygovernment.vehiclesurvey.analyser.display.report.AReport#show(com
	 * .citygovernment.vehiclesurvey.analyser.analysis.Analysis)
	 */
	@Override
	public void show(Analysis analysis) {
		try (OutputStream os = new FileOutputStream(outputFilePath.toFile()); PrintStream printStream = new PrintStream(os);) {
			Application.LOGGER.log(Level.INFO, () ->String.format("Generate report file - %s", outputFilePath));
			this.printStream = printStream;
			printStream.print("\t*** Rough distance between vehicles during various periods ***\n");
			int dayCount = 1;
			for (Iterator<DailyAnalysis> iterator = analysis.getDailyAnalysisList().iterator(); iterator.hasNext();) {
				DailyAnalysis dayWiseAnalysis = iterator.next();
				
				printStream.print("\n\t\tDay " + dayCount++);
				printStream.print("\n\t\t\tMorning");
				computeForMorning(dayWiseAnalysis.getVehiclesPassed(), b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v));
				printStream.print("\n\t\t\tEvening");
				computeForEvening(dayWiseAnalysis.getVehiclesPassed(), b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v));
				
				computeForAllOtherPeriods(dayWiseAnalysis.getVehiclesPassed(), s -> preCalculationForAPeriodMethod(s), b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v),
						this::postCalculationForAPeriodMethod);
			}
			
		} catch (Exception e) {
			Application.LOGGER.log(Level.SEVERE, "Could not show Report.", e);
		}
	}
	
}
