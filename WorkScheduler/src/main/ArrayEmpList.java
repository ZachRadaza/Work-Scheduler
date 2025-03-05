package main;

import java.util.ArrayList;

public class ArrayEmpList{
	
	int day;
	ArrayList<Employee> list;
	
	public ArrayEmpList(int day){
		this.day = day;
		list = new ArrayList<>();
	}
	
	public void add(Employee emp){
		//if empty
		if(list.isEmpty()) {
			list.add(emp);
			return;
		}
		
		boolean found = false;
		for(int i = 0; i < list.size(); i++){	
			if(emp.getAvailabilityHours(day, 0) < list.get(i).getAvailabilityHours(day, 0)){
				list.add(i, emp);
				found = true;
				break;
			}
			if(emp.getAvailabilityHours(day, 0) == list.get(i).getAvailabilityHours(day, 0)){
				if(emp.getAvailabilityHours(day, 1) >= list.get(i).getAvailabilityHours(day, 1)){
					list.add(i, emp);
					found = true;
					break;
				} else if (list.size() >= i+1 && emp.getAvailabilityHours(day, 0) != list.get(i + 1).getAvailabilityHours(day, 0)){
					list.add(i+1, emp);
					found = true;
					break;
				}
			}
		}
		//if the biggest
		if(!found) list.add(emp);
	}
	
	
	public Employee remove(int i){
		return list.remove(i);
	}
	
	public Employee get(int i){
		return list.get(i);
	}
	
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	public int size(){
		return list.size();
	}

	public int search(float time, int openClose){
		if(list.size() == 0){
			return -1;
		} else if((list.size() == 1 || list.size() == 2) && list.get(list.size() - 1).getAvailabilityHours(day, openClose) == time){
			
			return list.size() - 1;
		} else {
			return searchHelper(time, openClose, 0, list.size() - 1);
		}
	}
	
	private int searchHelper(float time, int openClose, int iStart, int iEnd){
		int iMid = (iStart + iEnd) / 2;
		if(time == list.get(iMid).getAvailabilityHours(iMid, openClose)){
			return iMid;
		} else if (time < list.get(iMid).getAvailabilityHours(iMid, openClose)){
			return searchHelper(time, openClose, iStart, iMid);
		} else {
			return searchHelper(time, openClose, iMid, iEnd);
		}
	}
}