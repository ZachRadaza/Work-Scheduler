package display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Stack;

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
	private static JPanel panelMain;
	private JScrollPane panelScroll;
	
	//panels on different steps on process
	private JPanel panel1;
	private JPanel panelFooter;
	private static JPanel panelCreate1;
	private static JPanel panelTemplate1;
	
	//which panel we are currently at
	private static int panelLevel;
	
	//last panel we are in
	private static JPanel panelLast;
	private static Stack<JPanel> backPath;
	
	//fields needed for each steps
	//for panel create 1
	private ArrayList<JPanel> panelStations;
	private ArrayList<StationData> panelStationsData;
	private JPanel panelCreate1Add;
	
	//buttons
	private Button[] buttonPanel1 = new Button[2];
	private Button[] buttonPanelCreate1 = new Button[4];
	
	//incorporates task bar at the left, templates, and recents. Follow word display
	protected New(){
		super("new");
		
		setBody();
		
		this.add(panelScroll, BorderLayout.CENTER);
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
	
	//sets main panels
	private void setBody(){
		panelLevel = 0;
		
		panelMain = new JPanel();
		panelMain.setBackground(MainFrame.darkMidBgColor);
		
		backPath = new Stack<>();
		
		setPanelAll();
		
		panelMain.add(panel1);
		panelLast = panel1;
		
		panelMain.isVisible();
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
		setPanelCreate1();
		setPanelTemplate1();
	}
	
	private void setPanel1(){
		panel1 = new JPanel();
		panel1.setBackground(MainFrame.darkMidBgColor);
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		
		buttonPanel1[0] = new Button("Manually Add Elements", 380, 550, 20, 0, true, "media/taskBar/homeIcon.png");
		buttonPanel1[0].isVisible();
		
		JLabel label = new JLabel("OR");
		label.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		label.setForeground(MainFrame.brightBgColor);
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		label.isVisible();
		
		buttonPanel1[1] = new Button("Use Templates", 380, 550, 20, 0, true, "media/taskBar/homeIcon.png");
		buttonPanel1[1].isVisible();
		
		panel1.add(buttonPanel1[0]);
		panel1.add(label);
		panel1.add(buttonPanel1[1]);
		
		panel1.isVisible();
		panel1.revalidate();
		panel1.repaint();
		
		panelFooter = new JPanel();
		panelFooter.setLayout(new BorderLayout());
		panelFooter.setBackground(MainFrame.darkMidBgColor);
	}
	
	private void setPanelCreate1(){
		panelCreate1 = new JPanel();
		
		panelCreate1.setLayout(new BorderLayout());
		panelCreate1.setBackground(MainFrame.darkMidBgColor);
		
		panelCreate1Add = new JPanel();
		panelCreate1Add.setBackground(MainFrame.darkMidBgColor);
		panelCreate1Add.setLayout(new BoxLayout(panelCreate1Add, BoxLayout.Y_AXIS));
		
		panelStations = new ArrayList<>();
		panelStationsData = new ArrayList<>();
		
		panelFooter = new JPanel();
		panelFooter.setLayout(new BorderLayout());
		panelFooter.setBackground(MainFrame.darkMidBgColor);
		
		buttonPanelCreate1[0] = setBackButton();
		buttonPanelCreate1[0].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		buttonPanelCreate1[0].isVisible();
		buttonPanelCreate1[0].revalidate();
		buttonPanelCreate1[0].repaint();
		panelFooter.add(buttonPanelCreate1[0], BorderLayout.WEST);
		
		buttonPanelCreate1[1] = new Button("next", 50, 25, 15, 1, false, "");
		buttonPanelCreate1[1].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		buttonPanelCreate1[1].isVisible();
		buttonPanelCreate1[1].revalidate();
		buttonPanelCreate1[1].repaint();
		panelFooter.add(buttonPanelCreate1[1], BorderLayout.EAST);
		
		panelCreate1Add.add(panelCreate1Stations());
		panelCreate1.add(panelFooter, BorderLayout.SOUTH);
		
		panelCreate1Add.isVisible();
		panelCreate1Add.revalidate();
		panelCreate1Add.repaint();
		
		panelCreate1.add(panelCreate1Add, BorderLayout.CENTER);
		
		panelCreate1.isVisible();
		panelCreate1.revalidate();
		panelCreate1.repaint();
	}
	
	private void setPanelTemplate1(){
		panelTemplate1 = new JPanel();
		
		panelTemplate1.isVisible();
		panelTemplate1.revalidate();
		panelTemplate1.repaint();
	}
	
	private JPanel panelCreate1Stations(){
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
		panelAdd.add(labels[0]);

		//adding text fields
		JTextField[] textFields = new JTextField[labelsName.length];
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField(1);
		}
		panelAdd.add(textFields[0]);
		
		//adding to grid
		int labelsCount = 1;
		int textFieldsCount = 1;
		for(int i = 2; i < 20; i++){
			switch(i){
			case 6:
			case 8:
			case 11:
			case 13:
			case 16:
			case 18:
				panelAdd.add(labels[labelsCount]);
				labels[labelsCount].isVisible();
				System.out.println(i);
				labelsCount++;
				break;
			case 7:
			case 9:
			case 12:
			case 14:
			case 17:
			case 19:
				panelAdd.add(textFields[textFieldsCount]);
				textFields[textFieldsCount].isVisible();
				textFieldsCount++;
				break;
			default:
				System.out.println(i);
				panelAdd.add(new JLabel());
				break;
			}
		}
		
		panelAdd.isVisible();
		panelAdd.revalidate();
		panelAdd.repaint();
		panel.add(panelAdd);
		
		panelAdd.isVisible();
		panelAdd.revalidate();
		panelAdd.repaint();
		panel.isVisible();
		panel.revalidate();
		panel.repaint();
		return panel;
	}
	
	private Button setBackButton(){
		Button button = new Button("back", 50, 25, 15, 0, false, "");
		
		return button;
	}
	
	private static void backButtonPressed(){
		panelMain.add(backPath.peek());
		backPath.peek().isVisible();
		panelLast = backPath.peek();
		backPath.push(backPath.pop());
		if(panelLevel < 0){
			panelLevel++;
		} else{
			panelLevel--;
		}
	}
	
	//evaluates which button was pressed
	protected static void buttonPress(int buttonNumber){
		//positive valuse for creating manually, negative for template use
		if(panelLevel == 0){
			panelLast.setVisible(false);
			panelMain.remove(panelLast);
			if(buttonNumber == 0) { 
				panelMain.add(panelCreate1);
				panelLast = panelCreate1;
				backPath.push(panelCreate1);
				panelLevel++;
			} else {
				panelMain.add(panelTemplate1);
				panelLast = panelTemplate1;
				backPath.push(panelTemplate1);
				panelLevel--;
			}
		} else if(panelLevel == 1){
			panelLast.setVisible(false);
			panelMain.remove(panelLast);
			if(buttonNumber == 0){
				backButtonPressed();
			} else if(buttonNumber == 1){
				System.out.println("test");
			}
		}
	}
}