package main;

import java.util.Scanner;
import java.util.ArrayList;

public class Station{ // schedules employees by station, single day
	
	private String name;
	private float[] stationHours; // {station opening time, station closing time}, use army time
	private float[] stationBusyHoursOpen;// stations busy hours
	private float[] stationBusyHoursClose;
	private float[] stationQuietHoursOpen; // stations quiet hours, non busy hours
	private float[] stationQuietHoursClose;
	private int[] numEmployees; // {minimum number of employees, maximum, best number}
	private int day; // day of the week, 0 is Sunday, 6 is Saturday
	private ArrayEmpList employeeWorkingIO; //employee working in order
	private ArrayList<Employee> employeeWorking;
	private int indexScheduler; //for managing layer starts, index of the start of new layer
	
	public Station(String name, int day, float stationHoursOpen, float stationHoursClose, String stationBusyHoursString, String stationQuietHoursString, int numEmployeesMin, int numEmployeesMax, int numEmployeesBest){
		this.name = name;
		
		this.day = day;
		this.indexScheduler = 0;
		
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
		
		this.employeeWorkingIO = new ArrayEmpList(day);
		this.employeeWorking = new ArrayList<>();
		
		
		schedEmp();
	}
	
	//methods to help object creations, setters
	
	//sets raw string into float time data for busy hours
	private void setStationBusyHours(String stationBusyHoursString){
		
		for(int j = 0; j < stationBusyHoursOpen.length; j++){
			stationBusyHoursOpen[j] = -1f;
			stationBusyHoursClose[j] = -1f;
		}
		
		Scanner scannerTW = new Scanner(stationBusyHoursString);//scanner for time windows
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
				
				for(int j = 0; j < stationBusyHoursOpen.length; j++){
					stationBusyHoursOpen[j] = -1f;
					stationBusyHoursClose[j] = -1f;
				}
				
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
	private void setStationQuietHours(String stationQuietHoursString){
		for(int j = 0; j < stationBusyHoursOpen.length; j++){
			stationQuietHoursOpen[j] = -1f;
			stationQuietHoursClose[j] = -1f;
		}
		
		Scanner scannerTW = new Scanner(stationQuietHoursString);//scanner for time windows
		scannerTW.useDelimiter(","); //scanner for separate time windows
		int i = 0;
		while(scannerTW.hasNext()){
			if(i == stationQuietHoursOpen.length){ //if it needs more space
				float[] tempOpen = new float[stationQuietHoursOpen.length];
				float[] tempClose = new float[stationQuietHoursClose.length];
				
				for(int n = 0; n < stationQuietHoursOpen.length; n++){
					tempOpen[n] = stationQuietHoursOpen[n];
					tempClose[n] = stationQuietHoursClose[n];
				}
				
				stationQuietHoursOpen = new float[stationQuietHoursOpen.length * 2];
				stationQuietHoursClose = new float[stationQuietHoursClose.length * 2];
				
				for(int j = 0; j < stationQuietHoursOpen.length; j++){
					stationQuietHoursOpen[j] = -1f;
					stationQuietHoursClose[j] = -1f;
				}
				
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
	
	public int getEmployeeWorkingSize(){
		return employeeWorking.size();
	}
	
	public Employee getEmployeeWorkingIO(int i){
		return employeeWorkingIO.get(i);
	}
	
	public int getEmployeeWorkingIOSize(){
		return employeeWorkingIO.size();
	}
	
	//methods to create schedule
	public void schedEmp(){
		if(employeeWorking.isEmpty()){
			for(int i = 0; i < numEmployees[0]; i++){
				schedEmp1();
			}
			if(stationQuietHoursOpen[0] == 0 && stationQuietHoursClose[0] == 0){
				stationQuietHoursOpen[0] = stationHours[0];
				stationQuietHoursClose[0] = stationHours[1];
			}
		}
		if((numEmployees[2] != numEmployees[0]) && (stationQuietHoursOpen[0] != 0) && (stationQuietHoursClose[0] != 0)){
			for(int i = 0; i < numEmployees[2] - numEmployees[0]; i++){
				schedEmp2();
			}
		}
		if(numEmployees[1] != numEmployees[2]){
			for(int i = 0; i < numEmployees[1]; i++){
				schedEmp3();
			}
		}
	}
	//schedules first layer of employees, makes sure there is at least the minimum number of employees the whole time they are open
	private void schedEmp1(){
		//checks if every hours has at least one employee
		if(schedEmpBaseCase(stationHours[0], stationHours[1])){
			indexScheduler = employeeWorking.size();
			return;
			//checks if empty for that layer, adds opening person
		} else if(employeeWorking.size() == indexScheduler){
			for(int i = 0; i < Store.employeesAvailable.size(); i++){
				//checks if they are available before or on the opening time, and shift length is greater than 3 hours
				if((schedCanWorkStation(i)) && (Store.employeesAvailable.get(i).getAvailabilityHours(day, 0) <= stationHours[0]) && ((Store.employeesAvailable.get(i).getAvailabilityHours(day, 1) - stationHours[0]) >= 3)){// && (schedCanWorkStation(i))){
					
					employeeWorking.add(Store.employeesAvailable.remove(i));
					employeeWorking.get(indexScheduler).hours[day][0] = employeeWorking.get(indexScheduler).getAvailabilityHours(day, 0);
					employeeWorking.get(indexScheduler).hours[day][1] = employeeWorking.get(indexScheduler).getAvailabilityHours(day, 1);
					//fixing time on hours
					if(employeeWorking.get(indexScheduler).getAvailabilityHours(day, 0) < stationHours[0]){
						employeeWorking.get(indexScheduler).hours[day][0] = stationHours[0];
					}
					if((employeeWorking.get(indexScheduler).getAvailabilityHours(day, 1) - employeeWorking.get(indexScheduler).getAvailabilityHours(day, 0)) > 8f){
						employeeWorking.get(indexScheduler).hours[day][1] = employeeWorking.get(indexScheduler).hours[day][0] + 8f;
					}
					if(employeeWorking.get(indexScheduler).hours[day][1] > stationHours[1]){
						employeeWorking.get(indexScheduler).hours[day][1] = stationHours[1];
					}
					employeeWorkingIO.addHours(employeeWorking.get(employeeWorking.size() - 1), 0); // adds to ordered employee
					break;
				}
			}
			schedEmp1(); //recursion
		} else {
			if(!schedEmpHelper(1, stationHours[0], stationHours[1])){
				if(!schedEmpHelper(2, stationHours[0], stationHours[1])) {
					if(!schedEmpHelper(3, stationHours[0], stationHours[1])){
						System.out.println("Hire some more people");
						return;
					}
				}
			}
			schedEmp1(); //recursion
		}
		
	}
	
	//2nd layer, makes sure to make the minimum
	private void schedEmp2(){
		//sets size of hours fill
		int z = 0;
		for(int i = 0; i < stationQuietHoursOpen.length; i++){
			if(stationQuietHoursOpen[i] != -1f) z++;
		}
		
		float[][] hoursFill = new float[z + 1][2]; // hours we need to fill up
		hoursFill[0][0] = stationHours[0];
		hoursFill[0][1] = stationQuietHoursOpen[0];
		//sets up hours fill
		for(int i = 1; i < hoursFill.length; i++){
			hoursFill[i][0] = stationQuietHoursClose[i-1];
			hoursFill[i][1] = stationQuietHoursOpen[i];
		}
		hoursFill[hoursFill.length - 1][1] = stationHours[1];
		
		for(int i = 0; i < hoursFill.length; i++){
			schedEmp23Helper(hoursFill[i][0], hoursFill[i][1]);
		}
	}
	
	private void schedEmp3(){
		int z = 0;
		for(int i = 0; i < stationBusyHoursOpen.length; i++){
			if(stationBusyHoursOpen[i] != -1f) z++;
		}
		
		float[][] hoursFill = new float[z][2]; // hours we need to fill up
		for(int i = 1; i < hoursFill.length; i++){
			hoursFill[i][0] = stationBusyHoursOpen[i];
			hoursFill[i][1] = stationBusyHoursClose[i];
		}

		for(int i = 0; i < hoursFill.length; i++){
			schedEmp23Helper(hoursFill[i][0], hoursFill[i][1]);
		}
	}
	
	private void schedEmp23Helper(float open, float close){
		if(schedEmpBaseCase(open, close)){
			indexScheduler = employeeWorking.size();
			return;
		}  else if(employeeWorking.size() == indexScheduler){
			boolean found = false;
			for(int i = 0; i < Store.employeesAvailable.size(); i++){
				//checks if they are available before or on the opening time, and shift length is greater than 3 hours
				if((schedCanWorkStation(i)) && (Store.employeesAvailable.get(i).getAvailabilityHours(day, 0) <= open) && ((Store.employeesAvailable.get(i).getAvailabilityHours(day, 1) - open) >= 3)){
					
					employeeWorking.add(Store.employeesAvailable.remove(i));
					employeeWorking.get(indexScheduler).hours[day][0] = employeeWorking.get(indexScheduler).getAvailabilityHours(day, 0);
					employeeWorking.get(indexScheduler).hours[day][1] = employeeWorking.get(indexScheduler).getAvailabilityHours(day, 1);
					//fixing time on hours
					if(employeeWorking.get(indexScheduler).getAvailabilityHours(day, 0) < open){
						employeeWorking.get(indexScheduler).hours[day][0] = open;
					}
					if((employeeWorking.get(indexScheduler).getAvailabilityHours(day, 1) - employeeWorking.get(indexScheduler).getAvailabilityHours(day, 0)) > 8f){
						employeeWorking.get(indexScheduler).hours[day][1] = employeeWorking.get(indexScheduler).hours[day][0] + 8f;
					}
					if(employeeWorking.get(indexScheduler).hours[day][1] > close){
						employeeWorking.get(indexScheduler).hours[day][1] = close;
						if(employeeWorking.get(indexScheduler).hours[day][1] - employeeWorking.get(indexScheduler).hours[day][1] < 3){
							employeeWorking.get(indexScheduler).hours[day][0] -= 1;
						}
						
					}
					employeeWorkingIO.addHours(employeeWorking.get(employeeWorking.size() - 1), 0); // adds to ordered employee
					found = true;
					break;
				}
			}
			if(!found) return;
			schedEmp23Helper(open, close); //recursion
		} else {
			if(!schedEmpHelper(1, open, close)){
				if(!schedEmpHelper(2, open, close)) {
					if(!schedEmpHelper(3, open, close)){
						System.out.println("Hire some more people");
						return;
					}
				}
			}
			schedEmp23Helper(open, close); //recursion
		}
	}
	//checks base case possibilities
	private boolean schedEmpBaseCase(float open, float close){
		if(employeeWorking.size() == indexScheduler){
			return false;
		} else if((employeeWorking.get(indexScheduler).hours[day][0] <= open) && (employeeWorking.get(employeeWorking.size() - 1).hours[day][1] >= close)){
			return true;
		}
		return false;
	}
	
	private boolean schedEmpHelper(int availNum, float open, float close){
		boolean found = false;
		if(availNum == 1){
			for(int i = 0; i < Store.employeesAvailable.size(); i++){
				//checks if they are available before or on the opening time, and shift length is greater than 3 hours
				if((schedCanWorkStation(i)) && (Store.employeesAvailable.get(i).getAvailabilityHours(day, 0) <= employeeWorking.get(employeeWorking.size() - 1).hours[day][1]) && ((Store.employeesAvailable.get(i).getAvailabilityHours(day, 1) - employeeWorking.get(employeeWorking.size() - 1).hours[day][1]) >= 3)){// && (schedCanWorkStation(i))){
					employeeWorking.add(Store.employeesAvailable.remove(i));
					employeeWorking.get(employeeWorking.size() - 1).hours[day][0] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 0);
					employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 1);
					//fixing time on hours
					if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 0) < employeeWorking.get(employeeWorking.size() - 2).hours[day][1]){
						employeeWorking.get(employeeWorking.size() - 1).hours[day][0] = employeeWorking.get(employeeWorking.size() - 2).hours[day][1];
					}
					if((employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 1) - employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours(day, 0)) > 8f){
						employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).hours[day][0] + 8f;
					}
					if(employeeWorking.get(employeeWorking.size() - 1).hours[day][1] > close){
						employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = close;
						if(employeeWorking.get(employeeWorking.size() - 1).hours[day][1] - employeeWorking.get(employeeWorking.size() - 1).hours[day][1] < 3){
							employeeWorking.get(employeeWorking.size() - 1).hours[day][0] -= 1;
						}
					}
					employeeWorkingIO.addHours(employeeWorking.get(employeeWorking.size() - 1), 0); // adds to ordered employee
					found = true;
					break;
				}
			}
		} else if(availNum == 2){
			for(int i = 0; i < Store.employeesAvailable.size(); i++){
				//checks if they are available before or on the opening time, and shift length is greater than 3 hours
				if(Store.employeesAvailable.get(i).getAvailabilityHours2Exists()){
					if((schedCanWorkStation(i)) && (Store.employeesAvailable.get(i).getAvailabilityHours2(day, 0) <= employeeWorking.get(employeeWorking.size() - 1).hours[day][1]) && ((Store.employeesAvailable.get(i).getAvailabilityHours2(day, 1) - employeeWorking.get(employeeWorking.size() - 1).hours[day][1]) >= 3)){// && schedCanWorkStation(i)){
						employeeWorking.add(Store.employeesAvailable.remove(i));
						employeeWorking.get(employeeWorking.size() - 1).hours[day][0] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 0);
						employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 1);
						//fixing time on hours
						if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 0) < employeeWorking.get(employeeWorking.size() - 2).hours[day][1]){
							employeeWorking.get(employeeWorking.size()- 1).hours[day][0] = employeeWorking.get(employeeWorking.size() - 2).hours[day][1];
						}
						if((employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 1) - employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours2(day, 0)) > 8f){
							employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).hours[day][0] + 8f;
						}
						if(employeeWorking.get(employeeWorking.size() - 1).hours[day][1] > close){
							employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = close;
							if(employeeWorking.get(employeeWorking.size() - 1).hours[day][1] - employeeWorking.get(employeeWorking.size() - 1).hours[day][1] < 3){
								employeeWorking.get(employeeWorking.size() - 1).hours[day][0] -= 1;
							}
						}
						employeeWorkingIO.addHours(employeeWorking.get(employeeWorking.size() - 1), 0); // adds to ordered employee
						found = true;
						break;
					}
				}
			}
		} else {
			for(int i = 0; i < Store.employeesAvailable.size(); i++){
				//checks if they are available before or on the opening time, and shift length is greater than 3 hours
				if(Store.employeesAvailable.get(i).getAvailabilityHours3Exists()){
					if((schedCanWorkStation(i)) && (Store.employeesAvailable.get(i).getAvailabilityHours3(day, 0) <= employeeWorking.get(employeeWorking.size() - 1).hours[day][1]) && ((Store.employeesAvailable.get(i).getAvailabilityHours3(day, 1) - employeeWorking.get(employeeWorking.size() - 1).hours[day][1]) >= 3) && (schedCanWorkStation(i))){
						employeeWorking.add(Store.employeesAvailable.remove(i));
						employeeWorking.get(employeeWorking.size() - 1).hours[day][0] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 0);
						employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 1);
						//fixing time on hours
						if(employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 0) < employeeWorking.get(employeeWorking.size() - 2).hours[day][1]){
							employeeWorking.get(employeeWorking.size() - 1).hours[day][0] = employeeWorking.get(employeeWorking.size() - 2).hours[day][1];
						}
						if((employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 1) - employeeWorking.get(employeeWorking.size() - 1).getAvailabilityHours3(day, 0)) > 8f){
							employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = employeeWorking.get(employeeWorking.size() - 1).hours[day][0] + 8f;
						}
						if(employeeWorking.get(employeeWorking.size() - 1).hours[day][1] > close){
							employeeWorking.get(employeeWorking.size() - 1).hours[day][1] = close;
							if(employeeWorking.get(employeeWorking.size() - 1).hours[day][1] - employeeWorking.get(employeeWorking.size() - 1).hours[day][1] < 3){
								employeeWorking.get(employeeWorking.size() - 1).hours[day][0] -= 1;
							}
						}
						employeeWorkingIO.addHours(employeeWorking.get(employeeWorking.size() - 1), 0); // adds to ordered employee
						found = true;
						break;
					}
				}
			}
		}
		return found;
	}
	
	private boolean schedCanWorkStation(int indexEmpAvail){
		for(int k = 0; k < Store.employeesAvailable.get(indexEmpAvail).getStationsCanWorkSize(); k++){
			if(Store.employeesAvailable.get(indexEmpAvail).getStationsCanWork(k).equals(this.name)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString(){
		String ret = "";
		ret += this.name + ":\n";
		for(int i = 0; i < employeeWorkingIO.size(); i++){
			ret += (i + 1) + ". ";
			ret += employeeWorkingIO.get(i).getNameLast() + ", " + employeeWorkingIO.get(i).getNameFirst() + ": " + TimeConverter.converterToString(employeeWorkingIO.get(i).hours[this.day][0]) + "-" + TimeConverter.converterToString(employeeWorkingIO.get(i).hours[this.day][1]);
			ret += "\n";
		}
		
		return ret;
	}
	
}