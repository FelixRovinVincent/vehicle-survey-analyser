package com.citygovernment.vehiclesurvey.analyser.analysis;

import java.time.LocalTime;

/**
 * Model class to represent a vehicle
 * 
 * @author Felix Rovin Vincent
 *
 */
public class Vehicle {

	private LocalTime passingTime;
	private Direction direction;
	private float speed;
	
	public LocalTime getPassingTime() {
		return passingTime;
	}
	
	public void setPassingTime(LocalTime passingTime) {
		this.passingTime = passingTime;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float f) {
		this.speed = f;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vehicle [passingTime=" + passingTime + ", direction=" + direction + ", speed=" + speed + "]";
	}
	
	
}