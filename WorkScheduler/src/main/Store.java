package main;

import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class Store{ //incorporates schedule made by stations, by day
	
	public HashSet<Employee> employeesAll;
	public ArrayList<Station> stations; //for a specific day, all stations
	private int day;
	
	public Store(int day, String filePathEmployee, String filePathStation){		
		this.day = day;
		
		FileRead.readFileEmp(filePathEmployee);
		FileRead.readFileStore(filePathStation);
		
		employeesAll = new HashSet<>();
		setEmployeesAll(filePathEmployee);
		
		stations = new ArrayList<>();
		setStationsDay(filePathStation);
	}
	
	//helper methods
	private void setEmployeesAll(String filePathEmployee){
		//FileRead.readFileEmp(filePathEmployee);
		for(int i = 0; i < FileRead.getNumberOfEmp(); i++){
			employeesAll.add(new Employee(FileRead.getEmpNameFirst(), FileRead.getEmpNameLast(), FileRead.getEmpIDNum(), FileRead.getNumberOfStations(), FileRead.getEmpStations(), FileRead.getEmpSunAvail(), FileRead.getEmpMonAvail(), FileRead.getEmpTueAvail(), FileRead.getEmpWedAvail(), FileRead.getEmpThurAvail(), FileRead.getEmpFriAvail(), FileRead.getEmpSatAvail()));
		}
	}
	
	//sets station for specific day
	private void setStationsDay(String filePathStation){
		//FileRead.readFileStore(filePathStation);
		for(int i = 0; i < FileRead.getNumberOfStations(); i++){
			stations.add(new Station(FileRead.getStationName(), day, FileRead.getTimeOpen(), FileRead.getTimeClose(), FileRead.getBusyHours(), FileRead.getQuietHours(), FileRead.getMinNumEmp(), FileRead.getMaxNumEmp(), FileRead.getEffNumEmp(), this.setEmployeeAvailable()));
		}
	}
	// adds employees available to queue on a certain day
	private Queue<Employee> setEmployeeAvailable(){ 
		Queue<Employee> employeesAvail = new LinkedList<>();
		
		for(Employee employee: employeesAll){
			if(employee.getAvailabilityDay(day)) employeesAvail.add(employee);
		}
		
		return employeesAvail;	
	}
	
	//getters
	public int getDay(){
		return this.day;
	}
	
	public String getStation(int i){
		String test  = "";
		test += "name:" + stations.get(i).getName();
		return test;
	}
	
}