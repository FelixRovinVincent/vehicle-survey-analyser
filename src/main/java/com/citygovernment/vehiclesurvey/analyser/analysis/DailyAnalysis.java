package com.citygovernment.vehiclesurvey.analyser.analysis;

import java.util.ArrayList;

/**
 * Model Class to represent analysis per day.
 * 
 * @author Felix Rovin Vincent
 *
 */
public class DailyAnalysis {
	
	/**
	 * Day count.
	 */
	private int					day;
	/**
	 * List of vehicles passed in a particular day.
	 */
	private ArrayList<Vehicle>	vehiclesPassed	= new ArrayList<>();
	
	public int getDay() {
		return day;
	}
	
	public ArrayList<Vehicle> getVehiclesPassed() {
		return vehiclesPassed;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DailyAnalysis [day=" + day + ", vehiclesPassed=" + vehiclesPassed + "]";
	}
	
}