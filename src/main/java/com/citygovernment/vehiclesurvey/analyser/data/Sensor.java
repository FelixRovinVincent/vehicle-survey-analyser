package com.citygovernment.vehiclesurvey.analyser.data;

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