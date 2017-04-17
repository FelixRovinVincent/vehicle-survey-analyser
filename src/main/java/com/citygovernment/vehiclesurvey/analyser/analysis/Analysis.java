package com.citygovernment.vehiclesurvey.analyser.analysis;

import java.io.File;
import java.util.ArrayList;

/**
 * Model Class to represent the whole analysis result.
 * 
 * @author Felix Rovin Vincent
 *
 */
public class Analysis {

	private ArrayList<DailyAnalysis> dailyAnalysisList = new ArrayList<>();
	private File file;
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public ArrayList<DailyAnalysis> getDailyAnalysisList() {
		return dailyAnalysisList;
	}	

}