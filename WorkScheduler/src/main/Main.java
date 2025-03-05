package main;

public class Main{
	public static void main(String[] args){
		
		String filePathStation = "C:/Users/Asus/workspace/WorkScheduler/src/main/StoreExample.txt";
		String filePathEmp = "C:/Users/Asus/workspace/WorkScheduler/src/main/EmployeeExample.txt";
		
		Store test1 = new Store(0, filePathEmp, filePathStation);
		
		System.out.println(test1);
		
		// TODO: Bounds check everything
	}
}