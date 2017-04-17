package com.citygovernment.vehiclesurvey.analyser.analysis;

import java.time.LocalTime;

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

	@Override
	public String toString() {
		return "Vehicle [passingTime=" + passingTime + ", direction=" + direction + ", speed=" + speed + "]";
	}
	
	
}