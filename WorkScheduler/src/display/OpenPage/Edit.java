package display.OpenPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import data.EmployeeData;
import data.StationData;
import display.MainFrame;
import display.Schedule;
import main.FileRead;
import main.TimeConverter;
import resources.Button;
import resources.HintTextField;

public class Edit extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panelCenter;
	private LinkedList<Button> buttons;
	private JPanel panelAddHome;
	private ArrayList<JTextField[]> panelStaEmpTextFields;
	private ArrayList<JPanel> panelStaEmp;
	
	//data stuff
	private ArrayList<StationData> stationData;
	private ArrayList<EmployeeData> empData;
	private int days; //number of days
	private boolean station;
	private int startingDay; //starting day
	private int index; //index of array list in Open
	
	public Edit(ArrayList<StationData> stationData, ArrayList<EmployeeData> empData, int days, boolean station, int index){
		this.panelCenter = new JPanel();
		this.buttons = new LinkedList<>();
		this.panelAddHome = new JPanel();
		this.panelStaEmpTextFields = new ArrayList<>();
		this.panelStaEmp = new ArrayList<>();
		
		this.stationData = stationData;
		this.empData = empData;
		this.days = days;
		this.station = station;
		this.index = index;
		
		setMainPanel();
	}
	
	private void setMainPanel(){
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		panelCenter.setOpaque(false);
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
		
		//sets header
		if(station) this.add(Open.setHeader("Edit All Stations"), BorderLayout.NORTH);
		else this.add(Open.setHeader("Edit All Employees"), BorderLayout.NORTH);
		//sets footer
		this.add(Open.setFooter(buttons), BorderLayout.SOUTH);
		
		//sets up instructions
		JLabel instructions = new JLabel("*Only add availability of the desired day you want to schedule.");
		instructions.setForeground(MainFrame.brightBgColor);
		instructions.setFont(new Font("Microsoft JhengHei", Font.ITALIC, 15));
		
		JPanel insPanel = new JPanel();
		insPanel.setPreferredSize(new Dimension(800, 25));
		insPanel.setOpaque(false);
		insPanel.add(instructions);
		insPanel.setVisible(true);
		insPanel.revalidate();
		insPanel.repaint();
		panelCenter.add(insPanel);
		
		//sets up add panel
		//added to other panel for border and spacing
		panelAddHome.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panelAddHome.setBackground(MainFrame.darkMidBgColor);
		buttons.add(new Button("+", 780, 150, 85, 2, false, ""));
		buttons.get(2).setForeground(MainFrame.brightBgColor);
		buttons.get(2).setBorder(BorderFactory.createDashedBorder(null, 2f, 5f, 5f, true));
		buttons.get(2).setVisible(true);
		buttons.get(2).revalidate();
		buttons.get(2).repaint();
		panelAddHome.add(buttons.get(2));
		
		//adds employee or station panels
		if(station){ 
			for(int i = 0; i < stationData.size(); i++){
				panelCenter.add(panelCreateStations(stationData.get(i), false));
			}
		}
		else{
			for(int i = 0; i < empData.size(); i++){
				panelCenter.add(panelCreateEmployee(empData.get(i), false));
			}
		}
		
		panelCenter.add(panelAddHome);
		
		panelCenter.setVisible(true);
		panelCenter.revalidate();
		panelCenter.repaint();
		this.add(panelCenter, BorderLayout.CENTER);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	private JPanel panelCreateStations(StationData stationData, boolean empty){
		JPanel panel = new JPanel();
		panel.setBackground(MainFrame.darkMidBgColor);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, MainFrame.darkBgColor));
		
		//where we add everything, another panel made for borders
		JPanel panelAdd = new JPanel();
		panelAdd.setBackground(MainFrame.darkMidBgColor);
		panelAdd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelAdd.setPreferredSize(new Dimension(780, 150));
		panelAdd.setLayout(new GridBagLayout());
		
		//adding labels
		String[] labelsName = {"Name:", "Station Hours:", "Minimum Number of Employees:", "Station Busy Hours:", "Maximum Number of Employess:", "Station Quiet Hours:", "Efficient Number of Employees:"};
		JLabel[] labels = new JLabel[labelsName.length];
		for(int i = 0; i < labelsName.length; i++){
			labels[i] = new JLabel(labelsName[i]);
			labels[i].setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
			labels[i].setForeground(MainFrame.brightBgColor);
		}		

		//adding text fields
		JTextField[] textFields = new JTextField[labelsName.length];
		String[] textFieldsData = new String[7];
		if(!empty){
			String hours = TimeConverter.converterToString(stationData.getTimeOpen()) + "-" + TimeConverter.converterToString(stationData.getTimeClose());
			String[] textFieldsDataTemp = {stationData.getname(), hours, Integer.toString(stationData.getMinNum()), stationData.getBusyHours(), Integer.toString(stationData.getMaxNum()), stationData.getQuietHours(), Integer.toString(stationData.getEffNum())};
			for(int i = 0; i < textFieldsData.length; i++) textFieldsData[i] = textFieldsDataTemp[i];
		} else {
			String[] textFieldsDataTemp = {"ex. Under The Hood", "ex. 11:00-22:00", "ex. 2", "ex. 12:00-14:00, 16:00-19:00", "ex. 4", "ex. 20:00-22:00", "ex. 3"};
			for(int i = 0; i < textFieldsData.length; i++) textFieldsData[i] = textFieldsDataTemp[i];
		}	
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField(textFieldsData[i]);
			if(empty) textFields[i] = new HintTextField(textFieldsData[i], 1);
			textFields[i].setBackground(MainFrame.darkBgColor);
			textFields[i].setForeground(MainFrame.brightBgColor);
			textFields[i].setCaretColor(MainFrame.brightBgColor);
			textFields[i].setFont(new Font("Microsoft JhengHei", Font.PLAIN, 14));
			textFields[i].setBorder(BorderFactory.createMatteBorder(2, 10, 2, 2, MainFrame.darkBgColor));
			//for numbers in number of employees
			if(i % 2 == 0 && i != 0){
				textFields[i].setHorizontalAlignment(SwingConstants.CENTER);
				textFields[i].setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 2));
			}
		}
		
		panelStaEmpTextFields.add(textFields);
		//for grid bag layout
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		//adding to grid
		int labelsCount = 0;
		int textFieldsCount = 0;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 8; j++){
				gbc.gridx = j;
				gbc.gridy = i;
				switch(i){
					case 0:
						switch(j){
							case 0: //adds name
								gbc.gridwidth = 1;
								gbc.anchor = GridBagConstraints.EAST;
								
								panelAdd.add(labels[labelsCount], gbc);
								labels[labelsCount].setVisible(true);
								labelsCount++;
								break;
							case 1: //adds text field
								gbc.gridwidth = 3;
								gbc.anchor = GridBagConstraints.WEST;
								
								panelAdd.add(panelStaEmpTextFields.get(panelStaEmpTextFields.size() - 1)[textFieldsCount], gbc);
								panelStaEmpTextFields.get(panelStaEmpTextFields.size() - 1)[textFieldsCount].setVisible(true);
								textFieldsCount++;
								break;
							case 7: //delete button
								gbc.gridwidth = 1;
								gbc.anchor = GridBagConstraints.CENTER;
								
								buttons.add(new Button("x", 15, buttons.size()));
								buttons.get(buttons.size() - 1).setVisible(true);
								buttons.get(buttons.size() - 1).revalidate();
								buttons.get(buttons.size() - 1).repaint();
								panelAdd.add(buttons.get(buttons.size() - 1));
								break;
							default:
								gbc.gridwidth = 1;
								
								panelAdd.add(new JLabel(), gbc);
								break;
						}
						break;

					default:
						switch(j){
							case 1:
							case 4: //texts
								gbc.gridwidth = 1;
								gbc.anchor = GridBagConstraints.WEST;
								
								panelAdd.add(labels[labelsCount], gbc);
								labels[labelsCount].setVisible(true);
								labelsCount++;
								break;
							case 2:
							case 5: //text fields
								gbc.gridwidth = 2;
								gbc.anchor = GridBagConstraints.WEST;
								
								panelAdd.add(panelStaEmpTextFields.get(panelStaEmpTextFields.size() - 1)[textFieldsCount], gbc);
								panelStaEmpTextFields.get(panelStaEmpTextFields.size() - 1)[textFieldsCount].setVisible(true);
								textFieldsCount++;
								break;
							case 3: //to give text fields width enough for time
								gbc.gridwidth = 1;
								
								panelAdd.add(new JLabel("AAAAAAAAAAAAAAAAAAAAAAAAAAA"), gbc);
							case 6: //to give text fields width enough for three digit numbers
								gbc.gridwidth = 1;
								
								panelAdd.add(new JLabel("1234567"), gbc);
								break;
							default: //empty
								gbc.gridwidth = 1;
								
								panelAdd.add(new JLabel(), gbc);
								break;
						}
						break;
				}
			}
		}
		
		panelAdd.setVisible(true);
		panelAdd.revalidate();
		panelAdd.repaint();
		panel.add(panelAdd);
		
		panelAdd.setVisible(true);
		panelAdd.revalidate();
		panelAdd.repaint();
		panel.setVisible(true);
		panel.revalidate();
		panel.repaint();
		
		panelStaEmp.add(panel);
		
		return panel;
	}
	
	private JPanel panelCreateEmployee(EmployeeData empData, boolean empty){
		JPanel panel = new JPanel();
		panel.setBackground(MainFrame.darkMidBgColor);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, MainFrame.darkBgColor));
		
		//where we add everything, another panel made for borders
		JPanel panelAdd = new JPanel();
		panelAdd.setBackground(MainFrame.darkMidBgColor);
		panelAdd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelAdd.setPreferredSize(new Dimension(780, 270));
		panelAdd.setLayout(new GridBagLayout());
		
		//adding labels
		String[] labelsName = {"Name:", "Stations:", "Sunday:", "Monday:", "Tuesday:", "Wednesday:", "Thursday:", "Friday:", "Saturday:"};
		JLabel[] labels = new JLabel[labelsName.length];
		for(int i = 0; i < labelsName.length; i++){
			labels[i] = new JLabel(labelsName[i]);
			labels[i].setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
			labels[i].setForeground(MainFrame.brightBgColor);
		}		

		//adding text fields
		JTextField[] textFields = new JTextField[labelsName.length + 1];
		String[] data = new String[textFields.length];
		if(!empty){
			String[] dataTemp = {empData.getNameLast(), empData.getNameFirst(), empData.getStations(), empData.getAvailSun(), empData.getAvailMon(), empData.getAvailTue(), empData.getAvailWed(), empData.getAvailThur(), empData.getAvailFri(), empData.getAvailSat()};
			for(int i = 0; i < data.length; i++) data[i] = dataTemp[i];
		} else{
			String[] dataTemp = {"Last Name, ex. Radaza", "First Name, ex. Zachary Juls", "Stations Employee can work in, ex. Under The Hood, Local Loaf", "ex. 10:30-17:00, 19:00-22:00", "ex. 10:30-17:00", "ex. n/a", "ex. 10:30-17:00", "ex. 10:30-17:00", "ex. 10:30-17:00", "ex. 10:30-17:00"};
			for(int i = 0; i < data.length; i++) data[i] = dataTemp[i];
		}
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField(data[i]);
			if(empty) textFields[i] = new HintTextField(data[i], 1);
			textFields[i].setBackground(MainFrame.darkBgColor);
			textFields[i].setForeground(MainFrame.brightBgColor);
			textFields[i].setCaretColor(MainFrame.brightBgColor);
			textFields[i].setFont(new Font("Microsoft JhengHei", Font.PLAIN, 14));
			textFields[i].setBorder(BorderFactory.createMatteBorder(2, 10, 2, 2, MainFrame.darkBgColor));
		}
		
		panelStaEmpTextFields.add(textFields);
		
		//for grid bag layout
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		//adding to grid
		int labelsCount = 0;
		int textFieldsCount = 0;
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 8; j++){
				gbc.gridx = j;
				gbc.gridy = i;
				switch(i){
					case 0:
						switch(j){
							case 0: //adds name
								gbc.gridwidth = 1;
								
								panelAdd.add(labels[labelsCount], gbc);
								labels[labelsCount].setVisible(true);
								labelsCount++;
								break;
							case 1:
							case 4: //adds text field
								gbc.gridwidth = 2;
								
								panelAdd.add(panelStaEmpTextFields.get(panelStaEmpTextFields.size() - 1)[textFieldsCount], gbc);
								panelStaEmpTextFields.get(panelStaEmpTextFields.size() - 1)[textFieldsCount].setVisible(true);
								textFieldsCount++;
								break;
							case 3: // adds comma for name
								gbc.gridwidth = 1;
								
								JLabel comma = new JLabel(", ");
								comma.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
								comma.setForeground(MainFrame.brightBgColor);
								panelAdd.add(comma, gbc);
								break;
							case 2:
							case 5:
								gbc.gridwidth = 1;
								panelAdd.add(new JLabel("AAAAAAAAAAAAAAAAAAAAAAAAAAA"), gbc);
								break;
							case 7:
								gbc.gridwidth = 1;
								
								buttons.add(new Button("x", 15, buttons.size()));
								buttons.get(buttons.size() - 1).setVisible(true);
								buttons.get(buttons.size() - 1).revalidate();
								buttons.get(buttons.size() - 1).repaint();
								panelAdd.add(buttons.get(buttons.size() - 1), gbc);
								break;
							default:
								panelAdd.add(new JLabel());
								break;
						}
						break;
					case 1:
						switch(j){
							case 1: //adds stations:
								gbc.gridwidth = 1;
								
								panelAdd.add(labels[labelsCount], gbc);
								labels[labelsCount].setVisible(true);
								labelsCount++;
								break;
							case 2: //adds text field
								gbc.gridwidth = 5;
								
								panelAdd.add(panelStaEmpTextFields.get(panelStaEmpTextFields.size() - 1)[textFieldsCount], gbc);
								panelStaEmpTextFields.get(panelStaEmpTextFields.size() - 1)[textFieldsCount].setVisible(true);
								textFieldsCount++;
								break;
							default:
								panelAdd.add(new JLabel());
								break;
						}
						break;
					case 2: //adds availability string
						if(j == 0){
							gbc.gridwidth = 8;
							JPanel panelAvail = new JPanel();
							panelAvail.setOpaque(false);
							panelAvail.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, MainFrame.darkBgColor), "Availability", 2, 0, new Font("Microsoft JhengHei", Font.PLAIN, 15), MainFrame.brightBgColor));
							panelAdd.add(panelAvail, gbc);
						}
						break;
					default:
						switch(j){
							case 1:
							case 4: //adds labels
								if(labelsCount < labels.length){
									gbc.gridwidth = 1;
									
									panelAdd.add(labels[labelsCount], gbc);
									labels[labelsCount].setVisible(true);
									labelsCount++;
								}
								break;
							case 2:
							case 5: //adds text fields
								if(textFieldsCount < panelStaEmpTextFields.get(panelStaEmpTextFields.size() - 1).length){
									gbc.gridwidth = 2;
									
									panelAdd.add(panelStaEmpTextFields.get(panelStaEmpTextFields.size() - 1)[textFieldsCount], gbc);
									panelStaEmpTextFields.get(panelStaEmpTextFields.size() - 1)[textFieldsCount].setVisible(true);
									textFieldsCount++;
								}
								break;
							default:
								panelAdd.add(new JLabel());
								break;
						}
						break;
				}
				
			}
			
		}
		
		panelAdd.setVisible(true);
		panelAdd.revalidate();
		panelAdd.repaint();
		panel.add(panelAdd);
		
		panelAdd.setVisible(true);
		panelAdd.revalidate();
		panelAdd.repaint();
		panel.isVisible();
		panel.revalidate();
		panel.repaint();
		
		panelStaEmp.add(panel);
		
		return panel;
	}
	
	//validates that inputs in text fields are valid
		private boolean panelStationsValidate(){
			boolean ret = true;
			for(int i = 0; i < panelStaEmpTextFields.size(); i++){
				String[] temp = new String[panelStaEmpTextFields.get(i).length];
				
				for(int j = 0; j < panelStaEmpTextFields.get(i).length; j++){
					temp[j] = panelStaEmpTextFields.get(i)[j].getText().trim();
					if(j % 2 != 0){
						if(temp[j].trim().toLowerCase().equals("n/a") || temp[j].trim().equals("")) {
							panelStaEmpTextFields.get(i)[j].setText("0:00-0:00");
							temp[j] = "0:00-0:00";
						}
						if(!TimeConverter.validateString(temp[j])){
							ret = false;
							break;
						}
					} else if(temp[j].equals("")){
						ret = false;
						break;
					}
				}
			}
			return ret;
		}
		
		private boolean panelEmpValidate(){
			boolean ret = true;
			for(int i = 0; i < panelStaEmpTextFields.size(); i++){
				String[] temp = new String[panelStaEmpTextFields.get(i).length];
				
				for(int j = 0; j < panelStaEmpTextFields.get(i).length; j++){
					temp[j] = panelStaEmpTextFields.get(i)[j].getText().trim();
					
					if(j < 2 && temp[j].equals("")){//validates names and makes sure they are not empty
						ret = false;
						break;
					} else if(j == 2){ //validates stations
						Scanner scanner = new Scanner(temp[j]);
						scanner.useDelimiter(",");
						String token = "";
						//checks each input of stations
						while(scanner.hasNext()){
							token = scanner.next().trim();
							
							for(int k = 0; k < stationData.size(); k++){
								if(!(token.toLowerCase().equals(stationData.get(k).getname().toLowerCase()))){
									ret = false;
									break;
								}
							}
						}
						scanner.close();
					} else if (j > 2){ //validates availability times
						if(temp[j].toLowerCase().equals("n/a") || temp[j].trim().equals("")) {
							panelStaEmpTextFields.get(i)[j].setText("0:00-0:00");
							temp[j] = "0:00-0:00";
						}
						if(!TimeConverter.validateString(temp[j])){
							ret = false;
							break;
						}
					}
				}
			}
			return ret;
		}
	
	private boolean validateStationPopUp(){
		if(!panelStationsValidate()){
			JOptionPane.showMessageDialog(null, "Invalid Information Inputted on Station's Data", "Data Misinput", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else {
			pushstationData();
			return true;
		}
	}
	
	private boolean validateEmpPopUp(){
		if(!panelEmpValidate()){
			JOptionPane.showMessageDialog(null, "Invalid Information Inputted on Employee's Data", "Data Misinput", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else {
			pushempData();
			return true;
		}
	}
	
	//pushes panelStations into stationData
	private void pushstationData(){
		while(!stationData.isEmpty()){
			stationData.remove(0);
		}
		for(int i = 0; i < panelStaEmpTextFields.size(); i++){
			//for easier input
			String name = panelStaEmpTextFields.get(i)[0].getText();
			//separates time due to my incompetence in consistency
			Scanner separate = new Scanner(panelStaEmpTextFields.get(i)[1].getText());
			separate.useDelimiter("-");
			String token = separate.next().trim();
			String timeOpen = token;
			token = separate.next().trim();
			String timeClose = token;
			separate.close();
			
			String busyHours = panelStaEmpTextFields.get(i)[3].getText();
			String quietHours = panelStaEmpTextFields.get(i)[5].getText();
			int minNum = Integer.parseInt(panelStaEmpTextFields.get(i)[2].getText());
			int maxNum = Integer.parseInt(panelStaEmpTextFields.get(i)[4].getText());
			int effNum = Integer.parseInt(panelStaEmpTextFields.get(i)[6].getText());
			stationData.add(new StationData(name, timeOpen, timeClose, busyHours, quietHours, minNum, maxNum, effNum));
		}
	}
	//pushes panelEmp into empData
	private void pushempData(){
		while(!empData.isEmpty()){
			empData.remove(0);
		}
		for(int i = 0; i < panelStaEmpTextFields.size(); i++){
			//for easier input
			String[] temp = new String[panelStaEmpTextFields.get(i).length];
			for(int j = 0; j < panelStaEmpTextFields.get(i).length; j++){
				temp[j] = panelStaEmpTextFields.get(i)[j].getText();
			}
			empData.add(new EmployeeData(temp[1], temp[0], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], temp[9]));
		}
	}
	//adds data into file read
	private void pushDataFileRead(){
		//pushstationData();
		//pushempData();
		FileRead.setStoreName("test");
		FileRead.setNumberStations(stationData.size());
		FileRead.setNumberEmp(empData.size());
		for(int i = 0; i < stationData.size(); i++){
			FileRead.addStationNames(stationData.get(i).getname());
			FileRead.addTimeOpen(stationData.get(i).getTimeOpen());
			FileRead.addTimeClose(stationData.get(i).getTimeClose());
			FileRead.addQuietHour(stationData.get(i).getQuietHours());
			FileRead.addBusyHour(stationData.get(i).getBusyHours());
			FileRead.addMinNumEmp(stationData.get(i).getMinNum());
			FileRead.addMaxNumEmp(stationData.get(i).getMaxNum());
			FileRead.addEffNumEmp(stationData.get(i).getEffNum());
		}
		for(int i = 0; i < empData.size(); i++){
			FileRead.addEmpNameFirst(empData.get(i).getNameFirst());
			FileRead.addEmpNameLast(empData.get(i).getNameLast());
			FileRead.addEmpStation(empData.get(i).getStations());
			FileRead.addEmpSunAvail(empData.get(i).getAvailSun());
			FileRead.addEmpMonAvail(empData.get(i).getAvailMon());
			FileRead.addEmpTueAvail(empData.get(i).getAvailTue());
			FileRead.addEmpWedAvail(empData.get(i).getAvailWed());
			FileRead.addEmpThurAvail(empData.get(i).getAvailThur());
			FileRead.addEmpFriAvail(empData.get(i).getAvailFri());
			FileRead.addEmpSatAvail(empData.get(i).getAvailSat());
		}
	}
	
	public void getStartingDay(){
		String[] availString = {empData.get(0).getAvailSun(), empData.get(0).getAvailMon(), empData.get(0).getAvailTue(), empData.get(0).getAvailWed(), empData.get(0).getAvailThur(), empData.get(0).getAvailFri(), empData.get(0).getAvailSat()};
		int index = 0;
		for(int i = 0; i < 7; i++){
			if(!availString[i].toLowerCase().equals("0:00-0:00")){
				index = i;
				break;
			}
		}
		startingDay = index;
	}
	
	public void buttonPressed(int buttonNumber){
		if(buttonNumber == 0){//back button
			Open.footerPress(buttonNumber);
		} else if(buttonNumber == 1){ //next button
			if(station){
				if(validateStationPopUp()){
					Open.setAllPanels(2, new Edit(stationData, empData, days, false, index));
					Open.footerPress(buttonNumber);
				}
			} else {
				if(validateEmpPopUp()){
					//creates schedule and adds it into panel
					getStartingDay();
					pushDataFileRead();
					Open.replaceStationData(stationData, index); //pushed needed data
					Open.replaceEmpData(empData, index);
					Open.replaceDays(days, index);
					Open.setPanel(); //adds buttons to edit
					Open.setAllPanels(3, new Schedule(days, startingDay, false));
					Open.footerPress(buttonNumber);
				}
			}
		} else if(buttonNumber == 2){ //add button
			panelCenter.remove(panelAddHome);
			if(station) panelCenter.add(panelCreateStations(null, true));
			else panelCenter.add(panelCreateEmployee(null, true));
			panelCenter.add(panelAddHome);
			panelCenter.setVisible(true);
			panelCenter.revalidate();
			panelCenter.repaint();
		} else{ //remove button
			panelCenter.remove(panelStaEmp.get(buttonNumber - 3));
			panelCenter.setVisible(true);
			panelCenter.revalidate();
			panelCenter.repaint();
		}
	}
}