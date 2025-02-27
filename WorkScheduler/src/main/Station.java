package main;

import java.util.Queue;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;



public class Station{ // schedules employees by station, single day
	
	private String name;
	private float[] stationHours; // {station opening time, station closing time}, use army time
	private float[] stationBusyHoursOpen;// stations busy hours
	private float[] stationBusyHoursClose;
	private float[] stationQuietHoursOpen; // stations quiet hours, non busy hours
	private float[] stationQuietHoursClose;
	private int[] numEmployees; // {minimum number of employees, maximum, best number}
	private LinkedEmpList employeeAvailable;
	private int day; // day of the week, 0 is Sunday, 6 is Saturday
	private ArrayList<Employee> employeeWorking;
	
	public Station(String name, int day, float stationHoursOpen, float stationHoursClose, String stationBusyHoursString, String stationQuietHoursString, int numEmployeesMin, int numEmployeesMax, int numEmployeesBest, Queue<Employee> employeeAvailable){
		this.name = name;
		
		this.day = day;
		
		this.stationHours = new float[2];
		this.stationHours[0] = stationHoursOpen;
		this.stationHours[1] = stationHoursClose;
		
		this.stationBusyHoursOpen = new float[8];
		this.stationBusyHoursClose = new float[8];
		this.setStationBusyHours(stationBusyHoursString);
		
		this.stationQuietHoursOpen = new float[8];
		this.stationQuietHoursClose = new float[8];
		this.setStationQuietHours(stationQuietHoursString);
		
		this.numEmployees = new int[3];
		this.numEmployees[0] = numEmployeesMin;
		this.numEmployees[1] = numEmployeesMax;
		this.numEmployees[2] = numEmployeesBest;
		
		this.employeeAvailable = new LinkedEmpList(day);
		this.setEmployeesAvailable(employeeAvailable);
		
		this.employeeWorking = new ArrayList<>();
	}
	
	//methods to help object creations, setters
	
	//sets raw string into float time data for busy hours
	private void setStationBusyHours(String stationQuietHoursString){
		Scanner scannerTW = new Scanner(stationQuietHoursString);//scanner for time windows
		scannerTW.useDelimiter(","); //scanner for separate time windows
		int i = 0;
		while(scannerTW.hasNext()){
			if(i == stationBusyHoursOpen.length){ //if it needs more space
				float[] tempOpen = new float[stationBusyHoursOpen.length];
				float[] tempClose = new float[stationBusyHoursClose.length];
				
				for(int n = 0; n < stationBusyHoursOpen.length; n++){
					tempOpen[n] = stationBusyHoursOpen[n];
					tempClose[n] = stationBusyHoursClose[n];
				}
				
				stationBusyHoursOpen = new float[stationBusyHoursOpen.length * 2];
				stationBusyHoursClose = new float[stationBusyHoursClose.length * 2];
				
				for(int n = 0; n < tempOpen.length; n++){
					stationBusyHoursOpen[n] = tempOpen[n];
					stationBusyHoursClose[n] = tempClose[n];
				}
			}
			String tokenTW = scannerTW.next().trim();
			Scanner scannerOC = new Scanner(tokenTW);//scanner for separate open and closing hours
			scannerOC.useDelimiter("-");
			
			String tokenOC = scannerOC.next().trim();
			stationBusyHoursOpen[i] = TimeConverter.converterToFloat(tokenOC);
			
			tokenOC = scannerOC.next().trim();
			stationBusyHoursClose[i] = TimeConverter.converterToFloat(tokenOC);
			
			i++;
			scannerOC.close();
		}
		scannerTW.close();
	}
	//sets quiet hours
	private void setStationQuietHours(String stationBusyHoursString){
		Scanner scannerTW = new Scanner(stationBusyHoursString);//scanner for time windows
		scannerTW.useDelimiter(","); //scanner for separate time windows
		int i = 0;
		while(scannerTW.hasNext()){
			if(i == stationBusyHoursOpen.length){ //if it needs more space
				float[] tempOpen = new float[stationQuietHoursOpen.length];
				float[] tempClose = new float[stationQuietHoursClose.length];
				
				for(int n = 0; n < stationQuietHoursOpen.length; n++){
					tempOpen[n] = stationQuietHoursOpen[n];
					tempClose[n] = stationQuietHoursClose[n];
				}
				
				stationQuietHoursOpen = new float[stationQuietHoursOpen.length * 2];
				stationQuietHoursClose = new float[stationQuietHoursClose.length * 2];
				
				for(int n = 0; n < tempOpen.length; n++){
					stationQuietHoursOpen[n] = tempOpen[n];
					stationQuietHoursClose[n] = tempClose[n];
				}
			}
			String tokenTW = scannerTW.next().trim();
			Scanner scannerOC = new Scanner(tokenTW);//scanner for separate open and closing hours
			scannerOC.useDelimiter("-");
			
			String tokenOC = scannerOC.next().trim();
			stationQuietHoursOpen[i] = TimeConverter.converterToFloat(tokenOC);
			
			tokenOC = scannerOC.next().trim();
			stationQuietHoursClose[i] = TimeConverter.converterToFloat(tokenOC);
			
			i++;
			scannerOC.close();
		}
		scannerTW.close();
	}
	//adds employee in queue to linked employee list
	private void setEmployeesAvailable(Queue<Employee> employeeAvailable){
		while(!employeeAvailable.isEmpty()){
			this.employeeAvailable.add(employeeAvailable.remove());
		}
	}
	
	
	//getters
	public String getName(){
		return this.name;
	}
	
	public int getDay(){
		return this.day;
	}

}