package com.citygovernment.vehiclesurvey.analyser.data;

/**
 * Enumuration to represent Sensor
 * 
 * @author Felix Rovin Vincent
 *
 */
public enum Sensor {
	A("A"), B("B");
	private String value;

	public String getValue() {
		return value;
	}

	private Sensor(String value) {
		this.value = value;
	}

}