package com.citygovernment.vehiclesurvey.analyser.analysis;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.citygovernment.vehiclesurvey.analyser.data.Sensor;
import com.citygovernment.vehiclesurvey.analyser.data.SensorData;
import com.citygovernment.vehiclesurvey.analyser.data.SensorDataRecord;

public class DataAnalyserTest {
	
	static final SensorData sensorData = new SensorData();
	
	@BeforeClass
	public void beforeClass() {
		List<SensorDataRecord> testData = Arrays.asList(
				new SensorDataRecord(Sensor.A, LocalTime.MIDNIGHT.plus(268981, ChronoUnit.MILLIS)),
				new SensorDataRecord(Sensor.A, LocalTime.MIDNIGHT.plus(269123, ChronoUnit.MILLIS)), new SensorDataRecord(Sensor.A, LocalTime.MIDNIGHT.plus(604957, ChronoUnit.MILLIS)),
				new SensorDataRecord(Sensor.B, LocalTime.MIDNIGHT.plus(604960, ChronoUnit.MILLIS)), new SensorDataRecord(Sensor.A, LocalTime.MIDNIGHT.plus(605128, ChronoUnit.MILLIS)),
				new SensorDataRecord(Sensor.B, LocalTime.MIDNIGHT.plus(605132, ChronoUnit.MILLIS))
				);
		sensorData.getDataRecordList().addAll(testData);
	}
	
	@Test(enabled = true)
	public void analyse() {
		DataAnalyser dataAnalyser = new DataAnalyser();
		Analysis analysis = dataAnalyser.analyse(sensorData);
		System.err.println(sensorData);
		System.err.println(analysis);
		Assert.assertEquals(analysis.getDailyAnalysisList().size(), 1);
		ArrayList<Vehicle> vehicleList = analysis.getDailyAnalysisList().get(0).getVehiclesPassed();
		Assert.assertEquals(vehicleList.size(), 2);
		Assert.assertEquals(vehicleList.get(0).getDirection(), Direction.NORTH);
		Assert.assertEquals(vehicleList.get(1).getDirection(), Direction.SOUTH);
		// Average Speed can be calculated by using the formula: V = D/T
		// (2.5m/142ms)*3600 = 63.38 KM/hr
		Assert.assertEquals(vehicleList.get(0).getSpeed(), 63.38f);
		// (2.5m/172ms)*3600 = 52.33 KM/hr
		Assert.assertEquals(vehicleList.get(1).getSpeed(), 52.33f);
	}
	
}
