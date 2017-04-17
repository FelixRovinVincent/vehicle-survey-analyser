package com.citygovernment.vehiclesurvey.analyser.display;

import java.util.List;

import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.display.report.AReport;

public class ReportGenerator {
	
	private Analysis analysisResult;
	
	public ReportGenerator(Analysis analysisResult) {
		this.analysisResult =analysisResult;
	}
	
	public void displayReport(AReport report) {
		report.show(analysisResult);		
	}
	
	public void displayReports(List<AReport> reportList) {
//		reportList.forEach((r)->r.show(analysisResult));			
	}

}
