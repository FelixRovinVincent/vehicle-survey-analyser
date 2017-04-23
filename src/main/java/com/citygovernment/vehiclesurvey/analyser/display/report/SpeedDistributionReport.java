package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
 * Report to output the (rough) speed distribution of traffic.
 * 
 * @author lordlion
 *
 */
public class SpeedDistributionReport extends AReport {
	
	protected class PercentageOfVehiclesWithinLimit {
		private final Double	lowerThreshold;
		private final Double	percentage;
		private final Double	upperThreshold;
		
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
		
		@Override
		public String toString() {
			return "PercentageOfVehiclesWithinLimit [lowerThreshold=" + lowerThreshold + ", percentage=" + percentage + ", upperThreshold=" + upperThreshold + "]";
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((lowerThreshold == null) ? 0 : lowerThreshold.hashCode());
			result = prime * result + ((percentage == null) ? 0 : percentage.hashCode());
			result = prime * result + ((upperThreshold == null) ? 0 : upperThreshold.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PercentageOfVehiclesWithinLimit other = (PercentageOfVehiclesWithinLimit) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (lowerThreshold == null) {
				if (other.lowerThreshold != null)
					return false;
			} else if (!lowerThreshold.equals(other.lowerThreshold))
				return false;
			if (percentage == null) {
				if (other.percentage != null)
					return false;
			} else if (!percentage.equals(other.percentage))
				return false;
			if (upperThreshold == null) {
				if (other.upperThreshold != null)
					return false;
			} else if (!upperThreshold.equals(other.upperThreshold))
				return false;
			return true;
		}

		private SpeedDistributionReport getOuterType() {
			return SpeedDistributionReport.this;
		}
		
	}
	
	protected class SpeedDistribution {
		private ArrayList<PercentageOfVehiclesWithinLimit> percentageList = new ArrayList<>();
		
		public ArrayList<PercentageOfVehiclesWithinLimit> getPercentageList() {
			return percentageList;
		}
	}
	
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
		List<Vehicle> vehiclesDuringPeriod = vehiclesList.stream().filter(v -> v.getPassingTime().isAfter(intervalStart) && v.getPassingTime().isBefore(intervalStop)).collect(Collectors.toList());
		Double averageSpeed = vehiclesDuringPeriod.stream().mapToDouble(Vehicle::getSpeed).average().orElse(0);
		printStream.print("\n\t\t\t\tAverage speed of all vehicles during (" + Application.DATE_TIME_FORMATTER.format(intervalStart) + " to " + Application.DATE_TIME_FORMATTER.format(intervalStop)
				+ ") = " + formatter.format(averageSpeed) + " kph");
		
		List<Vehicle> vehiclesNorthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.NORTH)).collect(Collectors.toList());
		averageSpeed = vehiclesNorthbound.stream().mapToDouble(Vehicle::getSpeed).average().orElse(0);
		printStream.print("\n\t\t\t\t\tAverage speed of vehicles northbound during (" + Application.DATE_TIME_FORMATTER.format(intervalStart) + " to "
				+ Application.DATE_TIME_FORMATTER.format(intervalStop) + ") = " + formatter.format(averageSpeed) + " kph");
		
		List<Vehicle> vehiclesSouthbound = vehiclesDuringPeriod.stream().filter(v -> v.getDirection().equals(Direction.SOUTH)).collect(Collectors.toList());
		averageSpeed = vehiclesSouthbound.stream().mapToDouble(Vehicle::getSpeed).average().orElse(0);
		printStream.print("\n\t\t\t\t\tAverage speed of vehicles southbound during (" + Application.DATE_TIME_FORMATTER.format(intervalStart) + " to "
				+ Application.DATE_TIME_FORMATTER.format(intervalStop) + ") = " + formatter.format(averageSpeed) + " kph");
		
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
		SpeedDistribution speedDistribution = new SpeedDistribution();
		
		try {
			long totalCount = vehicleList.size();
			for (int i = 0; i <= speedUpto - speedInterval; i += speedInterval) {
				Double upperSpeedThreshold = i + speedInterval;
				Double lowerSpeedThreshold = upperSpeedThreshold - 5d;
				long vehiclesWithinSpeedThresolds = vehicleList.stream().filter(vehicle -> (vehicle.getSpeed() > lowerSpeedThreshold) && (vehicle.getSpeed() <= upperSpeedThreshold)).count();
				Double percentageOfVehicles = ((double) vehiclesWithinSpeedThresolds / totalCount) * 100;
				speedDistribution.getPercentageList().add(new PercentageOfVehiclesWithinLimit(percentageOfVehicles, lowerSpeedThreshold, upperSpeedThreshold));
			}
			long vehiclesWithinSpeedThresolds = vehicleList.stream().filter(vehicle -> (vehicle.getSpeed() > speedUpto)).count();
			Double percentageOfVehicles = ((double) vehiclesWithinSpeedThresolds / totalCount) * 100;
			speedDistribution.getPercentageList().add(new PercentageOfVehiclesWithinLimit(percentageOfVehicles, 100d, null));
		} catch (Exception e) {
			Application.LOGGER.log(Level.SEVERE, "Could not find the Speed distribution.", e);
		}
		
		return speedDistribution;
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
			Application.LOGGER.log(Level.INFO, () ->String.format( "Generate report file - %s", outputFilePath));
			this.printStream = printStream;
			printStream.print("\t*** Speed Distribution Report ***\n");
			ArrayList<Vehicle> vehicleList = new ArrayList<>();
			for (Iterator<DailyAnalysis> iterator = analysis.getDailyAnalysisList().iterator(); iterator.hasNext();) {
				DailyAnalysis dayWiseAnalysis = iterator.next();
				
				vehicleList.addAll(dayWiseAnalysis.getVehiclesPassed());
			}
			printStream.print("\n\t\tSpeed distribution of vehicles in percentages\n");
			
			SpeedDistribution speedDistribution = findSpeedDistribution(vehicleList, 5d, 100d);
			
			ArrayList<PercentageOfVehiclesWithinLimit> spd = speedDistribution.getPercentageList();
			for (Iterator<PercentageOfVehiclesWithinLimit> iterator = spd.iterator(); iterator.hasNext();) {
				PercentageOfVehiclesWithinLimit percentageOfVehiclesWithinLimit = iterator.next();
				printStream.print("\n\t\t\tSpeed (");
				if (percentageOfVehiclesWithinLimit.getUpperThreshold() != null) {
					printStream.print(formatter.format(percentageOfVehiclesWithinLimit.getLowerThreshold()) + " kph to " + formatter.format(percentageOfVehiclesWithinLimit.getUpperThreshold()));
				} else {
					printStream.print("Above " + formatter.format(percentageOfVehiclesWithinLimit.getLowerThreshold()));
				}
				printStream.print(" kph ) \t= ");
				printStream.print(formatter.format(percentageOfVehiclesWithinLimit.getPercentage()) + " %");
			}
			
			Double averageSpeed = vehicleList.stream().mapToDouble(Vehicle::getSpeed).average().orElse(0);
			printStream.print("\n\n\t\tAverage speed of vehicles across all the days = " + formatter.format(averageSpeed) + " kph");
			
			printStream.print("\n\t\t\tMorning");
			computeForMorning(vehicleList, b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v));
			printStream.print("\n\t\t\tEvening");
			computeForEvening(vehicleList, b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v));
			
			computeForAllOtherPeriods(vehicleList, s -> preCalculationForAPeriodMethod(s), b -> e -> v -> calculationForEachIntervalInPeriodMethod(b, e, v), this::postCalculationForAPeriodMethod);
			
		} catch (Exception e2) {
			Application.LOGGER.log(Level.SEVERE, "Could not show Report.", e2);
		}
	}
}
