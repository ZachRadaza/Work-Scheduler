package main;

public class Main{
	public static void main(String[] args){
		/*
		String filePathStation = "C:/Users/Asus/workspace/WorkScheduler/src/main/StoreExample.txt";
		String filePathEmp = "C:/Users/Asus/workspace/WorkScheduler/src/main/EmployeeExample.txt";
		
		Store test1 = new Store(1, filePathEmp, filePathStation);
		
		for(int i = 0; i < 4; i++){
			System.out.println(test1.getStation(i));
		}
		*/
		// TODO: Bounds check everything
		
		LinkedEmpList test = new LinkedEmpList(0);
		test.add(new Employee("Nia", "Radaza", 303378790, 3, "yabadabadoo", "7:30-13:30", "7:30-12:30", "7:30-12:30", "7:30-12:30", "7:30-12:30", "7:30-12:30", "7:30-12:30"));
		test.add(new Employee("Zach", "Romero", 30337890, 2, "yabadabadoo", "7:30-12:30", "7:30-13:30", "7:30-13:30", "7:30-13:30", "7:30-13:30", "7:30-13:30", "7:30-13:30"));
		test.add(new Employee("Ron", "Radaza", 303378790, 3, "yabadabadoo", "7:30-9:30", "7:30-12:30", "7:30-12:30", "7:30-12:30", "7:30-12:30", "7:30-12:30", "7:30-12:30"));
		test.add(new Employee("C", "Radaza", 303378790, 3, "yabadabadoo", "14:00-20:00", "7:30-12:30", "7:30-12:30", "7:30-12:30", "7:30-12:30", "7:30-12:30", "7:30-12:30"));
		
		System.out.println(test.get(0).getNameFirst());
		System.out.println(test.get(1).getNameFirst());
		System.out.println(test.get(2).getNameFirst());
		System.out.println(test.get(3).getNameFirst());
		
		System.out.println(test.remove(0).getNameFirst());
		System.out.println(test.remove(0).getNameFirst());
		System.out.println(test.remove(0).getNameFirst());
		System.out.println(test.remove(0).getNameFirst());
	}
}