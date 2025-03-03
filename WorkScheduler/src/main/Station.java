package main;

import java.util.Scanner;

public class Station{ // schedules employees by station, single day
	
	private String name;
	private float[] stationHours; // {station opening time, station closing time}, use army time
	private float[] stationBusyHoursOpen;// stations busy hours
	private float[] stationBusyHoursClose;
	private float[] stationQuietHoursOpen; // stations quiet hours, non busy hours
	private float[] stationQuietHoursClose;
	private int[] numEmployees; // {minimum number of employees, maximum, best number}
	private int day; // day of the week, 0 is Sunday, 6 is Saturday
	private LinkedEmpList employeeWorking;
	
	public Station(String name, int day, float stationHoursOpen, float stationHoursClose, String stationBusyHoursString, String stationQuietHoursString, int numEmployeesMin, int numEmployeesMax, int numEmployeesBest){
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
		
		this.employeeWorking = new LinkedEmpList(day);
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
	
	//getters
	public String getName(){
		return this.name;
	}
	
	public int getDay(){
		return this.day;
	}
	
	public Employee getEmployeeWorking(int i){
		return employeeWorking.get(i);
	}
	
	//methods to create schedule
	
	//schedules first layer of employees, makes sure there is at least one employee the whole time they are open
	private void sched1stLayerEmp(){
		//checks if every hours has at least one employee
		if(sched1stLayerEmpBaseCase()){
			return;
		//checks if empty, adds opening person
		} else if(employeeWorking.isEmpty()){
			int i = 0;
			while(!Store.employeesAvailable.isEmpty()){
				//checks if they are available before or on the opening time, and shift length is greater than 3 hours
				if(Store.employeesAvailable.get(i).getAvailabilityHours(day, 0) <= stationHours[0] && (Store.employeesAvailable.get(i).getAvailabilityHours(day, 1) - stationHours[0]) >= 3){
					employeeWorking.add(Store.employeesAvailable.remove(i));
					employeeWorking.get(0).hours[day][0] = employeeWorking.get(0).getAvailabilityHours(day, 0);
					employeeWorking.get(0).hours[day][1] = employeeWorking.get(0).getAvailabilityHours(day, 1);
					//fixing time on hours
					if(employeeWorking.get(0).getAvailabilityHours(day, 0) < stationHours[0]){
						employeeWorking.get(0).hours[day][0] = stationHours[0];
					}
					if((employeeWorking.get(0).getAvailabilityHours(day, 1) - employeeWorking.get(0).getAvailabilityHours(day, 0)) > 8f){
						employeeWorking.get(0).hours[day][1] = employeeWorking.get(0).hours[day][0] + 8f;
					}
					if(employeeWorking.get(0).hours[day][1] > stationHours[1]){
						employeeWorking.get(0).hours[day][1] = stationHours[1];
					}
					break;
				}
				i++;
			}
		} else {
			if(!sched1stLayerEmpHelper(1)){
				if(!sched1stLayerEmpHelper(2)) {
					if(!sched1stLayerEmpHelper(3)){
						System.out.println("Hire some more people");
					}
				}
			}
			sched1stLayerEmp();
		}
	}
	
	private boolean sched1stLayerEmpHelper(int availNum){
		boolean found = false;
		if(availNum == 1){
			for(int i = 0; i < Store.employeesAvailable.size(); i++){
				//checks if they are available before or on the opening time, and shift length is greater than 3 hours
				if(Store.employeesAvailable.get(i).getAvailabilityHours(day, 0) <= employeeWorking.get(employeeWorking.size() - 1).hours[day][1] && (Store.employeesAvailable.get(i).getAvailabilityHours(day, 1) - employeeWorking.get(employeeWorking.size() - 1).hours[day][1]) >= 3){
					employeeWorking.add(Store.employeesAvailable.remove(i));
					employeeWorking.get(employeeWorking.size() - 1).hours[day][0] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 0);
					employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 1);
					//fixing time on hours
					if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 0) < employeeWorking.get(employeeWorking.size() - 2).hours[day][1]){
						employeeWorking.get(0).hours[day][0] = employeeWorking.get(employeeWorking.size() - 2).hours[day][1];
					}
					if((employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 1) - employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 0)) > 8f){
						employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).hours[day][0] + 8f;
					}
					if(employeeWorking.get(employeeWorking.size() - 1).hours[day][1] > stationHours[1]){
						employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = stationHours[1];
					}
					found = true;
					break;
				}
			}
		} else if(availNum == 2){
			for(int i = 0; i < Store.employeesAvailable.size(); i++){
				//checks if they are available before or on the opening time, and shift length is greater than 3 hours
				if(Store.employeesAvailable.get(i).getAvailabilityHours2Exists()){
					if(Store.employeesAvailable.get(i).getAvailabilityHours2(day, 0) <= employeeWorking.get(employeeWorking.size() - 1).hours[day][1] && (Store.employeesAvailable.get(i).getAvailabilityHours2(day, 1) - employeeWorking.get(employeeWorking.size() - 1).hours[day][1]) >= 3){
						employeeWorking.add(Store.employeesAvailable.remove(i));
						employeeWorking.get(employeeWorking.size() - 1).hours[day][0] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 0);
						employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 1);
						//fixing time on hours
						if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 0) < employeeWorking.get(employeeWorking.size() - 2).hours[day][1]){
							employeeWorking.get(0).hours[day][0] = employeeWorking.get(employeeWorking.size() - 2).hours[day][1];
						}
						if((employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 1) - employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 0)) > 8f){
							employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).hours[day][0] + 8f;
						}
						if(employeeWorking.get(employeeWorking.size() - 1).hours[day][1] > stationHours[1]){
							employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = stationHours[1];
						}
						found = true;
						break;
					}
				}
			}
		} else {
			for(int i = 0; i < Store.employeesAvailable.size(); i++){
				//checks if they are available before or on the opening time, and shift length is greater than 3 hours
				if(Store.employeesAvailable.get(i).getAvailabilityHours3Exists()){
					if(Store.employeesAvailable.get(i).getAvailabilityHours3(day, 0) <= employeeWorking.get(employeeWorking.size() - 1).hours[day][1] && (Store.employeesAvailable.get(i).getAvailabilityHours3(day, 1) - employeeWorking.get(employeeWorking.size() - 1).hours[day][1]) >= 3){
						employeeWorking.add(Store.employeesAvailable.remove(i));
						employeeWorking.get(employeeWorking.size() - 1).hours[day][0] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 0);
						employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 1);
						//fixing time on hours
						if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 0) < employeeWorking.get(employeeWorking.size() - 2).hours[day][1]){
							employeeWorking.get(0).hours[day][0] = employeeWorking.get(employeeWorking.size() - 2).hours[day][1];
						}
						if((employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 1) - employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 0)) > 8f){
							employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).hours[day][0] + 8f;
						}
						if(employeeWorking.get(employeeWorking.size() - 1).hours[day][1] > stationHours[1]){
							employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = stationHours[1];
						}
						found = true;
						break;
					}
				}
			}
		}
		return found;
	}
	//checks all possibilities of the base case
	private boolean sched1stLayerEmpBaseCase(){
		if((employeeWorking.get(0).getAvailabilityHours(day, 0) <= stationHours[0]) && (employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 1) >= stationHours[1])){
			return true;
		} else if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2Exists()){
			if((employeeWorking.get(0).getAvailabilityHours(day, 0) <= stationHours[0]) && (employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 1) >= stationHours[1])){
				return true;
			}
		} else if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3Exists()){
			if((employeeWorking.get(0).getAvailabilityHours(day, 0) <= stationHours[0]) && (employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 1) >= stationHours[1])){
				return true;
			}
		} else if(employeeWorking.get(0).getAvailabilityHours2Exists()){
			if((employeeWorking.get(0).getAvailabilityHours2(day, 0) <= stationHours[0]) && (employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 1) >= stationHours[1])){
				return true;
			} else if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2Exists()){
				if((employeeWorking.get(0).getAvailabilityHours2(day, 0) <= stationHours[0]) && (employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 1) >= stationHours[1])){
					return true;
				}
			} else if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3Exists()){
				if((employeeWorking.get(0).getAvailabilityHours2(day, 0) <= stationHours[0]) && (employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 1) >= stationHours[1])){
					return true;
				}
			}
		} else if(employeeWorking.get(0).getAvailabilityHours3Exists()){
			if((employeeWorking.get(0).getAvailabilityHours3(day, 0) <= stationHours[0]) && (employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 1) >= stationHours[1])){
				return true;
			} else if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2Exists()){
				if((employeeWorking.get(0).getAvailabilityHours3(day, 0) <= stationHours[0]) && (employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 1) >= stationHours[1])){
					return true;
				}
			} else if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3Exists()){
				if((employeeWorking.get(0).getAvailabilityHours3(day, 0) <= stationHours[0]) && (employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 1) >= stationHours[1])){
					return true;
				}
			}
		}
		return false;
	}

}