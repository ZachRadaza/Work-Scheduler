package main;

import java.util.Scanner;

public class Employee{
	
	private String nameFirst;
	private String nameLast;
	public int idNumber;
	
	private String[] stationsCanWork; //stations he can work at
	
	public String[] station; //station employee will work at
	public String[] station2; // in case several shifts in a day
	public String[] station3;
	
	private boolean availability[]; // {Sun, Mon, Tue, Wed, Thur, Fri, Sat} post sort
	
	private float[][] availabilityHours; //{open, close},{open, close}... by days, [day][time]
	private float[][] availabilityHours2; // in case availability split
	private boolean availabilityHours2Exist;
	private float[][] availabilityHours3;
	private boolean availabilityHours3Exist;
	
	public float[][] hours; //final hours working, army time
	
	public Employee(String nameFirst, String nameLast, int numberOfStations, String stationsCanWorkRaw, String availSun, String availMon, String availTue, String availWed, String availThur, String availFri, String availSat){
		this.nameFirst = nameFirst;
		this.nameLast = nameLast;
		
		this.stationsCanWork = new String[8];
		setStationsCanWork(stationsCanWorkRaw);
		
		this.station = new String[7]; // 7 days in a week
		this.station2 = new String[7];
		this.station3 = new String[7];
		
		this.availabilityHours = new float[7][2];
		this.availabilityHours2Exist = false;
		this.availabilityHours3Exist = false;
		this.availability = new boolean[7];
		for(int i = 0; i < 7; i++){
			availability[i] = true;
		}
		this.setAvailabiilyPerDay(availSun, availMon, availTue, availWed, availThur, availFri, availSat);

		hours = new float[7][2];
	}
	
	//methods to assist object creation
	private void setStationsCanWork(String stationsStringRaw){ //sorts stations you can work in from read file
		Scanner scanner = new Scanner(stationsStringRaw);
		scanner.useDelimiter(",");
		int i = 0;
		while(scanner.hasNext()){
			String token = scanner.next().trim();
			stationsCanWork[i] = token;
			i++;
		}
		scanner.close();
	}
	
	private void setAvailabiilyPerDay(String day0, String day1, String day2, String day3, String day4, String day5, String day6){ //sorts availability in time from read file
		String days[] = {day0, day1, day2, day3, day4, day5, day6};
		for(int i = 0; i < 7; i++){
			String reader = days[i];
			Scanner scannerSh = new Scanner(reader); // separates different times
			scannerSh.useDelimiter(",");
			String tokenSh = scannerSh.next();
			Scanner scannerOC = new Scanner(tokenSh); // separates open and closing shifts
			scannerOC.useDelimiter("-");
			String token = scannerOC.next().trim();
			
			availabilityHours[i][0] = TimeConverter.converterToFloat(token);
			token = scannerOC.next().trim();
			availabilityHours[i][1] = TimeConverter.converterToFloat(token);
			if(availabilityHours[i][0] == 0f && availabilityHours[i][1] == 0f) availability[i] = false;
			//if there are more shifts
			if(scannerSh.hasNext()){
				this.availabilityHours2 = new float[7][2];
				token = scannerOC.next().trim();
				availabilityHours2[i][0] = TimeConverter.converterToFloat(token);
				token = scannerOC.next().trim();
				availabilityHours2[i][1] = TimeConverter.converterToFloat(token);
				availabilityHours2Exist = true;
			}
			if(scannerSh.hasNext()){
				this.availabilityHours3 = new float[7][2];
				token = scannerOC.next().trim();
				availabilityHours3[i][0] = TimeConverter.converterToFloat(token);
				token = scannerOC.next().trim();
				availabilityHours3[i][1] = TimeConverter.converterToFloat(token);
				availabilityHours3Exist = true;
			}
			scannerOC.close();
			scannerSh.close();
		}
	}
	
	//getters
	public String getNameFirst(){
		return this.nameFirst;
	}
	
	public String getNameLast(){
		return this.nameLast;
	}
	
	public boolean getAvailabilityDay(int i){
		return availability[i];
	}
	
	public float getAvailabilityHours(int day, int openClose){
		return availabilityHours[day][openClose];
	}
	
	public float getAvailabilityHours2(int day, int openClose){
		return availabilityHours[day][openClose];
	}
	
	public float getAvailabilityHours3(int day, int openClose){
		return availabilityHours[day][openClose];
	}
	
	public boolean getAvailabilityHours2Exists(){
		return availabilityHours2Exist;
	}
	
	public boolean getAvailabilityHours3Exists(){
		return availabilityHours3Exist;
	}
	
	public String getStationsCanWork(int i){
		return stationsCanWork[i];
	}
	
	public int getStationsCanWorkSize(){
		return stationsCanWork.length;
	}
	// setters
	
	public void setAvailabilityHours2(int day, float open, float close){
		availabilityHours2[day][0] = open;
		availabilityHours2[day][1] = close;
	}
	
	public void setAvailabilityHours3(int day, float open, float close){
		availabilityHours3[day][0] = open;
		availabilityHours3[day][1] = close;
	}
	@Override //add if statements for hours if more shifts in a day
	public String toString(){
		String ret = "";
		ret += "Name: " + this.nameFirst + " " + this.nameLast;
		ret += "\nID Number: " + this.idNumber;
		ret += "Sunday: " + TimeConverter.converterToString(hours[0][0]) + "-" + TimeConverter.converterToString(hours[0][1]) + " in " + station[0];
		ret += "Monday: " + TimeConverter.converterToString(hours[1][0]) + "-" + TimeConverter.converterToString(hours[1][1]) + " in " + station[1];
		ret += "Tueday: " + TimeConverter.converterToString(hours[2][0]) + "-" + TimeConverter.converterToString(hours[2][1]) + " in " + station[2];
		ret += "Wednesday: " + TimeConverter.converterToString(hours[3][0]) + "-" + TimeConverter.converterToString(hours[3][1]) + " in " + station[3];
		ret += "Thursday: " + TimeConverter.converterToString(hours[4][0]) + "-" + TimeConverter.converterToString(hours[4][1]) + " in " + station[4];
		ret += "Friday: " + TimeConverter.converterToString(hours[5][0]) + "-" + TimeConverter.converterToString(hours[5][1]) + " in " + station[5];
		ret += "Satday: " + TimeConverter.converterToString(hours[6][0]) + "-" + TimeConverter.converterToString(hours[6][1]) + " in " + station[6];
		return ret;
	}
}