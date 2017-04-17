package com.citygovernment.vehiclesurvey.analyser.analysis;

/**
 * Enumuration to represent direction
 * 
 * @author Felix Rovin Vincent
 *
 */
public enum Direction {
	NORTH("Northbound"), SOUTH("Southbound");
	private String value;

	public String getValue() {
		return value;
	}

	private Direction(String value) {
		this.value = value;
	}

}