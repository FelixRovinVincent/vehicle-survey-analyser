package com.citygovernment.vehiclesurvey.analyser.data;

/**
 * Enumuration to represent Sensor Type.
 * 
 * @author Felix Rovin Vincent
 *
 */
public enum Sensor {
	A("A"), B("B");
	private String value;

	private Sensor(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}