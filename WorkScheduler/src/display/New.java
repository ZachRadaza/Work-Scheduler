package display;

import main.FileRead;
import main.Store;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class New extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//main panel to add and remove things on
	private static JPanel panelMain = new JPanel();
	private JScrollPane panelScroll;
	
	//panels on different steps on process
	private static JPanel panel0 = new JPanel();
	private static JPanel[] panelHeader = new JPanel[3];
	private static JPanel[] panelFooter = new JPanel[3];
	private static JPanel[] panelCreate = new JPanel[2]; //0 and 1
	public static JPanel panelCreate2 = new JPanel();
	private static JPanel panelTemplate0 = new JPanel();
	
	//which panel we are currently at
	private static int panelLevel = 0;
	
	//last panel we are in
	private static JPanel panelLast;
	
	//fields needed for each steps
	//for panel 0
	private static int days = 0; //number of days to schedule
	
	//for panel 0
	private static JPanel[] panel0Board = new JPanel[3];
	private static JPanel[] panel0BoardHome = new JPanel[3];
	private static JPanel panel0Center = new JPanel();
	//for panel create 0 and 1
	private static ArrayList<JPanel> panelStations = new ArrayList<>();
	private static ArrayList<JPanel> panelEmp = new ArrayList<>();
	private static ArrayList<StationData> panelStationsData = new ArrayList<>();
	private static ArrayList<EmployeeData> panelEmpData = new ArrayList<>();
	private static ArrayList<JTextField[]> panelStationsTextFields = new ArrayList<>();
	private static ArrayList<JTextField[]> panelEmpTextFields = new ArrayList<>();
	private static JPanel[] panelCreateAdd = new JPanel[2];
	private static JPanel[] panelCreateAddButtonHome = new JPanel[2];
	
	//buttons
	private Button[] buttonPanel0 = new Button[6];
	private static ArrayList<LinkedList<Button>> buttonPanelCreate = new ArrayList<>();
	
	//incorporates task bar at the left, templates, and recents. Follow word display
	protected New(){
		super("new");
		
		setBody();
		
		this.add(panelScroll, BorderLayout.CENTER);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	//sets main panels
	private void setBody(){
		panelLevel = 0;
		
		panelMain.setBackground(MainFrame.darkMidBgColor);
		
		setPanelAll();
		
		panelMain.add(panel0);
		panelLast = panel0;
		
		panelMain.setVisible(true);
		panelMain.revalidate();
		panelMain.repaint();
		
		panelScroll = new JScrollPane(panelMain);
		panelScroll.setBorder(null);
		// Change the background color of the scroll bar track
        panelScroll.getVerticalScrollBar().setBackground(MainFrame.darkMidBgColor);
        panelScroll.getHorizontalScrollBar().setBackground(MainFrame.darkMidBgColor);
        // Change the color of the scroll bar thumb
        panelScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = MainFrame.darkBgColor;
            }});
        panelScroll.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = MainFrame.darkBgColor;
            }});
        //sets speed of scroll
        panelScroll.getVerticalScrollBar().setUnitIncrement(16);
	}
	//sets all panels being used
	private void setPanelAll(){
		setPanel1();
		setPanelCreate(0);
		setPanelCreate(1);
		setPanelCreate2();
		setPanelTemplate0();
	}
	
	private void setHeader(int i, String text){
		panelHeader[i] = new JPanel();
		panelHeader[i].setOpaque(false);
		JLabel title = new JLabel(text.toUpperCase());
		title.setForeground(MainFrame.brightBgColor);
		title.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 25));
		title.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		JPanel titleHolder = new JPanel();
		//titleHolder.setBackground(MainFrame.darkBgColor);
		titleHolder.add(title);
		titleHolder.setOpaque(false);
		titleHolder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, MainFrame.brightBgColor));
		
		Dimension panelSize = titleHolder.getPreferredSize();
		panelSize.width = 750;
		titleHolder.setPreferredSize(panelSize);
		
		panelHeader[i].add(titleHolder);
		panelHeader[i].setVisible(true);
		panelHeader[i].revalidate();
		panelHeader[i].repaint();
	}
	
	private void setFooter(int i, LinkedList<Button> buttons){
		panelFooter[i] = new JPanel();
		panelFooter[i].setLayout(new BorderLayout());
		panelFooter[i].setBackground(MainFrame.darkMidBgColor);
		
		//sets up back button
		buttons.add(setBackButton());
		buttons.get(0).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		buttons.get(0).setVisible(true);
		buttons.get(0).revalidate();
		buttons.get(0).repaint();
		panelFooter[i].add(buttons.get(0), BorderLayout.WEST);
		
		//sets up next panel
		buttons.add(new Button("next", 50, 30, 15, 1, false, ""));
		buttons.get(1).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		buttons.get(1).setVisible(true);
		buttons.get(1).revalidate();
		buttons.get(1).repaint();
		panelFooter[i].add(buttons.get(1), BorderLayout.EAST);
		
		panelFooter[i].setVisible(true);
		panelFooter[i].revalidate();
		panelFooter[i].repaint();
	}
	
	private void setPanel1(){
		panel0.setBackground(MainFrame.darkMidBgColor);
		panel0.setLayout(new BorderLayout());
		
		panel0Center.setBackground(MainFrame.darkMidBgColor);
		panel0Center.setLayout(new BoxLayout(panel0Center, BoxLayout.Y_AXIS));
		
		//sets button panel and border
		String[] titleString = {"Create Schedules for One Day", "Create Schedules for Week", "Create Schedules for Weekdays"};
		for(int i = 0; i < panel0Board.length; i++){
			panel0Board[i] = new JPanel();
			panel0Board[i].setBackground(MainFrame.darkMidBgColor);
			panel0Board[i].setLayout(new BoxLayout(panel0Board[i], BoxLayout.X_AXIS));
			panel0Board[i].setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 2, true), titleString[i], 1, 0, new Font("Microsoft JhengHei", Font.PLAIN, 20), MainFrame.brightBgColor));
			
			panel0BoardHome[i] = new JPanel();
			panel0BoardHome[i].setOpaque(false);
			panel0BoardHome[i].setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
			panel0BoardHome[i].add(panel0Board[i]);
			
			panel0Center.add(panel0BoardHome[i]);
		}
		
		//sets buttons
		String[] buttonStrings = {"Manually Add Elements", "Use Templates", "Manually Add Elements", "Use Templates", "Manually Add Elements", "Use Templates"};
		String[] buttonFilePaths = {"media/taskBar/homeIcon.png", "media/taskBar/homeIcon.png", "media/taskBar/homeIcon.png", "media/taskBar/homeIcon.png", "media/taskBar/homeIcon.png", "media/taskBar/homeIcon.png"};
		for(int i = 0; i < buttonStrings.length; i++){
			buttonPanel0[i] = new Button(buttonStrings[i], 300, 300, 15, i, true, buttonFilePaths[i]);
			if(i < 2) panel0Board[0].add(buttonPanel0[i]);
			else if(i < 4) panel0Board[1].add(buttonPanel0[i]);
			else panel0Board[2].add(buttonPanel0[i]);
		}
		
		//sets header
		setHeader(0, "Create New Schedules");
		panel0.add(panelHeader[0], BorderLayout.NORTH);
		panelFooter[0] = new JPanel();

		panel0.add(panel0Center, BorderLayout.CENTER);
		
		panel0.setVisible(true);
		panel0.revalidate();
		panel0.repaint();
	}
	
	private void setPanelCreate(int n){ // 0 = stations add panel, 2 = employee add panel
		panelCreate[n] = new JPanel();
		
		panelCreate[n].setLayout(new BorderLayout());
		panelCreate[n].setBackground(MainFrame.darkMidBgColor);
		
		panelCreateAdd[n] = new JPanel();
		panelCreateAdd[n].setBackground(MainFrame.darkMidBgColor);
		panelCreateAdd[n].setLayout(new BoxLayout(panelCreateAdd[n], BoxLayout.Y_AXIS));
		
		//sets header
		if(n == 0) setHeader(n + 1, "Add All Stations");
		else setHeader(n + 1, "Add All Employees");
		//sets footer
		buttonPanelCreate.add(new LinkedList<>());
		setFooter(n + 1, buttonPanelCreate.get(buttonPanelCreate.size() - 1));
		
		//sets up add panel
		//added to other panel for border and spacing
		panelCreateAddButtonHome[n] = new JPanel();
		panelCreateAddButtonHome[n].setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panelCreateAddButtonHome[n].setBackground(MainFrame.darkMidBgColor);
		buttonPanelCreate.get(n).add(new Button("+", 800, 150, 85, 2, false, ""));
		buttonPanelCreate.get(n).get(2).setForeground(MainFrame.brightBgColor);
		buttonPanelCreate.get(n).get(2).setBorder(BorderFactory.createDashedBorder(null, 2f, 5f, 5f, true));
		buttonPanelCreate.get(n).get(2).setVisible(true);
		buttonPanelCreate.get(n).get(2).revalidate();
		buttonPanelCreate.get(n).get(2).repaint();
		panelCreateAddButtonHome[n].add(buttonPanelCreate.get(n).get(2));
		
		//adds employee or station panels
		if(n == 0) panelCreateAdd[n].add(panelCreateStations());
		else panelCreateAdd[n].add(panelCreateEmployee());
		panelCreateAdd[n].add(panelCreateAddButtonHome[n]);
		panelCreate[n].add(panelFooter[n + 1], BorderLayout.SOUTH);
		panelCreate[n].add(panelHeader[n + 1], BorderLayout.NORTH);
		
		panelCreateAdd[n].setVisible(true);
		panelCreateAdd[n].revalidate();
		panelCreateAdd[n].repaint();
		
		panelCreate[n].add(panelCreateAdd[n], BorderLayout.CENTER);
		
		panelCreate[n].setVisible(true);
		panelCreate[n].revalidate();
		panelCreate[n].repaint();
	}
	
	private static JPanel panelCreateStations(){
		JPanel panel = new JPanel();
		panel.setBackground(MainFrame.darkMidBgColor);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, MainFrame.darkBgColor));
		
		//where we add everything, another panel made for borders
		JPanel panelAdd = new JPanel();
		panelAdd.setBackground(MainFrame.darkMidBgColor);
		panelAdd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelAdd.setPreferredSize(new Dimension(800, 150));
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
		
		panelStationsTextFields.add(textFields);
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
								
								panelAdd.add(panelStationsTextFields.get(panelStationsTextFields.size() - 1)[textFieldsCount], gbc);
								panelStationsTextFields.get(panelStationsTextFields.size() - 1)[textFieldsCount].setVisible(true);
								textFieldsCount++;
								break;
							case 7: //delete button
								gbc.gridwidth = 1;
								gbc.anchor = GridBagConstraints.CENTER;
								
								buttonPanelCreate.get(0).add(new Button("x", 30, 30, 15, buttonPanelCreate.get(0).size(), false, ""));
								buttonPanelCreate.get(0).get(buttonPanelCreate.get(0).size() - 1).setVisible(true);
								buttonPanelCreate.get(0).get(buttonPanelCreate.get(0).size() - 1).revalidate();
								buttonPanelCreate.get(0).get(buttonPanelCreate.get(0).size() - 1).repaint();
								panelAdd.add(buttonPanelCreate.get(0).get(buttonPanelCreate.get(0).size() - 1));
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
								
								panelAdd.add(panelStationsTextFields.get(panelStationsTextFields.size() - 1)[textFieldsCount], gbc);
								panelStationsTextFields.get(panelStationsTextFields.size() - 1)[textFieldsCount].setVisible(true);
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
		
		panelStations.add(panel);
		
		return panel;
	}
	
	private static JPanel panelCreateEmployee(){
		JPanel panel = new JPanel();
		panel.setBackground(MainFrame.darkMidBgColor);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, MainFrame.darkBgColor));
		
		//where we add everything, another panel made for borders
		JPanel panelAdd = new JPanel();
		panelAdd.setBackground(MainFrame.darkMidBgColor);
		panelAdd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelAdd.setPreferredSize(new Dimension(800, 270));
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
		String[] hints = {"Last Name, ex. Radaza", "First Name, ex. Zachary Juls", "Stations Employee can work in, ex. Under The Hood, Local Loaf", "ex. 10:30-17:00, 19:00-22:00", "ex. 10:30-17:00", "ex. 10:30-17:00", "ex. 10:30-17:00", "ex. 10:30-17:00", "ex. 10:30-17:00", "ex. 10:30-17:00"};
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new HintTextField(hints[i], 1);
			textFields[i].setBackground(MainFrame.darkBgColor);
			textFields[i].setForeground(MainFrame.brightBgColor);
			textFields[i].setCaretColor(MainFrame.brightBgColor);
			textFields[i].setFont(new Font("Microsoft JhengHei", Font.PLAIN, 14));
			textFields[i].setBorder(BorderFactory.createMatteBorder(2, 10, 2, 2, MainFrame.darkBgColor));
		}
		
		panelEmpTextFields.add(textFields);
		
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
								
								panelAdd.add(panelEmpTextFields.get(panelEmpTextFields.size() - 1)[textFieldsCount], gbc);
								panelEmpTextFields.get(panelEmpTextFields.size() - 1)[textFieldsCount].setVisible(true);
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
								
								buttonPanelCreate.get(1).add(new Button("x", 30, 30, 15, buttonPanelCreate.get(1).size(), false, ""));
								buttonPanelCreate.get(1).get(buttonPanelCreate.get(1).size() - 1).setVisible(true);
								buttonPanelCreate.get(1).get(buttonPanelCreate.get(1).size() - 1).revalidate();
								buttonPanelCreate.get(1).get(buttonPanelCreate.get(1).size() - 1).repaint();
								panelAdd.add(buttonPanelCreate.get(1).get(buttonPanelCreate.get(1).size() - 1), gbc);
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
								
								panelAdd.add(panelEmpTextFields.get(panelEmpTextFields.size() - 1)[textFieldsCount], gbc);
								panelEmpTextFields.get(panelEmpTextFields.size() - 1)[textFieldsCount].setVisible(true);
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
								if(textFieldsCount < panelEmpTextFields.get(panelEmpTextFields.size() - 1).length){
									gbc.gridwidth = 2;
									
									panelAdd.add(panelEmpTextFields.get(panelEmpTextFields.size() - 1)[textFieldsCount], gbc);
									panelEmpTextFields.get(panelEmpTextFields.size() - 1)[textFieldsCount].setVisible(true);
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
		
		panelEmp.add(panel);
		
		return panel;
	}
	
	//validates that inputs in text fields are valid
	private static boolean panelStationsValidate(){
		return false;
	}
	
	private static boolean panelEmpValidate(){
		return false;
	}
	
	//pushes panelStations into panelStationsData
	private static void pushPanelStationsData(){
		for(int i = 0; i < panelStationsTextFields.size(); i++){
			//for easier input
			String name = panelStationsTextFields.get(i)[0].getText();
			String timeOpen = panelStationsTextFields.get(i)[1].getText();
			String timeClose = panelStationsTextFields.get(i)[2].getText();
			String busyHours = panelStationsTextFields.get(i)[3].getText();
			String quietHours = panelStationsTextFields.get(i)[4].getText();
			int minNum = Integer.parseInt(panelStationsTextFields.get(i)[5].getText());
			int maxNum = Integer.parseInt(panelStationsTextFields.get(i)[6].getText());
			int effNum = Integer.parseInt(panelStationsTextFields.get(i)[7].getText());
			panelStationsData.add(new StationData(name, timeOpen, timeClose, busyHours, quietHours, minNum, maxNum, effNum));
		}
	}
	//pushes panelEmp into panelEmpData
	private static void pushPanelEmpData(){
		for(int i = 0; i < panelEmpTextFields.size(); i++){
			//for easier input
			String[] temp = new String[panelEmpTextFields.get(i).length];
			for(int j = 0; j < panelEmpTextFields.get(i).length; j++){
				temp[j] = panelEmpTextFields.get(i)[j].getText();
			}
			panelEmpData.add(new EmployeeData(temp[1], temp[0], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], temp[9]));
		}
	}
	//adds data into file read
	private static void pushDataFileRead(){
		pushPanelStationsData();
		pushPanelEmpData();
		FileRead.setStoreName("test");
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
	
	private static void createSchedule(int n){ //n = 0 is creating from scratch, n = 1 is using template
		if(n == 0){
			Store[] store = new Store[7];
			for(int i = 0; i < 7; i++){
				pushDataFileRead();
				store[i] = new Store(i);
			}
		} else{
			
		}
	}
	
	private void setPanelCreate2(){
		panelCreate2.setBackground(MainFrame.darkMidBgColor);
		
		panelCreate2.setVisible(true);
		panelCreate2.revalidate();
		panelCreate2.repaint();
	}
	
	private void setPanelTemplate0(){
		
		panelTemplate0.setVisible(true);
		panelTemplate0.revalidate();
		panelTemplate0.repaint();
	}
	
	private Button setBackButton(){
		Button button = new Button("back", 50, 30, 15, 0, false, "");
		
		return button;
	}
	
	//evaluates which button was pressed
	protected static void buttonPress(int buttonNumber){
		//positive values for creating manually, negative for template use
		if(panelLevel == 0){
			panelLast.setVisible(false);
			panelMain.remove(panelLast);
			if(buttonNumber < 2){ //sets days based on button pressed
				days = 1;
			} else if(buttonNumber < 4){
				days = 7;
			} else {
				days = 5;
			}
			if(buttonNumber % 2 == 0) { //for panel create pressed
				panelMain.add(panelCreate[0]);
				panelLast = panelCreate[0];
				panelLevel++;
				panelCreate[0].setVisible(true);
				panelCreate[0].revalidate();
				panelCreate[0].repaint();
			} else {
				panelMain.add(panelTemplate0);
				panelLast = panelTemplate0;
				panelLevel--;
				panelTemplate0.setVisible(true);
				panelTemplate0.revalidate();
				panelTemplate0.repaint();
			}
		} else if(panelLevel == 1){
			if(buttonNumber == 0){//back button
				panelLast.setVisible(false);
				panelMain.remove(panelLast);
				panelMain.add(panel0);
				panelLast = panel0;
				panelLevel--;
				panel0.setVisible(true);
				panel0.revalidate();
				panel0.repaint();
			} else if(buttonNumber == 1){ //next button
				panelLast.setVisible(false);
				panelMain.remove(panelLast);
				panelMain.add(panelCreate[1]);
				panelLast = panelCreate[1];
				panelLevel++;
				panelCreate[1].setVisible(true);
				panelCreate[1].revalidate();
				panelCreate[1].repaint();
			} else if(buttonNumber == 2){ //add button
				panelCreateAdd[0].remove(panelCreateAddButtonHome[0]);
				panelCreateAdd[0].add(panelCreateStations());
				panelCreateAdd[0].add(panelCreateAddButtonHome[0]);
				panelCreateAdd[0].setVisible(true);
				panelCreateAdd[0].revalidate();
				panelCreateAdd[0].repaint();
			} else{ //remove button
				panelCreateAdd[0].remove(panelStations.get(buttonNumber - 3));
				panelCreateAdd[0].setVisible(true);
				panelCreateAdd[0].revalidate();
				panelCreateAdd[0].repaint();
			}
		} else if(panelLevel == 2){
			if(buttonNumber == 0){//back button
				panelLast.setVisible(false);
				panelMain.remove(panelLast);
				panelMain.add(panelCreate[0]);
				panelLast = panelCreate[0];
				panelLevel--;
				panelCreate[0].setVisible(true);
				panelCreate[0].revalidate();
				panelCreate[0].repaint();
			} else if(buttonNumber == 1){ //next button
				panelLast.setVisible(false);
				panelMain.remove(panelLast);
				panelMain.add(panelCreate2);
				panelLast = panelCreate2;
				panelLevel++;
				panelCreate2.setVisible(true);
				panelCreate2.revalidate();
				panelCreate2.repaint();
			} else if(buttonNumber == 2){ //add button
				panelCreateAdd[1].remove(panelCreateAddButtonHome[1]);
				panelCreateAdd[1].add(panelCreateEmployee());
				panelCreateAdd[1].add(panelCreateAddButtonHome[1]);
				panelCreateAdd[1].setVisible(true);
				panelCreateAdd[1].revalidate();
				panelCreateAdd[1].repaint();
			} else{ //remove button
				panelCreateAdd[1].remove(panelEmp.get(buttonNumber - 3));
				panelCreateAdd[1].setVisible(true);
				panelCreateAdd[1].revalidate();
				panelCreateAdd[1].repaint();
			}
		} 
	}
	
}