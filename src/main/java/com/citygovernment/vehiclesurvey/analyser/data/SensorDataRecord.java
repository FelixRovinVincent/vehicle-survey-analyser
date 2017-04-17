package com.citygovernment.vehiclesurvey.analyser.data;

import java.time.LocalTime;

public class SensorDataRecord {

	private Sensor sensor;
	private LocalTime localTime;
	
	public Sensor getSensor() {
		return sensor;
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
	
	@Override
	public String toString() {
		return "SensorDataRecord [sensor=" + sensor + ", localTime=" + localTime + "]";
	}

}