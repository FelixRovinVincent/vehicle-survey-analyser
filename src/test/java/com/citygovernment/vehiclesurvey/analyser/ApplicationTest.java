package com.citygovernment.vehiclesurvey.analyser;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ApplicationTest {
	/**
	 * This method tests whether the application differentiates data of different days
	 * 
	 * @param strDataMultipleDays
	 */
	@Test(dataProvider = "sampleDataWithTimeDropAfterDay")
	public void testGroupingDataIntoDays(String strDataMultipleDays) {
		System.out.println(strDataMultipleDays);
	}
	
	/**
	 * This method tests the following scenario:-
	 * 
	 * 
	 * The first line above represents a pair of tires driving by at 12:04:28am.
	 * 
	 * The second line represents another pair of tires going by 142ms later
	 * (almost certainly the 2nd axle of the car).
	 * 
	 * Lines 3-6 above represent a second car going in the other direction.
	 * 
	 * The first set of tires hit the 'A' sensor at 12:10:04am, and then hit the
	 * 'B' sensor 3ms later.
	 * 
	 * The second set of tires then hit the 'A' sensor 171ms later, and then the
	 * 'B' sensor 4ms later.
	 * 
	 * @param strData
	 */
	@Test(dataProvider = "sampleLines")
	public void testParsingLine(String strData) {
		System.out.println(strData);
	}
	
	@DataProvider
	public Object[][] sampleLines() {
		return new Object[][] {
				new Object[] {
						"A268981 \n" + "A269123\n" + "A604957\n" + "B604960\n" + "A605128\n" + "B605132\n" + "A1089807\n" + "B1089810\n" + "A1089948\n" + "B1089951"
				}
		};
	}
	
	@DataProvider
	public Object[][] sampleDataWithTimeDropAfterDay() {
		return new Object[][] {
				new Object[] {
						"A86328771 \n" + "B86328774 \n" + "A86328899 \n" + "B86328902 \n" + "A582668 \n" + "B582671 \n" + "A582787 \n" + "B582789"
				}
		};
	}
	
}
