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

/**
 * Class representing report showing peak volume times.
 * 
 * @author Felix Rovin Vincent
 *
 */
public class PeakVolumeTimesReport extends AReport{

	/* (non-Javadoc)
	 * @see com.citygovernment.vehiclesurvey.analyser.display.report.AReport#show(com.citygovernment.vehiclesurvey.analyser.analysis.Analysis)
	 */
	@Override
	public void show(Analysis analysis) {
		System.out.println("\n\t*** Peak Volume Times Report ***");
		//TODO
	}
	
	/**
	 * Find peak time.
	 * @param numberOfDays Number of days data is recorded.
	 * @param period report period.
	 * @param intervalStart Interval Start.
	 * @param intervalStop Interval Stop.
	 * @param vehiclesList List of vehicles on which count is performed.
	 */
	private void findPeakTime(long numberOfDays, ReportPeriod period, LocalTime intervalStart, LocalTime intervalStop, List<Vehicle> vehiclesList) {}
	
}
