package display.NewPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import display.MainFrame;
import main.FileRead;
import main.Store;
import main.TimeConverter;
import resources.Button;

public class Schedule extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Store[] store;
	private JPanel panelSchedCenter;
	private JPanel[] panelSchedDay;
	private boolean backCreate; //for when back button is pressed, true if schedule was created through manual, false if template
	private LinkedList<Button> buttonPanelSchedule;
	
	public Schedule(int days, String filePathStation, String filePathEmp){ //for template
		store = new Store[days];
		panelSchedCenter = new JPanel();
		panelSchedDay = new JPanel[days];
		backCreate = false;
		buttonPanelSchedule = new LinkedList<>();
		
		createScheduleTemplate(days, filePathStation, filePathEmp);
		
		setSchedule();
	}
	
	public Schedule(int days, int startingDay){ //for manual
		store = new Store[days];
		panelSchedCenter = new JPanel();
		panelSchedDay = new JPanel[days];
		backCreate = true;
		buttonPanelSchedule = new LinkedList<>();
		
		createScheduleManual(days, startingDay);
		
		setSchedule();
	}
	
	private void setSchedule(){
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		
		this.add(New.setHeader("Created Schedule"), BorderLayout.NORTH);
		this.add(New.setFooter(buttonPanelSchedule), BorderLayout.SOUTH);
		
		panelSchedCenter.setLayout(new BoxLayout(panelSchedCenter, BoxLayout.Y_AXIS));
		panelSchedCenter.setOpaque(false);
		
		for(int i = 0; i < store.length; i++){
			panelScheduleAddHelper(i);
			panelSchedCenter.add(panelSchedDay[i]);
		}
		
		this.add(panelSchedCenter, BorderLayout.CENTER);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	private void createScheduleTemplate(int days, String filePathStation, String filePathEmp){
		FileRead.reset();
		if(days == 1){
			//done to read files and see which day we are trying to schedule
			FileRead.readFileEmp(filePathEmp);
			String[] avail = {FileRead.getEmpSunAvail(), FileRead.getEmpMonAvail(), FileRead.getEmpTueAvail(), FileRead.getEmpWedAvail(), FileRead.getEmpThurAvail(), FileRead.getEmpFriAvail(), FileRead.getEmpSatAvail(), };
			FileRead.reset();
			int index = 0;
			for(int i = 0; i < 7; i++){
				if(!avail[i].equals("0:00-0:00")){
					index = i;
					break;
				}
			}
			store[0] = new Store(index, filePathEmp, filePathStation);
		} else if(days == 5){
			for(int i = 1; i <= 5; i++) //adjusted for starting at Monday's
				store[i - 1] = new Store(i, filePathEmp, filePathStation);
		} else{
			for(int i = 0; i < store.length; i++)
				store[i] = new Store(i, filePathEmp, filePathStation);
		}
	}
	
	private void createScheduleManual(int days, int startingDay){
		if(days == 1){ //checks which day the single schedule is wanted through checking availability
			store[0] = new Store(startingDay);
		} else if(days == 5){
			for(int i = 1; i <= 5; i++) //adjusted for starting at Monday's
				store[i - 1] = new Store(i);
		} else{
			for(int i = 0; i < store.length; i++)
				store[i] = new Store(i);
		}
	}
	
	//adds a panel for the day with stations and employees
	private void panelScheduleAddHelper(int i){
		panelSchedDay[i] = new JPanel();
			
		String[] dayString = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		panelSchedDay[i].setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(MainFrame.brightBgColor, 2, true), dayString[store[i].getDay()], 2, 0, new Font("Microsoft JhengHei", Font.PLAIN, 20), MainFrame.brightBgColor));
		panelSchedDay[i].setLayout(new BoxLayout(panelSchedDay[i], BoxLayout.Y_AXIS));
		panelSchedDay[i].setOpaque(false);
		
		for(int j = 0; j < store[i].getStationSize(); j++){
			panelSchedDay[i].add(panelScheduleAddStations(i, j));
		}
		
		panelSchedDay[i].setVisible(true);
		panelSchedDay[i].revalidate();
		panelSchedDay[i].repaint();
	}
	
	//adds employees to station
	private JPanel panelScheduleAddStations(int i, int j){
		JPanel stationMain = new JPanel();
		stationMain.setBackground(MainFrame.darkMidBgColor);
		stationMain.setLayout(new BoxLayout(stationMain, BoxLayout.Y_AXIS));
			
		//for the station title
		JLabel stationName = new JLabel(store[i].getStation(j).getName().toUpperCase());
		stationName.setFont(new Font("Microsoft JhengHei", Font.BOLD, 18));
		stationName.setForeground(MainFrame.brightBgColor);
		stationName.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			
		JPanel stationNameHolder = new JPanel();
		stationNameHolder.setBackground(MainFrame.darkBgColor);
		stationNameHolder.setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 1));
		stationNameHolder.add(stationName);
			
		//for the number of rows and columns on the table do to rounding down
		int column = 4;
		int rows = 0;
		int numEmps = store[i].getStation(j).getEmployeeWorkingSize();
		if((numEmps % 2) == 0) rows = numEmps / 2;
		else  rows = (numEmps / 2) + 1;
			
		JPanel stationTable = new JPanel();
		stationTable.setLayout(new GridLayout(rows, column, 0, 0));
		stationTable.setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 1));
		stationTable.setBackground(MainFrame.darkMidBgColor);
			
		for(int k = 0; k < (rows * column) / 2; k++){
			if(k < store[i].getStation(j).getEmployeeWorkingSize()){
				Font font = new Font("Microsoft JhengHei", Font.PLAIN, 15);
				//for the name
				String firstName = store[i].getStation(j).getEmployeeWorkingIO(k).getNameFirst();
				String lastName = store[i].getStation(j).getEmployeeWorkingIO(k).getNameLast();
				JLabel emp = new JLabel("   " + lastName + ", " + firstName);
				emp.setForeground(MainFrame.darkBgColor);
				emp.setFont(font);
				JPanel empHolder = new JPanel();
				empHolder.setBackground(MainFrame.midBgColor);
				empHolder.setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 1));
				empHolder.add(emp);
				stationTable.add(empHolder);
				//for the hours
				String timeOpen = TimeConverter.converterToString(store[i].getStation(j).getEmployeeWorkingIO(k).hours[store[i].getDay()][0]);
				String timeClose = TimeConverter.converterToString(store[i].getStation(j).getEmployeeWorkingIO(k).hours[store[i].getDay()][1]);
				JLabel empHours = new JLabel(timeOpen + "-" + timeClose);
				empHours.setForeground(MainFrame.brightBgColor);
				empHours.setBackground(MainFrame.darkMidBgColor);
				empHours.setFont(font);
				empHours.setHorizontalAlignment(SwingConstants.CENTER);
				empHours.setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 1));
				stationTable.add(empHours);
				
			} else { //for extra space
				JLabel[] extra = new JLabel[2];
				for(int l = 0; l < extra.length; l++){
					extra[l] = new JLabel();
					extra[l].setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 1));
					extra[l].setBackground(MainFrame.darkMidBgColor);
					stationTable.add(extra[l]);
				}
			}
		}
			
		//for preferred size
		Dimension dimensionN = stationNameHolder.getPreferredSize();
		dimensionN.width = 750;
		stationNameHolder.setPreferredSize(dimensionN);
		stationMain.add(stationNameHolder);
			
		Dimension dimensionT = stationTable.getPreferredSize();
		dimensionT.width = 750;
		stationTable.setPreferredSize(dimensionT);
		stationMain.add(stationTable);
			
		stationMain.setVisible(true);
		stationMain.revalidate();
		stationMain.repaint();
		return stationMain;
	}	
	
	public void buttonPressed(int buttonNumber){
		if(buttonNumber == 0){ //back
			if(backCreate) New.adjustPanelLevel(-1);
			New.footerPress(buttonNumber);
		} else { //next, brings it back to first panel
			New.setPanelLevel(-1);
			New.footerPress(buttonNumber);
		}
	}
}