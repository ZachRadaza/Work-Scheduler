package main;

import java.util.Scanner;

import javax.swing.JOptionPane;

public class TimeConverter{
	
	//converts 0:00 to 0.00
	public static float converterToFloat(String time){
		try{
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
		} catch(NumberFormatException e){
			/*
			System.err.println("Incorrect inputs");
			System.err.println(e.getMessage());
			e.printStackTrace();
			*/
			JOptionPane.showMessageDialog(null, "There has been an error with the files uploaded", "Data Misinput", JOptionPane.INFORMATION_MESSAGE);
			return 0f;
		}
	}
	// converts Decimal to string
	public static String converterToString(float time){
		try{
			Integer hourInt = (int) time;
			float minFloat = time - hourInt; //since java rounds down when int
			
			String hour = hourInt.toString();
			String min = decToMin(minFloat);
			if(hourInt <= 9){
				hour = "0" + hour;
			}
			if(minFloat <= 0.09f){
				min = "0" + min;
			}
		return hour + ":" + min;
		} catch(NumberFormatException e){
			/*
			System.err.println("Incorrect inputs");
			System.err.println(e.getMessage());
			e.printStackTrace();
			*/
			JOptionPane.showMessageDialog(null, "There has been an error with the files uploaded", "Data Misinput", JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
	}
	
	public static boolean validateString(String time){
		try{
			if(time.length() == 0) return false;
			
			Character temp = ' ';
			
			for(int i = 0; i < time.length(); i++){
				temp = time.charAt(i);
				
				if(temp.equals('-')) break;
				if(i == time.length() - 1 && !temp.equals('-')) return false;
			}
			
			Scanner scannerBig = new Scanner(time);
			scannerBig.useDelimiter(",");
			boolean ret = true;
			
			while(scannerBig.hasNext()){
				Scanner scanner = new Scanner(scannerBig.next().trim());
				String time1 = "";
				String time2 = "";
				scanner.useDelimiter("-");
				
				String token = scanner.next().trim();
				time1 = token;
				
				token = scanner.next().trim();
				time2 = token;
				
				scanner.close();
				
				if(time1.length() > 5 || time1.length() < 4){
					ret = false;
					break;
				}
				if(time2.length() > 5 || time2.length() < 4){
					ret = false;
					break;
				}
				
				if(!validateStringHelper(time1)) { 
					ret = false;
					break;
				}
				if(!validateStringHelper(time2)) { 
					ret = false;
					break;
				}
			}
			scannerBig.close();
			return ret;
		} catch(NumberFormatException e){
			System.err.println("Incorrect inputs");
			System.err.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	//validates individual times
	private static boolean validateStringHelper(String time){
		try{
			Character temp = ' ';
			
			for(int i = 0; i < time.length(); i++){
				temp = time.charAt(i);
				
				if(temp.equals(':')) break;
				if(i == time.length() - 1 && !temp.equals(':')) return false;
			}
			
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
			
			if(hour < 0 || hour > 24) return false;
			if(min < 0 || min > 0.59) return false;
			
			return true;
		} catch(NumberFormatException e){
			System.err.println("Incorrect inputs");
			System.err.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
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