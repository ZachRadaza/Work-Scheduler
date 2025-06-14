package main;

import java.util.HashSet;
import java.util.ArrayList;

public class Store{ //incorporates schedule made by stations, by day
	
	private HashSet<Employee> employeesAll;
	private ArrayList<Station> stations; //for a specific day, all stations
	public static ArrayEmpList employeesAvailable;
	private int day;
	//constructor for using template
	public Store(int day, String filePathEmployee, String filePathStation){		
		this.day = day;
		
		FileRead.readFileEmp(filePathEmployee);
		FileRead.readFileStore(filePathStation);
		
		employeesAll = new HashSet<>();
		setEmployeesAll();
		
		employeesAvailable = new ArrayEmpList(day);
		setEmployeesAvailable();
		
		stations = new ArrayList<>();
		setStationsDay();
	}
	//constructor for manually added inputs
	public Store(int day){
		this.day = day;
		
		employeesAll = new HashSet<>();
		setEmployeesAll();
		
		employeesAvailable = new ArrayEmpList(day);
		setEmployeesAvailable();
		
		stations = new ArrayList<>();
		setStationsDay();
	}
	
	//helper methods
	private void setEmployeesAll(){
		for(int i = 0; i < FileRead.getNumberOfEmp(); i++){
			employeesAll.add(new Employee(FileRead.getEmpNameFirst(), FileRead.getEmpNameLast(), FileRead.getNumberOfStations(), FileRead.getEmpStations(), FileRead.getEmpSunAvail(), FileRead.getEmpMonAvail(), FileRead.getEmpTueAvail(), FileRead.getEmpWedAvail(), FileRead.getEmpThurAvail(), FileRead.getEmpFriAvail(), FileRead.getEmpSatAvail()));
		}
	}
	
	//sets station for specific day
	private void setStationsDay(){
		for(int i = 0; i < FileRead.getNumberOfStations(); i++){
			stations.add(new Station(FileRead.getStationName(), day, FileRead.getTimeOpen(), FileRead.getTimeClose(), FileRead.getBusyHours(), FileRead.getQuietHours(), FileRead.getMinNumEmp(), FileRead.getMaxNumEmp(), FileRead.getEffNumEmp()));
		}
	}
	// adds employees available on a certain day
	private void setEmployeesAvailable(){ 
		for(Employee employee: employeesAll){
			if(employee.getAvailabilityDay(day)) Store.employeesAvailable.addAvail(employee, 0);
		}
	}
	
	//getters
	public int getDay(){
		return this.day;
	}
	
	public Station getStation(int i){
		return stations.get(i);
	}
	
	public int getStationSize(){
		return stations.size();
	}
	//TODO:
	public String toString(){
		String ret = "";
		String[] dayString = {"Sunday", "Monday", "Tueday", "Wednesday", "Thursday", "Friday", "Saturday"};
		ret += dayString[this.day] + ":\n";
		for(int i = 0; i < stations.size(); i++){
			ret += stations.get(i).toString();
		}
		return ret;
	}
	
}