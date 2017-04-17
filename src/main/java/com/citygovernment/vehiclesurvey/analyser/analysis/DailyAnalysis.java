package com.citygovernment.vehiclesurvey.analyser.analysis;

import java.util.ArrayList;

/**
 * Model Class to represent analysis per day.
 * @author Felix Rovin Vincent
 *
 */
public class DailyAnalysis {

	private int day;
	private ArrayList<Vehicle> vehiclesPassed = new ArrayList<>() ;
	
	public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	public ArrayList<Vehicle> getVehiclesPassed() {
		return vehiclesPassed;
	}

	@Override
	public String toString() {
		return "DailyAnalysis [day=" + day + ", vehiclesPassed=" + vehiclesPassed + "]";
	}
	
	
}