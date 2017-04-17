package com.citygovernment.vehiclesurvey.analyser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.citygovernment.vehiclesurvey.analyser.analysis.Analysis;
import com.citygovernment.vehiclesurvey.analyser.analysis.DataAnalyser;
import com.citygovernment.vehiclesurvey.analyser.display.ReportGenerator;
import com.citygovernment.vehiclesurvey.analyser.display.report.AReport;
import com.citygovernment.vehiclesurvey.analyser.display.report.AverageVehicleCountReport;
import com.citygovernment.vehiclesurvey.analyser.display.report.DayWiseVehicleCountReport;
import com.citygovernment.vehiclesurvey.analyser.display.report.InterVehicularDistanceReport;
import com.citygovernment.vehiclesurvey.analyser.display.report.PeakVolumeTimesReport;
import com.citygovernment.vehiclesurvey.analyser.display.report.SpeedDistributionReport;
import com.citygovernment.vehiclesurvey.analyser.parser.PneumaticDataFileParser;

public class Application {
	
	public static final Logger				LOGGER				= Logger.getLogger("Vehicle Survey Analyser");
	public static final DateTimeFormatter	DATE_TIME_FORMATTER	= DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public static void main(String[] args) {
		try {
			Application app = new Application();
			app.configureLogging();
			LOGGER.fine("Application started");
			
			String dataFolderName = "data";
			
			DataAnalyser dataAnalyser = new DataAnalyser();
			
			PneumaticDataFileParser dataFileParser = new PneumaticDataFileParser();
			dataFileParser.parseAllFiles(dataFolderName, (data) ->
			
			{
				
				Analysis analysisResult = dataAnalyser.analyse(data);
				
				ReportGenerator reportGenerator = new ReportGenerator(analysisResult);
				
				reportGenerator.displayReport(new DayWiseVehicleCountReport());
				
				List<AReport> reportsToGenerate = new ArrayList<>(
						Arrays.asList(new AverageVehicleCountReport(), new PeakVolumeTimesReport(), new SpeedDistributionReport(), new InterVehicularDistanceReport()));
				reportGenerator.displayReports(reportsToGenerate);
			});
			
			LOGGER.fine("Application End");
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Application cannot proceed further. Please see the error logs for detailed info.", e);
		}
	}
	
	public void configureLogging() {
		try {
			// Programmatic log configuration
			String pattern = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s] (%2$s) %5$s %6$s%n";
			System.setProperty("java.util.logging.SimpleFormatter.format", pattern);
			SimpleFormatter formatter = new SimpleFormatter();
			
			final ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(Level.FINEST);
			consoleHandler.setFormatter(formatter);
			
			String filePathStr = "./logs/Application_log";
			Path filePath = Paths.get(filePathStr);
			
			// Make sure the directories exist
			Files.createDirectories(filePath.getParent());
			
			final FileHandler fileHandler = new FileHandler(filePathStr, 1073741824, 10, true);
			fileHandler.setLevel(Level.ALL);
			fileHandler.setFormatter(formatter);
			
			LOGGER.setLevel(Level.FINEST);
			LOGGER.addHandler(consoleHandler);
			LOGGER.addHandler(fileHandler);
			LOGGER.setUseParentHandlers(false);
		} catch (Exception e) {
			// The runtime won't show stack traces if the exception is thrown
			e.printStackTrace();
		}
	}
}
