package com.citygovernment.vehiclesurvey.analyser.parser;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Stream;

import com.citygovernment.vehiclesurvey.analyser.Application;
import com.citygovernment.vehiclesurvey.analyser.data.Sensor;
import com.citygovernment.vehiclesurvey.analyser.data.SensorData;
import com.citygovernment.vehiclesurvey.analyser.data.SensorDataRecord;

/**
 * Class to parse data file produced by Vehicle Counter device.
 *
 * @author Felix Rovin Vincent
 *
 */
public class PneumaticDataFileParser {
	
	/**
	 * Whole data containing all data lines
	 */
	private SensorData sensorData;
	
	/**
	 * Parse a single line to a record.
	 * 
	 * @param dataLine
	 *            each line of data
	 * @return
	 */
	private SensorDataRecord createDataRecord(String dataLine) {
		SensorDataRecord sensorDataRecord = new SensorDataRecord();
		try {
			String strSensor = dataLine.substring(0, 1);
			switch (strSensor) {
				case "A":
					sensorDataRecord.setSensor(Sensor.A);
					break;
				case "B":
					sensorDataRecord.setSensor(Sensor.B);
					break;
				
				default:
					throw new IllegalArgumentException("No such Sensor - " + strSensor);
			}
			
			long numberOfMillisSinceMidnight = Long.parseUnsignedLong(dataLine.substring(1).trim());
			LocalTime localTime = LocalTime.MIDNIGHT.plus(numberOfMillisSinceMidnight, ChronoUnit.MILLIS);
			sensorDataRecord.setLocalTime(localTime);
		} catch (NumberFormatException e) {
			Application.LOGGER.log(Level.SEVERE, "Incorrect data in line -> " + dataLine, e);
			throw e;
		}
		
		return sensorDataRecord;
	}
	
	/**
	 * Parse all files
	 * 
	 * @param dataFolderName
	 *            name of the folder containing data files
	 * @param analyseMethod
	 *            method to analyse a single file
	 */
	public void parseAllFiles(String dataFolderName, Consumer<SensorData> analyseMethod) {		
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(".//" + dataFolderName + "//"));) {
			
			for (Path path : dirStream) {
				// read file into stream, try-with-resources
				try (Stream<String> stream = Files.lines(path)) {
					Application.LOGGER.info("**********************************************************************************");
					Application.LOGGER.log(Level.INFO, () -> String.format("Data file - \"%s\"",path.getFileName()));
					Application.LOGGER.info("**********************************************************************************");
					
					SensorData sensorData = this.parseDataStream(stream);
					analyseMethod.accept(sensorData);
					
				} catch (Exception e) {
					Application.LOGGER.log(Level.SEVERE, "Could not read File - " + path.toString() + "!", e);
				}
				
			}
		} catch (IOException e1) {
			Application.LOGGER.log(Level.SEVERE, "Incorrect Data folder.", e1);
		}
	}
	
	/**
	 * Parse a single data file.
	 * 
	 * @param stream
	 * @return SensorData
	 */
	private SensorData parseDataStream(Stream<String> stream) {
		sensorData = new SensorData();
		sensorData.getDataRecordList().clear();
		
		stream.forEachOrdered((strLine) -> {
			SensorDataRecord sensorDataRecord = createDataRecord(strLine);
			sensorData.getDataRecordList().add(sensorDataRecord);
		});
		
		return sensorData;
	}
	
}
