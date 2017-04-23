package com.citygovernment.vehiclesurvey.analyser.data;

import java.time.LocalTime;

/**
 * Model class to represent a single data record.
 * 
 * @author Felix Rovin Vincent
 *
 */
public class SensorDataRecord {

	private Sensor sensor;
	private LocalTime localTime;
	
	
	public Sensor getSensor() {
		return sensor;
	}
	
	/**
	 * Constructor
	 */
	public SensorDataRecord() {
		super();
	}

	/**
	 * Constructor with parameters
	 * @param sensor The sensor input
	 * @param localTime Time at which vehicle is marked
	 */
	public SensorDataRecord(Sensor sensor, LocalTime localTime) {
		super();
		this.sensor = sensor;
		this.localTime = localTime;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	
	public LocalTime getLocalTime() {
		return localTime;
	}
	
	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\n\tSensorDataRecord [sensor=" + sensor + ", localTime=" + localTime + "]";
	}

}