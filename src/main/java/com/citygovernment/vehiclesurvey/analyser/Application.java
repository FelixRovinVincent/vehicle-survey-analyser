package com.citygovernment.vehiclesurvey.analyser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Application {

	public static final Logger logger = Logger.getLogger("Vehicle Survey Analyser");

	public static void main(String[] args) {
		try {
			Application app = new Application();
			app.configureLogging();
			logger.fine("Application started");
			
		//TODO Application logic

			logger.fine("End of execution.");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Application cannot proceed further. Please see the error logs for detailed info.",e);
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

			logger.setLevel(Level.FINEST);
			logger.addHandler(consoleHandler);
			logger.addHandler(fileHandler);
			logger.setUseParentHandlers(false);
		} catch (Exception e) {
			// The runtime won't show stack traces if the exception is thrown
			e.printStackTrace();
		}
	}
}
