package com.citygovernment.vehiclesurvey.analyser.analysis;

import java.time.LocalTime;

/**
 * Model class to represent a vehicle
 * 
 * @author Felix Rovin Vincent
 *
 */
public class Vehicle {
	
	/**
	 * Direction of the vehicle.
	 */
	private Direction	direction;
	/**
	 * The time of day in which the vehicle has passed over the Pneumatic sensors of Vehicle counter. 
	 */
	private LocalTime	passingTime;
	/**
	 * Speed of the vehicle at the time of passing.
	 */
	private float		speed;
	
	public Direction getDirection() {
		return direction;
	}
	
	public LocalTime getPassingTime() {
		return passingTime;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void setPassingTime(LocalTime passingTime) {
		this.passingTime = passingTime;
	}
	
	public void setSpeed(float f) {
		this.speed = f;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vehicle [passingTime=" + passingTime + ", direction=" + direction + ", speed=" + speed + "]";
	}
	
}