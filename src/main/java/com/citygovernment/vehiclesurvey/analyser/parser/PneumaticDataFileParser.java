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

public class PneumaticDataFileParser {
		
	private SensorData sensorData;

	private SensorData parseDataFile(Stream<String> stream) {
		sensorData = new SensorData();
//		sensorData.getDataRecordList().clear();
//		
//		stream.forEachOrdered(this::createDataRecord);
		
		return sensorData;
	}
	
	public void parseAllFiles(String dataFolderName, Consumer<SensorData> analyseMethod) {
//		try {
//			Path dataFilePath = Paths.get(".//" + dataFolderName + "//");
//			
//			DirectoryStream<Path> dirStream;
//			
//			dirStream = Files.newDirectoryStream(dataFilePath);
//			
//			for (Path path : dirStream) {
//				// read file into stream, try-with-resources
//				try (Stream<String> stream = Files.lines(path)) {
//					System.out.println("**********************************************************************************");
//					System.out.println("Data file - \"" + path.getFileName()+"\"");
//					System.out.println("**********************************************************************************");
//					
//					SensorData sensorData = this.parseDataFile(stream);
//					
//					// for (Iterator<SensorDataRecord> iterator =
//					// data.getRecord().iterator(); iterator.hasNext();) {
//					// SensorDataRecord sensorDataRecord = (SensorDataRecord)
//					// iterator.next();
//					// logger.fine(sensorDataRecord.toString());
//					// }
//					
//					analyseMethod.accept(sensorData);
//					
//				} catch (Exception e) {
//					Application.LOGGER.log(Level.SEVERE, "Could not read File - " + path.toString() + "!", e);
//				}
//				
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}
	
	private void createDataRecord(String dataLine) {
		
//		SensorDataRecord sensorDataRecord = new SensorDataRecord();
//		
//		String strSensor = dataLine.substring(0, 1);
//		switch (strSensor) {
//			case "A":
//				sensorDataRecord.setSensor(Sensor.A);
//				break;
//			case "B":
//				sensorDataRecord.setSensor(Sensor.B);
//				break;
//			
//			default:
//				throw new IllegalArgumentException("No such Sensor - " + strSensor);
//		}
//		
//		long numberOfMillisSinceMidnight = Long.parseUnsignedLong(dataLine.substring(1));
//		LocalTime localTime = LocalTime.MIDNIGHT.plus(numberOfMillisSinceMidnight, ChronoUnit.MILLIS);
//		sensorDataRecord.setLocalTime(localTime);
//		
//		sensorData.getDataRecordList().add(sensorDataRecord);
	}
	
}
