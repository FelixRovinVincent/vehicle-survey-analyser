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

public abstract class AReport {
		
	protected void computeForAllOtherPeriods(List<Vehicle> vehiclesList, Function<Integer, Function<ReportPeriod, Function<LocalTime, Function<LocalTime, Consumer<List<Vehicle>>>>>> calculationMethod,
			Integer numberOfDays) {}

	protected List<Vehicle> computeForMorningEvening(List<Vehicle> vehicleList, Direction direction, int numberOfDays) {
		List<Vehicle> directionWiseVehiclesList = new ArrayList();
		
		return directionWiseVehiclesList;
	}
	
	public abstract void show(Analysis analysis);
}
