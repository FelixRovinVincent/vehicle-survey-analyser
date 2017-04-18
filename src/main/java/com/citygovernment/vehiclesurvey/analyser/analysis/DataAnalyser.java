package com.citygovernment.vehiclesurvey.analyser.analysis;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import com.citygovernment.vehiclesurvey.analyser.Application;
import com.citygovernment.vehiclesurvey.analyser.data.Sensor;
import com.citygovernment.vehiclesurvey.analyser.data.SensorData;
import com.citygovernment.vehiclesurvey.analyser.data.SensorDataRecord;

/**
 * Instance of this class is used to perform Data analysis.
 * 
 * @author Felix Rovin Vincent
 *
 */
public class DataAnalyser {
	
	public static final float	AVERAGE_WHEEL_BASE	= 2.5f;
	public static final int		MAX_SPEED_LIMIT		= 60;
	public static final int		NUMBER_OF_AXLES		= 2;
		
	/**
	 * Method to perform analysis.
	 * 
	 * @param data
	 *            Data to be analysed.
	 * @return Analysis result of Analysis
	 */
	public Analysis analyse(SensorData data) {
		
		Analysis analysis = new Analysis();
		try {
			DailyAnalysis currentDailyAnalysis = new DailyAnalysis();
			currentDailyAnalysis.setDay(1);
			analysis.getDailyAnalysisList().add(currentDailyAnalysis);
			
			ArrayList<SensorDataRecord> records = data.getDataRecordList();
			
			int count = 1, index = 0;
			SensorDataRecord lastRecord = null;
			
			Vehicle currentVehicle = new Vehicle();
			LocalTime firstAxleHit = null;
			
			for (Iterator<SensorDataRecord> iterator = records.iterator(); iterator.hasNext();) {
				SensorDataRecord sensorDataRecord = (SensorDataRecord) iterator.next();
				index++;
				
				if (lastRecord == null) {
					// Do nothing
				} else if (sensorDataRecord.getLocalTime().isBefore(lastRecord.getLocalTime())) {
					currentDailyAnalysis = new DailyAnalysis();
					currentDailyAnalysis.setDay(analysis.getDailyAnalysisList().size() + 1);
					analysis.getDailyAnalysisList().add(currentDailyAnalysis);					
				}
				
				switch (count++) {
					case 1:
						if (!sensorDataRecord.getSensor().getValue().contentEquals("A")) {
							if (lastRecord != null) {
								Application.LOGGER.info("Last sensorDataRecord = " + lastRecord);
							}
							Application.LOGGER.info("End of valid data at line " + index + "; " + sensorDataRecord);
							index = 0;
							break;
						} else {
							firstAxleHit = sensorDataRecord.getLocalTime();
						}
						break;
					case 2:
						if (sensorDataRecord.getSensor().getValue().contentEquals("A")) {
							currentVehicle.setDirection(Direction.NORTH);
							setPropertiesOfVehicle(firstAxleHit, sensorDataRecord, currentVehicle, currentDailyAnalysis);
							currentVehicle = new Vehicle();
							count = 1;
						} else {
							currentVehicle.setDirection(Direction.SOUTH);
							firstAxleHit = sensorDataRecord.getLocalTime();
						}
						break;
					case 3:
						if (!sensorDataRecord.getSensor().getValue().contentEquals("A")) {
							throw new IllegalArgumentException("Data file is corrupt.");
						}
						break;
					case 4:
						if (!sensorDataRecord.getSensor().getValue().contentEquals("B")) {
							throw new IllegalArgumentException("Data file is corrupt.");
						} else {
							setPropertiesOfVehicle(firstAxleHit, sensorDataRecord, currentVehicle, currentDailyAnalysis);
							currentVehicle = new Vehicle();
							count = 1;
						}
						break;
					
					default:
						throw new RuntimeException("Count can only be from 1 to 4.");
				}
				
				if (index == 0) {
					break;
				}
				lastRecord = sensorDataRecord;
			}
		} catch (Exception e) {
			Application.LOGGER.log(Level.SEVERE, "Could not perform analysis!", e);
		}
		return analysis;
	}
	
	/**
	 * Set properties of each vehicle.
	 * 
	 * @param firstAxleHit
	 *            Time of sensing a vehicle for the first time
	 * @param sensorDataRecord
	 *            Individual record
	 * @param currentVehicle
	 *            Record considered at present
	 * @param currentDailyAnalysis
	 *            DailyAnalysis considered at present
	 */
	private void setPropertiesOfVehicle(LocalTime firstAxleHit, SensorDataRecord sensorDataRecord, Vehicle currentVehicle, DailyAnalysis currentDailyAnalysis) {
		Duration duration = Duration.between(firstAxleHit, sensorDataRecord.getLocalTime());
		currentVehicle.setPassingTime(firstAxleHit.plus(duration.dividedBy(NUMBER_OF_AXLES)));
		float speed = (AVERAGE_WHEEL_BASE / duration.toMillis())*3600;
		currentVehicle.setSpeed(new Float(String.format("%.2f", speed)));
		currentDailyAnalysis.getVehiclesPassed().add(currentVehicle);
	}
}