package com.citygovernment.vehiclesurvey.analyser.data;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<SensorDataRecord> getDataRecordList() {
		return dataRecordList;
	}
	
	@Override
	public String toString() {
		return "\nSensorData [dataRecordList=" + dataRecordList + "\n\t]";
	}
	
}