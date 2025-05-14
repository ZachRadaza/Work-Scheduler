package main;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.LinkedList;

public class FileRead{
	//station static fields
	private static String storeName;
	private static Queue<String> stationNames = new LinkedList<>();
	private static Queue<Float> timeOpen = new LinkedList<>();
	private static Queue<Float> timeClose = new LinkedList<>();
	private static Queue<String> busyHours = new LinkedList<>();
	private static Queue<String> quietHours = new LinkedList<>();
	private static Queue<Integer> minNumEmp = new LinkedList<>();
	private static Queue<Integer> maxNumEmp = new LinkedList<>();
	private static Queue<Integer> effNumEmp = new LinkedList<>();
	private static int numberOfStations = 0; //keeps count of number of stations
	
	//employee static fields
	private static Queue<String> empNameFirst = new LinkedList<>();
	private static Queue<String> empNameLast = new LinkedList<>();
	
	private static Queue<String> empSunAvail = new LinkedList<>();//string, will convert to time in employee
	private static Queue<String> empMonAvail = new LinkedList<>();
	private static Queue<String> empTueAvail = new LinkedList<>();
	private static Queue<String> empWedAvail = new LinkedList<>();
	private static Queue<String> empThurAvail = new LinkedList<>();
	private static Queue<String> empFriAvail = new LinkedList<>();
	private static Queue<String> empSatAvail = new LinkedList<>();
	private static Queue<String> empStations = new LinkedList<>();
	private static int numberOfEmp = 0; //keeps count of number of employee

	//reads file for store
	public static boolean readFileStore(String filePath){
		try{
			File file = new File(filePath);   // connects file to file object
			Scanner readFile = new Scanner(file);
			readFile.useDelimiter("~"); // separates each content
			
			readFile.next();
			String token = readFile.next().trim();
			storeName = token;
			
			//while loop for each station
			while(readFile.hasNext()){
				//read station name, adds to queue
				readFile.next();
				token = readFile.next().trim().toUpperCase();
				stationNames.add(token);
				
				//read times
				readFile.next();
				token = readFile.next().trim();
				
				//to separate opening and closing times
				Scanner scannerTimeSeparater = new Scanner(token);
				scannerTimeSeparater.useDelimiter("-");
				//opening time
				String tokenTime = scannerTimeSeparater.next();
				float timeO = TimeConverter.converterToFloat(tokenTime);
				timeOpen.add(timeO);
				//closing time
				tokenTime = scannerTimeSeparater.next();
				float timeC = TimeConverter.converterToFloat(tokenTime);
				timeClose.add(timeC);
				scannerTimeSeparater.close();
				
				//reads busy hours
				readFile.next();
				token = readFile.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "0:00-0:00";
				busyHours.add(token);
				//reads quiet hours
				readFile.next();
				token = readFile.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "0:00-0:00";
				quietHours.add(token);
				
				// minimum number of employees
				readFile.next();
				token = readFile.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "1";
				int min = Integer.parseInt(token);
				minNumEmp.add(min);
				
				//maximum number of employees
				readFile.next();
				token = readFile.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "1";
				int max = Integer.parseInt(token);
				maxNumEmp.add(max);
				
				//efficient number of employees
				readFile.next();
				token = readFile.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "1";
				int eff = Integer.parseInt(token);
				effNumEmp.add(eff);
				
				numberOfStations++;//increments number of stations
			}
			
			readFile.close();
			return true;
		} catch(FileNotFoundException e) {
			/*
			System.err.println("Problem finding file pookie sorry");
			System.err.println(e.getMessage());
			e.printStackTrace();
			*/
			return false;
		} catch(NumberFormatException e){
			/*
			System.err.println("Incorrect inputs");
			System.err.println(e.getMessage());
			e.printStackTrace();
			*/
			return false;
		}
		
	}
	//reads employee file
	public static boolean readFileEmp(String filePath){
		try{
			File file = new File(filePath);
			Scanner fileRead = new Scanner(file);
			fileRead.useDelimiter("~");
			String token;

			//loops for each employee
			while(fileRead.hasNext()){
				//adds first name
				fileRead.next();
				token = fileRead.next().trim().toUpperCase();
				empNameFirst.add(token);
				//adds last name
				fileRead.next();
				token = fileRead.next().trim().toUpperCase();
				empNameLast.add(token);
				//adds to days
				
				fileRead.next();
				token = fileRead.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "0:00-0:00";
				empSunAvail.add(token);
				
				fileRead.next();
				token = fileRead.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "0:00-0:00";
				empMonAvail.add(token);
				
				fileRead.next();
				token = fileRead.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "0:00-0:00";
				empTueAvail.add(token);
				
				fileRead.next();
				token = fileRead.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "0:00-0:00";
				empWedAvail.add(token);

				fileRead.next();
				token = fileRead.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "0:00-0:00";
				empThurAvail.add(token);
				
				fileRead.next();
				token = fileRead.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "0:00-0:00";
				empFriAvail.add(token);
				
				fileRead.next();
				token = fileRead.next().trim();
				if(token.toLowerCase().equals("n/a")) token = "0:00-0:00";
				empSatAvail.add(token);
				//adds stations they can work in
				fileRead.next();
				token = fileRead.next().trim().toUpperCase();
				empStations.add(token);
				
				numberOfEmp++;
			}
			fileRead.close();
			return true;
		} catch (FileNotFoundException e){
			/*
			System.err.println("Sorry pookie file was not found :(");
			System.err.println(e.getMessage());
			e.printStackTrace();
			*/
			return false;
		}  catch(NumberFormatException e){
			/*
			System.err.println("Incorrect inputs");
			System.err.println(e.getMessage());
			e.printStackTrace();
			*/
			return false;
		}
	}
	//getters
	//store
	public static String getStoreName(){
		return storeName;
	}
	
	public static String getStationName(){
		if(!stationNames.isEmpty()){
			return stationNames.remove();
		} else {
			return null;
		}
	}
	
	public static Float getTimeOpen(){
		if(!timeOpen.isEmpty()){
			return timeOpen.remove();
		} else {
			return -1f;
		}
	}
	
	public static Float getTimeClose(){
		if(!timeClose.isEmpty()){
			return timeClose.remove();
		} else {
			return -1f;
		}
	}
	
	public static String getBusyHours(){
		if(!busyHours.isEmpty()){
			return busyHours.remove();
		} else {
			return null;
		}
	}
	
	public static String getQuietHours(){
		if(!quietHours.isEmpty()){
			return quietHours.remove();
		} else {
			return null;
		}
	}
	
	public static int getMinNumEmp(){
		if(!minNumEmp.isEmpty()){
			return minNumEmp.remove();
		} else {
			return -1;
		}
	}
	
	public static int getMaxNumEmp(){
		if(!maxNumEmp.isEmpty()){
			return maxNumEmp.remove();
		} else {
			return -1;
		}
	}
	
	public static int getEffNumEmp(){
		if(!effNumEmp.isEmpty()){
			return effNumEmp.remove();
		} else {
			return -1;
		}
	}
	
	public static int getNumberOfStations(){
		return numberOfStations;
	}
	
	//employee
	public static String getEmpNameFirst(){
		if(!empNameFirst.isEmpty()){
			return empNameFirst.remove();
		} else {
			return null;
		}
	}
	
	public static String getEmpNameLast(){
		if(!empNameLast.isEmpty()){
			return empNameLast.remove();
		} else {
			return null;
		}
	}
	
	public static String getEmpSunAvail(){
		if(!empSunAvail.isEmpty()){
			return empSunAvail.remove();
		} else {
			return null;
		}
	}
	
	public static String getEmpMonAvail(){
		if(!empMonAvail.isEmpty()){
			return empMonAvail.remove();
		} else {
			return null;
		}
	}
	
	public static String getEmpTueAvail(){
		if(!empTueAvail.isEmpty()){
			return empTueAvail.remove();
		} else {
			return null;
		}
	}
	
	public static String getEmpWedAvail(){
		if(!empWedAvail.isEmpty()){
			return empWedAvail.remove();
		} else {
			return null;
		}
	}
	
	public static String getEmpThurAvail(){
		if(!empThurAvail.isEmpty()){
			return empThurAvail.remove();
		} else {
			return null;
		}
	}
	
	public static String getEmpFriAvail(){
		if(!empFriAvail.isEmpty()){
			return empFriAvail.remove();
		} else {
			return null;
		}
	}
	
	public static String getEmpSatAvail(){
		if(!empSatAvail.isEmpty()){
			return empSatAvail.remove();
		} else {
			return null;
		}
	}
	
	public static String getEmpStations(){
		if(!empStations.isEmpty()){
			return empStations.remove();
		} else {
			return null;
		}
	}
	
	public static int getNumberOfEmp(){
		return numberOfEmp;
	}
	
	//setters
	//store
	public static void setStoreName(String name){
		storeName = name;
	}
	
	public static void setNumberStations(int n){
		numberOfStations = n;
	}
	
	public static void addStationNames(String stationName){
		stationNames.add(stationName);
	}
	
	public static void addTimeOpen(Float time) {
	    timeOpen.add(time);
	}

	public static void addTimeClose(Float time) {
	    timeClose.add(time);
	}

	public static void addBusyHour(String hour) {
	    busyHours.add(hour);
	}

	public static void addQuietHour(String hour) {
	    quietHours.add(hour);
	}

	public static void addMinNumEmp(Integer num) {
	    minNumEmp.add(num);
	}

	public static void addMaxNumEmp(Integer num) {
	    maxNumEmp.add(num);
	}

	public static void addEffNumEmp(Integer num) {
	    effNumEmp.add(num);
	}
	
	//Employees
	public static void setNumberEmp(int n){
		numberOfEmp = n;
	}
	
	public static void addEmpNameFirst(String firstName) {
	    empNameFirst.add(firstName);
	}

	public static void addEmpNameLast(String lastName) {
	    empNameLast.add(lastName);
	}

	public static void addEmpSunAvail(String time) {
	    empSunAvail.add(time);
	}

	public static void addEmpMonAvail(String time) {
	    empMonAvail.add(time);
	}

	public static void addEmpTueAvail(String time) {
	    empTueAvail.add(time);
	}

	public static void addEmpWedAvail(String time) {
	    empWedAvail.add(time);
	}

	public static void addEmpThurAvail(String time) {
	    empThurAvail.add(time);
	}

	public static void addEmpFriAvail(String time) {
	    empFriAvail.add(time);
	}

	public static void addEmpSatAvail(String time) {
	    empSatAvail.add(time);
	}

	public static void addEmpStation(String station) {
	    empStations.add(station);
	}
	
	public static void reset(){
		storeName = "";
		numberOfStations = 0;
		numberOfEmp = 0;
		//stations
	    stationNames.clear();
	    timeOpen.clear();
	    timeClose.clear();
	    busyHours.clear();
	    quietHours.clear();
	    minNumEmp.clear();
	    maxNumEmp.clear();
	    effNumEmp.clear();
		
		//emps
	    empNameFirst.clear();
	    empNameLast.clear();
	    empSunAvail.clear();
	    empMonAvail.clear();
	    empTueAvail.clear();
	    empWedAvail.clear();
	    empThurAvail.clear();
	    empFriAvail.clear();
	    empSatAvail.clear();
	    empStations.clear();
	}
}