package main;

import java.util.Scanner;

public class TimeConverter{
	
	//converts 0:00 to 0.00
	public static float converterToFloat(String time){
		Scanner scanner = new Scanner(time);
		float hour = 0;
		float min = 0;
		scanner.useDelimiter(":");
		
		String token = scanner.next().trim();
		hour = Float.parseFloat(token);
		
		token = scanner.next().trim();
		min = Float.parseFloat(token);
		
		min = minToDec(min);
		
		scanner.close();
		return hour + min;
	}
	// converts Decimal to string
	public static String converterToString(float time){
		Integer hourInt = (int) time;
		float minFloat = time - hourInt; //since java rounds down when int
		
		String hour = hourInt.toString();
		String min = decToMin(minFloat);
		
		return hour + ":" + min;
	}
	
	//converts the minute to decimals
	private static float minToDec(float min){
		double ret = 0;
		min *= 0.01;
		
		ret = min * 1.667;
		ret = Math.round(ret * 100.0) / 100.0;
		
		float retFloat = (float) ret;
		
		return retFloat;
	}
	
	private static String decToMin(float min){
		String ret;
		min *= 60;
		Integer minInt = (int) min;
		ret = minInt.toString();
		return ret;
	}
		
	
}