package com.citygovernment.vehiclesurvey.analyser.display;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * The Enumeration which includes all possible time slices to perform analysis.
 * 
 * @author Felix Rovin Vincent
 *
 */
public enum ReportPeriod {
	AM_PM(Duration.ZERO.plus(1, ChronoUnit.HALF_DAYS)),	
	HOURLY(Duration.ZERO.plus(1, ChronoUnit.HOURS)),
	HALF_HOURLY(Duration.ZERO.plus(30, ChronoUnit.MINUTES)),	
	PER_20_MINUTES(Duration.ZERO.plus(20, ChronoUnit.MINUTES)),
	PER_15_MINUTES(Duration.ZERO.plus(15, ChronoUnit.MINUTES))
	;
	
	/**
	 * The time duration of the time slice.
	 */
	private transient Duration duration;
	
	ReportPeriod(Duration duration) {
		this.duration = duration;
	}
	
	public Duration getDuration() {
		return duration;
	}
	
}
