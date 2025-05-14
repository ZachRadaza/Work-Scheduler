package display.NewPage;

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
import main.FileRead;
import main.TimeConverter;
import resources.Button;
import resources.HintTextField;

public class Create extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<JPanel> panelStaEmp = new ArrayList<>();
	private ArrayList<StationData> panelStationsData = new ArrayList<>();
	private ArrayList<EmployeeData> panelEmpData = new ArrayList<>();
	private ArrayList<JTextField[]> panelStaEmpTextFields = new ArrayList<>();
	private JPanel panelCreateCenter = new JPanel();
	private JPanel panelCreateAddButtonHome = new JPanel();
	private LinkedList<Button> buttonPanelCreate = new LinkedList<>();
	
	private int days;
	private boolean station; //if station or employee
	private int startingDay; //starting day for scheduling

	public Create(int days, boolean station){
		panelStaEmp = new ArrayList<>();
		panelStationsData = new ArrayList<>();
		panelEmpData = new ArrayList<>();
		panelStaEmpTextFields = new ArrayList<>();
		panelCreateCenter = new JPanel();
		panelCreateAddButtonHome = new JPanel();
		buttonPanelCreate = new LinkedList<>();
		this.days = days;
		this.station = station;
		startingDay = 0;
		
		setCreate();
	}
	
	private void setCreate(){
		this.setLayout(new BorderLayout());
		this.setBackground(MainFrame.darkMidBgColor);
		
		panelCreateCenter.setOpaque(false);
		panelCreateCenter.setLayout(new BoxLayout(panelCreateCenter, BoxLayout.Y_AXIS));
		
		//sets header
		if(station) this.add(New.setHeader("Add All Stations"), BorderLayout.NORTH);
		else this.add(New.setHeader("Add All Employees"), BorderLayout.NORTH);
		//sets footer
		this.add(New.setFooter(buttonPanelCreate), BorderLayout.SOUTH);
		
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
		panelCreateCenter.add(insPanel);
		
		//sets up add panel
		//added to other panel for border and spacing
		panelCreateAddButtonHome.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panelCreateAddButtonHome.setBackground(MainFrame.darkMidBgColor);
		buttonPanelCreate.add(new Button("+", 780, 150, 85, 2, false, ""));
		buttonPanelCreate.get(2).setForeground(MainFrame.brightBgColor);
		buttonPanelCreate.get(2).setBorder(BorderFactory.createDashedBorder(null, 2f, 5f, 5f, true));
		buttonPanelCreate.get(2).setVisible(true);
		buttonPanelCreate.get(2).revalidate();
		buttonPanelCreate.get(2).repaint();
		panelCreateAddButtonHome.add(buttonPanelCreate.get(2));
		
		//adds employee or station panels
		if(station) panelCreateCenter.add(panelCreateStations());
		else panelCreateCenter.add(panelCreateEmployee());
		panelCreateCenter.add(panelCreateAddButtonHome);
		
		panelCreateCenter.setVisible(true);
		panelCreateCenter.revalidate();
		panelCreateCenter.repaint();
		this.add(panelCreateCenter, BorderLayout.CENTER);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	private JPanel panelCreateStations(){
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
		String[] textFieldsHint = {"ex. Under The Hood", "ex. 11:00-22:00", "ex. 2", "ex. 12:00-14:00, 16:00-19:00", "ex. 4", "ex. 20:00-22:00", "ex. 3"};
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new HintTextField(textFieldsHint[i], 1);
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
								
								buttonPanelCreate.add(new Button("x", 15, buttonPanelCreate.size()));
								buttonPanelCreate.get(buttonPanelCreate.size() - 1).setVisible(true);
								buttonPanelCreate.get(buttonPanelCreate.size() - 1).revalidate();
								buttonPanelCreate.get(buttonPanelCreate.size() - 1).repaint();
								panelAdd.add(buttonPanelCreate.get(buttonPanelCreate.size() - 1));
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
	
	private JPanel panelCreateEmployee(){
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
		String[] hints = {"Last Name, ex. Radaza", "First Name, ex. Zachary Juls", "Stations Employee can work in, ex. Under The Hood, Local Loaf", "ex. 10:30-17:00, 19:00-22:00", "ex. 10:30-17:00", "ex. n/a", "ex. 10:30-17:00", "ex. 10:30-17:00", "ex. 10:30-17:00", "ex. 10:30-17:00"};
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new HintTextField(hints[i], 1);
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
								
								buttonPanelCreate.add(new Button("x", 15, buttonPanelCreate.size()));
								buttonPanelCreate.get(buttonPanelCreate.size() - 1).setVisible(true);
								buttonPanelCreate.get(buttonPanelCreate.size() - 1).revalidate();
								buttonPanelCreate.get(buttonPanelCreate.size() - 1).repaint();
								panelAdd.add(buttonPanelCreate.get(buttonPanelCreate.size() - 1), gbc);
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
						
						for(int k = 0; k < panelStationsData.size(); k++){
							if(!(token.toLowerCase().equals(panelStationsData.get(k).getname().toLowerCase()))){
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
			pushPanelStationsData();
			return true;
		}
	}
	
	private boolean validateEmpPopUp(){
		if(!panelEmpValidate()){
			JOptionPane.showMessageDialog(null, "Invalid Information Inputted on Employee's Data", "Data Misinput", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else {
			pushPanelEmpData();
			return true;
		}
	}
	
	//pushes panelStations into panelStationsData
	private void pushPanelStationsData(){
		while(!panelStationsData.isEmpty()){
			panelStationsData.remove(0);
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
			panelStationsData.add(new StationData(name, timeOpen, timeClose, busyHours, quietHours, minNum, maxNum, effNum));
		}
	}
	//pushes panelEmp into panelEmpData
	private void pushPanelEmpData(){
		while(!panelEmpData.isEmpty()){
			panelEmpData.remove(0);
		}
		for(int i = 0; i < panelStaEmpTextFields.size(); i++){
			//for easier input
			String[] temp = new String[panelStaEmpTextFields.get(i).length];
			for(int j = 0; j < panelStaEmpTextFields.get(i).length; j++){
				temp[j] = panelStaEmpTextFields.get(i)[j].getText();
			}
			panelEmpData.add(new EmployeeData(temp[1], temp[0], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], temp[9]));
		}
	}
	//adds data into file read
	private void pushDataFileRead(){
		//pushPanelStationsData();
		//pushPanelEmpData();
		FileRead.setStoreName("test");
		FileRead.setNumberStations(panelStationsData.size());
		FileRead.setNumberEmp(panelEmpData.size());
		for(int i = 0; i < panelStationsData.size(); i++){
			FileRead.addStationNames(panelStationsData.get(i).getname());
			FileRead.addTimeOpen(panelStationsData.get(i).getTimeOpen());
			FileRead.addTimeClose(panelStationsData.get(i).getTimeClose());
			FileRead.addQuietHour(panelStationsData.get(i).getQuietHours());
			FileRead.addBusyHour(panelStationsData.get(i).getBusyHours());
			FileRead.addMinNumEmp(panelStationsData.get(i).getMinNum());
			FileRead.addMaxNumEmp(panelStationsData.get(i).getMaxNum());
			FileRead.addEffNumEmp(panelStationsData.get(i).getEffNum());
		}
		for(int i = 0; i < panelEmpData.size(); i++){
			FileRead.addEmpNameFirst(panelEmpData.get(i).getNameFirst());
			FileRead.addEmpNameLast(panelEmpData.get(i).getNameLast());
			FileRead.addEmpStation(panelEmpData.get(i).getStations());
			FileRead.addEmpSunAvail(panelEmpData.get(i).getAvailSun());
			FileRead.addEmpMonAvail(panelEmpData.get(i).getAvailMon());
			FileRead.addEmpTueAvail(panelEmpData.get(i).getAvailTue());
			FileRead.addEmpWedAvail(panelEmpData.get(i).getAvailWed());
			FileRead.addEmpThurAvail(panelEmpData.get(i).getAvailThur());
			FileRead.addEmpFriAvail(panelEmpData.get(i).getAvailFri());
			FileRead.addEmpSatAvail(panelEmpData.get(i).getAvailSat());
		}
	}
	
	public void getStartingDay(){
		String[] availString = {panelEmpData.get(0).getAvailSun(), panelEmpData.get(0).getAvailMon(), panelEmpData.get(0).getAvailTue(), panelEmpData.get(0).getAvailWed(), panelEmpData.get(0).getAvailThur(), panelEmpData.get(0).getAvailFri(), panelEmpData.get(0).getAvailSat()};
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
			New.footerPress(buttonNumber);
		} else if(buttonNumber == 1){ //next button
			if(station){
				if(validateStationPopUp()){
					New.setAllPanels(2, new Create(days, false));
					New.footerPress(buttonNumber);
				}
			} else {
				if(validateEmpPopUp()){
					//creates schedule and adds it into panel
					getStartingDay();
					pushDataFileRead();
					New.setAllPanels(4, new Schedule(days, startingDay));
					New.adjustPanelLevel(1);
					New.footerPress(buttonNumber);
				}
			}
		} else if(buttonNumber == 2){ //add button
			panelCreateCenter.remove(panelCreateAddButtonHome);
			if(station) panelCreateCenter.add(panelCreateStations());
			else panelCreateCenter.add(panelCreateEmployee());
			panelCreateCenter.add(panelCreateAddButtonHome);
			panelCreateCenter.setVisible(true);
			panelCreateCenter.revalidate();
			panelCreateCenter.repaint();
		} else{ //remove button
			panelCreateCenter.remove(panelStaEmp.get(buttonNumber - 3));
			panelCreateCenter.setVisible(true);
			panelCreateCenter.revalidate();
			panelCreateCenter.repaint();
		}
	}
}