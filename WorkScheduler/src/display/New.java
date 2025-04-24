package display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
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
	private static JPanel panel1 = new JPanel();
	private static JPanel[] panelHeader = new JPanel[3];
	private static JPanel[] panelFooter = new JPanel[3];
	private static JPanel[] panelCreate = new JPanel[2];
	private static JPanel panelTemplate1;
	
	//which panel we are currently at
	private static int panelLevel;
	
	//last panel we are in
	private static JPanel panelLast;
	
	//fields needed for each steps
	//for panel create 1
	private static ArrayList<JPanel> panelStations = new ArrayList<>();
	private static ArrayList<JPanel> panelEmp = new ArrayList<>();
	private static ArrayList<StationData> panelStationsData = new ArrayList<>();
	private static ArrayList<StationData> panelEmpData = new ArrayList<>();
	private static ArrayList<JTextField[]> panelStationsTextFields = new ArrayList<>();
	private static ArrayList<JTextField[]> panelEmpTextFields = new ArrayList<>();
	private static JPanel[] panelCreateAdd = new JPanel[2];
	private static JPanel[] panelCreateAddButtonHome = new JPanel[2];
	
	//buttons
	private Button[] buttonPanel1 = new Button[2];
	private static ArrayList<LinkedList<Button>> buttonPanelCreate = new ArrayList<>();
	//private static LinkedList<Button> buttonPanelCreate1; //linked list to allow removing individual stations
	
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
		
		panelMain.add(panel1);
		panelLast = panel1;
		
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
		setPanelTemplate1();
	}
	
	private void setPanel1(){
		panel1.setBackground(MainFrame.darkMidBgColor);
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		
		buttonPanel1[0] = new Button("Manually Add Elements", 380, 550, 20, 0, true, "media/taskBar/homeIcon.png");
		buttonPanel1[0].isVisible();
		
		buttonPanel1[1] = new Button("Use Templates", 380, 550, 20, 1, true, "media/taskBar/homeIcon.png");
		buttonPanel1[1].isVisible();
		
		panel1.add(buttonPanel1[0]);
		panel1.add(buttonPanel1[1]);
		
		panel1.setVisible(true);
		panel1.revalidate();
		panel1.repaint();
		
		panelHeader[0] = new JPanel();
		panelHeader[0].setBackground(MainFrame.darkMidBgColor);
		panelFooter[0] = new JPanel();
		panelFooter[0].setLayout(new BorderLayout());
		panelFooter[0].setBackground(MainFrame.darkMidBgColor);
	}
	
	private void setPanelCreate(int n){ // 0 = stations add panel, 2 = employee add panel
		panelCreate[n] = new JPanel();
		
		panelCreate[n].setLayout(new BorderLayout());
		panelCreate[n].setBackground(MainFrame.darkMidBgColor);
		
		panelCreateAdd[n] = new JPanel();
		panelCreateAdd[n].setBackground(MainFrame.darkMidBgColor);
		panelCreateAdd[n].setLayout(new BoxLayout(panelCreateAdd[n], BoxLayout.Y_AXIS));
		
		buttonPanelCreate.add(new LinkedList<>());
		panelHeader[n + 1] = new JPanel();
		panelFooter[n + 1] = new JPanel();
		
		JLabel title = new JLabel("Add All Stations");
		if(n == 1) title.setText("Add All Employees");
		title.setForeground(MainFrame.brightBgColor);
		title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 25));
		panelHeader[n + 1].add(title);
		panelHeader[n + 1].setVisible(true);
		panelHeader[n + 1].revalidate();
		panelHeader[n + 1].repaint();
		
		//sets up back button
		buttonPanelCreate.get(n).add(setBackButton());
		buttonPanelCreate.get(n).get(0).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		buttonPanelCreate.get(n).get(0).setVisible(true);
		buttonPanelCreate.get(n).get(0).revalidate();
		buttonPanelCreate.get(n).get(0).repaint();
		panelFooter[n + 1].add(buttonPanelCreate.get(n).get(0), BorderLayout.WEST);
		
		panelFooter[n + 1].setVisible(true);
		panelFooter[n + 1].revalidate();
		panelFooter[n + 1].repaint();
		
		//sets up next panel
		buttonPanelCreate.get(n).add(new Button("next", 50, 30, 15, 1, false, ""));
		buttonPanelCreate.get(n).get(1).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		buttonPanelCreate.get(n).get(1).setVisible(true);
		buttonPanelCreate.get(n).get(1).revalidate();
		buttonPanelCreate.get(n).get(1).repaint();
		panelFooter[n + 1].add(buttonPanelCreate.get(n).get(1), BorderLayout.EAST);
		
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
		
		panelCreateAdd[n].add(panelCreateStaEmp(n));
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
	
	private static JPanel panelCreateStaEmp(int n){
		JPanel panel = new JPanel();
		panel.setBackground(MainFrame.darkMidBgColor);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, MainFrame.darkBgColor));
		
		//where we add everything, another panel made for borders
		JPanel panelAdd = new JPanel();
		panelAdd.setBackground(MainFrame.darkMidBgColor);
		panelAdd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelAdd.setPreferredSize(new Dimension(800, 150));
		panelAdd.setLayout(new GridLayout(4, 5, 5, 3));
		
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
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField(1);
		}
		if(n == 0) panelStationsTextFields.add(textFields);
		else panelEmpTextFields.add(textFields);
		
		//adding to grid
		int labelsCount = 0;
		int textFieldsCount = 0;
		for(int i = 0; i < 20; i++){
			switch(i){
			case 4:
				buttonPanelCreate.get(n).add(new Button("x", 50, 30, 15, buttonPanelCreate.get(n).size(), false, ""));
				buttonPanelCreate.get(n).get(buttonPanelCreate.get(n).size() - 1).setVisible(true);
				buttonPanelCreate.get(n).get(buttonPanelCreate.get(n).size() - 1).revalidate();
				buttonPanelCreate.get(n).get(buttonPanelCreate.get(n).size() - 1).repaint();
				panelAdd.add(buttonPanelCreate.get(n).get(buttonPanelCreate.get(n).size() - 1));
				break;
			case 0:
			case 6:
			case 8:
			case 11:
			case 13:
			case 16:
			case 18:
				panelAdd.add(labels[labelsCount]);
				labels[labelsCount].setVisible(true);
				labelsCount++;
				break;
			case 1:
			case 7:
			case 9:
			case 12:
			case 14:
			case 17:
			case 19:
				if(n == 0){
					panelAdd.add(panelStationsTextFields.get(panelStationsTextFields.size() - 1)[textFieldsCount]);
					panelStationsTextFields.get(panelStationsTextFields.size() - 1)[textFieldsCount].setVisible(true);
					textFieldsCount++;
				} else {
					panelAdd.add(panelEmpTextFields.get(panelEmpTextFields.size() - 1)[textFieldsCount]);
					panelEmpTextFields.get(panelEmpTextFields.size() - 1)[textFieldsCount].setVisible(true);
					textFieldsCount++;
				}
				break;
			default:
				panelAdd.add(new JLabel());
				break;
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
		
		if(n == 0) panelStations.add(panel);
		else panelEmp.add(panel);
		
		return panel;
	}
	//pushes panelStations into panelStationsData
	private void pushPanelStationsData(){
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
	
	private void setPanelTemplate1(){
		panelTemplate1 = new JPanel();
		
		panelTemplate1.setVisible(true);
		panelTemplate1.revalidate();
		panelTemplate1.repaint();
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
			if(buttonNumber == 0) { 
				panelMain.add(panelCreate[0]);
				panelLast = panelCreate[0];
				panelLevel++;
				panelCreate[0].setVisible(true);
				panelCreate[0].revalidate();
				panelCreate[0].repaint();
			} else {
				panelMain.add(panelTemplate1);
				panelLast = panelTemplate1;
				panelLevel--;
				panelTemplate1.setVisible(true);
				panelTemplate1.revalidate();
				panelTemplate1.repaint();
			}
		} else if(panelLevel == 1){
			if(buttonNumber == 0){//back button
				panelLast.setVisible(false);
				panelMain.remove(panelLast);
				panelMain.add(panel1);
				panelLast = panel1;
				panelLevel--;
				panel1.setVisible(true);
				panel1.revalidate();
				panel1.repaint();
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
				panelCreateAdd[0].add(panelCreateStaEmp(0));
				panelCreateAdd[0].add(panelCreateAddButtonHome[0]);
				panelCreateAdd[0].setVisible(true);
				panelCreateAdd[0].revalidate();
				panelCreateAdd[0].repaint();
			} else{
				panelCreateAdd[0].remove(panelStations.get(buttonNumber - 3));
				panelCreateAdd[0].setVisible(true);
				panelCreateAdd[0].revalidate();
				panelCreateAdd[0].repaint();
			}
		}
	}
}