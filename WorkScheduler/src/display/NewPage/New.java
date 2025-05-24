package display.NewPage;

import resources.Button;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import display.MainFrame;
import display.Page;
import display.Schedule;

public class New extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//for New page
	private static JPanel mainPanel = new JPanel();
	private static JScrollPane scrollPanel = new JScrollPane(mainPanel);
	private static JPanel[] allPanels = new JPanel[5]; //panels on different steps on process
	private static int panelLevel = 0; //which panel we are currently at
	private static JPanel panelLast; //last panel we are in
	private static int days = 0; //number of days to schedule
	
	//for panel 0
	private static JPanel panel0 = new JPanel();
	private static JPanel[] panel0Board = new JPanel[3];
	private static JPanel[] panel0BoardHome = new JPanel[3];
	private static JPanel panel0Center = new JPanel();
	private static Button[] buttonPanel0 = new Button[6];
	
	//incorporates task bar at the left, templates, and recents. Follow word display
	public New(){
		super("new");
		
		setBody(scrollPanel, mainPanel);
		setPanel0();
		mainPanel.add(panel0);
		panelLast = panel0;
		
		this.add(scrollPanel, BorderLayout.CENTER);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}

	private static void setPanel0(){
		panelLevel = 0;
		
		
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
			panel0BoardHome[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			panel0BoardHome[i].add(panel0Board[i]);
			
			panel0Center.add(panel0BoardHome[i]);
		}
		
		//sets buttons
		String[] buttonStrings = {"Manually Add Elements", "Use Templates", "Manually Add Elements", "Use Templates", "Manually Add Elements", "Use Templates"};
		String[] buttonFilePaths = {"media/NewPage/Create1.png", "media/NewPage/Template1.png", "media/NewPage/Create7.png", "media/NewPage/Template7.png", "media/NewPage/Create5.png", "media/NewPage/Template5.png"};
		for(int i = 0; i < buttonStrings.length; i++){
			buttonPanel0[i] = new Button(buttonStrings[i], 300, 300, 15, i, true, buttonFilePaths[i]);
			if(i < 2) panel0Board[0].add(buttonPanel0[i]);
			else if(i < 4) panel0Board[1].add(buttonPanel0[i]);
			else panel0Board[2].add(buttonPanel0[i]);
		}
		
		//sets header
		panel0.add(setHeader("Create New Schedules"), BorderLayout.NORTH);

		panel0.add(panel0Center, BorderLayout.CENTER);
		
		allPanels[0] = panel0;
		
		panel0.setVisible(true);
		panel0.revalidate();
		panel0.repaint();
	}
	
	public static JPanel getAllPanel(int i){
		return allPanels[i];
	}
	
	public static Button getButtonPanel0(int i){
		if(i < buttonPanel0.length) return buttonPanel0[i];
		else return null;
	}
	
	public static void setAllPanels(int i, JPanel panel){
		allPanels[i] = panel;
	}
	
	public static void setPanelLevel(int i){
		panelLevel = i;
	}
	
	public static  void adjustPanelLevel(int i){
		panelLevel += i;
	}
	
	public static void footerPress(int button){ //0 == back, 1 == next
		if(button == 0) panelLevel--;
		else panelLevel++;
		panelLast.setVisible(false);
		mainPanel.remove(panelLast);
		mainPanel.add(allPanels[panelLevel]);
		panelLast = allPanels[panelLevel];
		allPanels[panelLevel].setVisible(true);
		allPanels[panelLevel].revalidate();
		allPanels[panelLevel].repaint();
	}
	
	//evaluates which button was pressed
	public static void buttonPress(int buttonNumber){
		//panel Level, manual is 1 and 2, template 3, schedule 4;
		if(panelLevel == 0){
			panelLast.setVisible(false);
			mainPanel.remove(panelLast);
			if(buttonNumber < 2){ //sets days based on button pressed
				days = 1;
			} else if(buttonNumber < 4){
				days = 7;
			} else {
				days = 5;
			}
			if(buttonNumber % 2 == 0) { //for panel create pressed
				allPanels[1] = new Create(days, true);
				footerPress(1);
			} else {
				allPanels[3] = new Template(days);
				panelLevel = 2;
				footerPress(1);
			}
		} else if(panelLevel == 1){ //station add screen
			((Create) allPanels[1]).buttonPressed(buttonNumber);
		} else if(panelLevel == 2){ //employee add screen
			((Create) allPanels[2]).buttonPressed(buttonNumber);
		} else if(panelLevel == 3){ //template
			((Template) allPanels[3]).buttonPressed(buttonNumber);
		} else if(panelLevel == 4){
			((Schedule) allPanels[4]).buttonPressed(buttonNumber);
		}
	}
	
}