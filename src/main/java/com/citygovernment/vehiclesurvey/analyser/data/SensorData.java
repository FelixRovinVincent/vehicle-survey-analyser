package com.citygovernment.vehiclesurvey.analyser.data;

import java.util.ArrayList;

/**
 * Model Class to represent a collection of data records
 * 
 * @author Felix Rovin Vincent
 *
 */
public class SensorData {

	/**
	 * Collection of data records.
	 */
	private ArrayList<SensorDataRecord> dataRecordList = new ArrayList<>();

	public ArrayList<SensorDataRecord> getDataRecordList() {
		return dataRecordList;
	}

	@Override
	public String toString() {
		return "SensorData [dataRecordList=" + dataRecordList + "]";
	}

	
}