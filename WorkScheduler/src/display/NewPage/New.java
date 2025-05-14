package display.NewPage;

import resources.Button;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import display.MainFrame;
import display.Page;

public class New extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//main panel to add and remove things on
	private static JPanel panelMain = new JPanel();
	private static JScrollPane panelScroll;
	
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
		
		setPanel0();
		
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
	
	public static JPanel setHeader(String text){
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		
		JLabel title = new JLabel(text.toUpperCase());
		title.setForeground(MainFrame.brightBgColor);
		title.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 25));
		title.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		
		JPanel titleHolder = new JPanel();
		titleHolder.add(title);
		titleHolder.setOpaque(false);
		titleHolder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, MainFrame.brightBgColor));
		
		Dimension panelSize = titleHolder.getPreferredSize();
		panelSize.width = 750;
		titleHolder.setPreferredSize(panelSize);
		
		panel.add(titleHolder);
		return panel;
	}
	
	public static JPanel setFooter(LinkedList<Button> buttons){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(MainFrame.darkMidBgColor);
		panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, MainFrame.brightBgColor));
		
		//sets up back button
		buttons.add(setBackButton());
		buttons.get(0).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		panel.add(buttons.get(0), BorderLayout.WEST);
		
		//sets up next panel
		buttons.add(new Button("next", 15, 1));
		buttons.get(1).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		panel.add(buttons.get(1), BorderLayout.EAST);
		
		return panel;
	}
	
	private static void setPanel0(){
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
		panel0.add(setHeader("Create New Schedules"), BorderLayout.NORTH);

		panel0.add(panel0Center, BorderLayout.CENTER);
		
		allPanels[0] = panel0;
		
		panel0.setVisible(true);
		panel0.revalidate();
		panel0.repaint();
	}
	
	public static void setAllPanels(int i, JPanel panel){
		allPanels[i] = panel;
	}
	
	public static JPanel getAllPanel(int i){
		return allPanels[i];
	}
	
	public static void setPanelLevel(int i){
		panelLevel = i;
	}
	
	public static void adjustPanelLevel(int i){
		panelLevel += i;
	}
	
	private static Button setBackButton(){
		Button button = new Button("back", 15, 0);
		
		return button;
	}
	
	public static void footerPress(int button){ //0 == back, 1 == next
		if(button == 0) panelLevel--;
		else panelLevel++;
		panelLast.setVisible(false);
		panelMain.remove(panelLast);
		panelMain.add(allPanels[panelLevel]);
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
			panelMain.remove(panelLast);
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