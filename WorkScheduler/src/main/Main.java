package main;

public class Main{
	public static void main(String[] args){
		String filePathStation = "C:/Users/Asus/workspace/WorkScheduler/src/main/StoreExample.txt";
		String filePathEmp = "C:/Users/Asus/workspace/WorkScheduler/src/main/EmployeeExample.txt";
		
		Store test1 = new Store(1, filePathEmp, filePathStation);
		
		for(int i = 0; i < 4; i++){
			System.out.println(test1.getStation(i));
		}
	}
}