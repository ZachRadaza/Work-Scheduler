package display.OpenPage;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import data.EmployeeData;
import data.StationData;
import display.MainFrame;
import display.Page;

public class Open extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JPanel mainPanel = new JPanel();
	private static JScrollPane panelScroll;

	private static LinkedList<SchedButton> schedules = new LinkedList<>();
	private static ArrayList<ArrayList<StationData>> stationData = new ArrayList<>();
	private static ArrayList<ArrayList<EmployeeData>> empData = new ArrayList<>();
	
	//incorporates task bar at the left, templates, and recents. Follow word display
	public Open(){
		super("open");
		
		setPanel();
		this.add(mainPanel);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	public static void setBody(){
		mainPanel.setOpaque(false);
		
		panelScroll = new JScrollPane(mainPanel);
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
	
	public static void setPanel(){
		mainPanel.removeAll();
		System.out.println("number of station: " + stationData.size());
		if(stationData.size() != 0){
			int height = stationData.size() / 3;
			if(height < 1 || height % 3 != 0) height++;
			System.out.println("height: " +  height);
			System.out.println("5/3 = " + Math.ceil(5 / 3));
			mainPanel.setLayout(new GridLayout(height, 3, 5, 5));
			mainPanel.setOpaque(false);
			mainPanel.setBorder(BorderFactory.createLineBorder(MainFrame.midBgColor));
			
			//adds schedules
			for(int i = 0; i < (3 * height); i++){
				if(i < stationData.size()){
					schedules.add(new SchedButton(i, stationData.get(i).get(0), empData.get(i)));
					mainPanel.add(schedules.get(schedules.size() - 1), i);
				} else {
					JPanel empty = new JPanel();
					empty.setOpaque(false);
					mainPanel.add(empty, i);
				}
			}
		} else {
			mainPanel.setOpaque(false);
			
			JLabel label = new JLabel("No old schedules found ");
			label.setFont(new Font("Microsoft JhengHei", Font.ITALIC, 20));
			label.setForeground(MainFrame.brightBgColor);
			mainPanel.add(label);
		}
		
		mainPanel.setVisible(true);
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	public static void addStationData(ArrayList<StationData> data){
		stationData.add(data);
	}
	
	public static void addEmpData(ArrayList<EmployeeData> data){
		empData.add(data);
	}
	
	private static void buttonPress(int buttonNumber){
		
	}
}