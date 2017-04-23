package com.citygovernment.vehiclesurvey.analyser.display;

import java.util.List;

import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.display.report.AReport;

/**
 * Class which takes in an analysis result as input. It has methods to generate
 * reports.
 * 
 * @author Felix Rovin Vincent
 *
 */
public class ReportGenerator {
	
	/**
	 * Analysis result fed as input.
	 */
	private Analysis analysisResult;
	
	/**
	 * Constructor of ReportGenerator.
	 * 
	 * @param analysisResult
	 *            Analysis result fed as input.
	 */
	public ReportGenerator(Analysis analysisResult) {
		this.analysisResult = analysisResult;
	}
	
	/**
	 * Method to generate a single report.
	 * 
	 * @param report
	 *            The report to be generated.
	 */
	public void displayReport(AReport report) {
		report.show(analysisResult);
	}
	
	/**
	 * Method to generate a multiple reports specified as a list.
	 * 
	 * @param reportList
	 *            List of reports to be generated.
	 */
	public void displayReports(List<AReport> reportList) {
		reportList.forEach((r) -> r.show(analysisResult));
	}
	
}
