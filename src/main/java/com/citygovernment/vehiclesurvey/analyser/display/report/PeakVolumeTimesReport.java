package com.citygovernment.vehiclesurvey.analyser.display.report;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.citygovernment.vehiclesurvey.analyser.Application;
import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.DailyAnalysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.Direction;
import com.citygovernment.vehiclesurvey.analyser.analysis.Vehicle;
import com.citygovernment.vehiclesurvey.analyser.display.ReportPeriod;

public class PeakVolumeTimesReport extends AReport{

	@Override
	public void show(Analysis analysis) {
		System.out.println("\n\t*** Peak Volume Times Report ***");
		//TODO
	}
	
	private void findPeakTime(long numberOfDays, ReportPeriod period, LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList) {}
	
}
