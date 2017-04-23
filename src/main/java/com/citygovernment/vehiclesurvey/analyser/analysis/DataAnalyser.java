package com.citygovernment.vehiclesurvey.analyser.analysis;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import com.citygovernment.vehiclesurvey.analyser.Application;
import com.citygovernment.vehiclesurvey.analyser.data.SensorData;
import com.citygovernment.vehiclesurvey.analyser.data.SensorDataRecord;

/**
 * Instance of this class is used to perform Data analysis or interpreting data to object model.
 * 
 * @author Felix Rovin Vincent
 *
 */
public class DataAnalyser {
	
	/**
	 * Custom exception to indicate invalid values while dividing data into two or four lines belonging to a vehicle. 
	 * @author lordlion
	 *
	 */
	public class InvalidLineCountException extends Exception {

		/**
		 * Constructor.
		 * @param message Message.
		 */
		public InvalidLineCountException(String message) {
			super(message);
		}
		
	}

	public static final float	AVERAGE_WHEEL_BASE	= 2.5f;
	public static final int		MAX_SPEED_LIMIT		= 60;
	public static final int		NUMBER_OF_AXLES		= 2;
	
	/**
	 * Method to perform analysis. This would interpret the data records to vehicle instances grouped day-wise.
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
				SensorDataRecord sensorDataRecord = iterator.next();
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
								Application.LOGGER.log(Level.INFO,"Last sensorDataRecord = %s" , lastRecord);
							}
							Application.LOGGER.log(Level.INFO,"End of valid data at line %s" ,String.format("%d; %s.",index , sensorDataRecord.toString()) );
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
						throw new InvalidLineCountException("Count can only be from 1 to 4.");
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
		float speed = (AVERAGE_WHEEL_BASE / duration.toMillis()) * 3600;
		currentVehicle.setSpeed(new Float(String.format("%.2f", speed)));
		currentDailyAnalysis.getVehiclesPassed().add(currentVehicle);
	}
}