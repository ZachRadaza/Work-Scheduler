package display.OpenPage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import data.EmployeeData;
import data.StationData;
import display.MainFrame;
import display.Page;
import display.Schedule;

public class Open extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JPanel mainPanel = new JPanel();
	private static JScrollPane panelScroll = new JScrollPane(mainPanel);
	private static int panelLevel = 0;
	private static JPanel panelLast;
	private static JPanel[] allPanels = new JPanel[4];
	private static JPanel panel0 = new JPanel();

	private static LinkedList<SchedButton> schedules = new LinkedList<>();
	private static ArrayList<ArrayList<StationData>> stationData = new ArrayList<>();
	private static ArrayList<ArrayList<EmployeeData>> empData = new ArrayList<>();
	private static ArrayList<Integer> days = new ArrayList<>();
	
	//incorporates task bar at the left, templates, and recents. Follow word display
	public Open(){
		super("open");
		
		setBody(panelScroll, mainPanel);
		setPanel();
		this.add(panelScroll);
		panelLast = panel0;
		mainPanel.add(panel0);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	public static void setPanel(){
		allPanels[0] = panel0;
		panel0.removeAll();
		panel0.setOpaque(false);
		panel0.setLayout(new BoxLayout(panel0, BoxLayout.Y_AXIS));
		if(stationData.size() != 0){
			int height = stationData.size() / 3;
			if(height < 1 || height % 3 != 0) height++;
			System.out.println("height % 3 = " + height % 3);
			JPanel[] row = new JPanel[height];
			for(int i = 0; i < row.length; i++){
				row[i] = new JPanel();
				row[i].setLayout(new GridLayout(1, 3, 10, 10));
				row[i].setMinimumSize(new Dimension(750, 300));
				row[i].setMaximumSize(new Dimension(750, 300));
				row[i].setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
				row[i].setOpaque(false);
				
				//adds schedules
				for(int j = 0; j < 3; j++){
					if((3 * i) + j < stationData.size()){
						schedules.add(new SchedButton(((3 * i) + j) * 3, stationData.get((3 * i) + j).get(0), empData.get((3 * i) + j)));
						row[i].add(schedules.get(schedules.size() - 1), j);
					} else {
						JPanel empty = new JPanel();
						empty.setOpaque(false);
						row[i].add(empty, j);
					}
				}
				panel0.add(row[i]);
			}
			
		} else {
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			panel.setPreferredSize(new Dimension(750, 100));
			
			JLabel label = new JLabel("No old schedules found ");
			label.setFont(new Font("Microsoft JhengHei", Font.ITALIC, 20));
			label.setForeground(MainFrame.brightBgColor);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(label);
			panel0.add(panel);
		}
		
		panel0.setVisible(true);
		panel0.revalidate();
		panel0.repaint();
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
	
	public static void addStationData(ArrayList<StationData> data){
		stationData.add(data);
	}
	
	public static void addEmpData(ArrayList<EmployeeData> data){
		empData.add(data);
	}
	
	public static void addDays(int day){
		days.add(day);
	}
	
	public static void replaceStationData(ArrayList<StationData> data, int i){
		stationData.set(i, data);
	}
	
	public static void replaceEmpData(ArrayList<EmployeeData> data, int i){
		empData.set(i, data);
	}
	
	public static void replaceDays(int day, int i){
		days.set(i, day);
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
	
	public static void buttonPress(int buttonNumber){
		if(panelLevel == 0){
			if(buttonNumber % 3 == 0){ //open schedule
				allPanels[1] = new Edit(stationData.get((buttonNumber - 1) / 3), empData.get((buttonNumber - 1) / 3), days.get((buttonNumber - 1) / 3), true, (buttonNumber - 1) / 3);
				footerPress(1);
			} else if(buttonNumber % 3 == 1){ //remove
				schedules.remove((buttonNumber - 1) / 3);
				stationData.remove((buttonNumber - 1) / 3);
				empData.remove((buttonNumber - 1) / 3);
				days.remove((buttonNumber - 1) / 3);
				setPanel();
			} else if(buttonNumber % 3 == 2){ //rename
				String input = JOptionPane.showInputDialog(null, "Enter New Title for the Schedule", "Rename Schedule", 0);
		        if(input != null) {
		            schedules.get((buttonNumber - 2)/ 3).rename(input);
		        } 
			}
		} else if(panelLevel == 1){
			((Edit) allPanels[1]).buttonPressed(buttonNumber);
		} else if(panelLevel == 2){
			((Edit) allPanels[2]).buttonPressed(buttonNumber);
		} else {
			((Schedule) allPanels[3]).buttonPressed(buttonNumber);
		}
	}
}