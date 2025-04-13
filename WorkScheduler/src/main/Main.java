package main;

import display.MainFrame;

public class Main{
	public static void main(String[] args){
		
		new MainFrame();
		
		String filePathStation = "C:/Users/Asus/workspace/WorkScheduler/src/main/StoreExample.txt";
		String filePathEmp = "C:/Users/Asus/workspace/WorkScheduler/src/main/EmployeeExample.txt";
		
		Store test1 = new Store(0, filePathEmp, filePathStation);
		
		System.out.println(test1);
		
	}
}