package com.citygovernment.vehiclesurvey.analyser.analysis;

import java.util.ArrayList;

/**
 * Model Class to represent the whole analysis result.
 * 
 * @author Felix Rovin Vincent
 *
 */
public class Analysis {

	/**
	 * List of day-wise analysis results. 
	 */
	private ArrayList<DailyAnalysis> dailyAnalysisList = new ArrayList<>();
		
	public ArrayList<DailyAnalysis> getDailyAnalysisList() {
		return dailyAnalysisList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Analysis [dailyAnalysisList=" + dailyAnalysisList + "]";
	}	

}