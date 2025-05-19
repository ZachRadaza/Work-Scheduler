package display.OpenPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import data.EmployeeData;
import data.StationData;
import display.MainFrame;
import resources.Button;

public class SchedButton extends JPanel implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int buttonNumber;
	private StationData stationData;
	private ArrayList<EmployeeData> empData;
	
	private JLabel title;
	private Button[] buttons;
	
	public SchedButton(int buttonNumber, StationData stationData, ArrayList<EmployeeData> empData){
		addMouseListener(this);
		
		this.buttonNumber = buttonNumber;
		this.stationData = stationData;
		this.empData = empData;
		
		setMainPanel();
		
		//for preferred size
		Dimension dimension = this.getPreferredSize();
		dimension.height = 200;
		this.setMaximumSize(dimension);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	private void setMainPanel(){
		this.setOpaque(false);
		this.setBorder(BorderFactory.createLineBorder(MainFrame.midBgColor, 1));
		this.setLayout(new BorderLayout());
		
		this.add(setButtons(), BorderLayout.NORTH);
		this.add(setMiddleIcon(), BorderLayout.CENTER);
		this.add(setTitle(), BorderLayout.SOUTH);
	}
	
	private JPanel setButtons(){
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		panel.setLayout(new BorderLayout());
		
		buttons = new Button[2];
		//remove
		buttons[0] = new Button("x", 15, 0);
		panel.add(buttons[0], BorderLayout.EAST);
		//rename
		buttons[1] = new Button("A|", 15, 1);
		panel.add(buttons[1], BorderLayout.WEST);
		
		return panel;
	}

	private JPanel setMiddleIcon(){
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//day it is at
		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		int dayAt = findAvailDay();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 1), days[dayAt], 2, 0, new Font("Microsoft JhengHei", Font.PLAIN, 10), MainFrame.brightBgColor));
		
		//station name
		JPanel station = new JPanel();
		station.setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 1));
		station.setBackground(MainFrame.darkBgColor);
		JLabel stationName = new JLabel(stationData.getname().toUpperCase());
		stationName.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 8));
		stationName.setForeground(MainFrame.brightBgColor);
		station.add(stationName);
		
		//table for station
		JPanel table = new JPanel();
		table.setBackground(MainFrame.darkMidBgColor);
		table.setLayout(new GridLayout(3, 2));
		for(int i = 0; i < 2 * 3; i++){
			if(i < empData.size()){
				JPanel tablePanel = new JPanel();
				tablePanel.setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 1));
				tablePanel.setBackground(MainFrame.midBgColor);
				JLabel names = new JLabel(empData.get(i).getNameLast() + ", " + empData.get(i).getNameFirst());
				names.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 8));
				names.setForeground(MainFrame.brightBgColor);
				names.setHorizontalAlignment(SwingConstants.LEFT);
				tablePanel.add(names);
				table.add(tablePanel, i);
				
				i++;
				
				JPanel tablePanel1 = new JPanel();
				tablePanel1.setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 1));
				tablePanel1.setBackground(MainFrame.darkMidBgColor);
				String[] times = {empData.get(i).getAvailSun(), empData.get(i).getAvailMon(), empData.get(i).getAvailTue(), empData.get(i).getAvailWed(), empData.get(i).getAvailThur(), empData.get(i).getAvailFri(), empData.get(i).getAvailSat()};
				JLabel names1 = new JLabel(times[dayAt]);
				names.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 8));
				names.setForeground(MainFrame.brightBgColor);
				names.setHorizontalAlignment(SwingConstants.CENTER);
				tablePanel1.add(names1);
				table.add(tablePanel1, i);
			} else {
				JPanel empty = new JPanel();
				empty.setBackground(MainFrame.midBgColor);
				JLabel empty2 = new JLabel();
				empty2.setBackground(MainFrame.darkMidBgColor);
				table.add(empty, i);
				i++;
				table.add(empty2, i);
			}
		}
		
		panel.add(station);
		panel.add(table);
		return panel;
	}
	
	private JPanel setTitle(){
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		title = new JLabel("Created Schedule " + buttonNumber);
		title.setForeground(MainFrame.brightBgColor);
		title.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(title);
		
		return panel;
	}
	
	private int findAvailDay(){
		int index = 0;
		String[] avails = {empData.get(0).getAvailSun(), empData.get(0).getAvailMon(), empData.get(0).getAvailTue(), empData.get(0).getAvailWed(), empData.get(0).getAvailThur(), empData.get(0).getAvailFri(), empData.get(0).getAvailSat()};
		for(int i = 0; i < 7; i++){
			if(!avails[i].equals("0:00-0:00")){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public void rename(String newName){
		title.setText(newName);
		
		title.setVisible(true);
		title.revalidate();
		title.repaint();
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setBackground(MainFrame.brightBgColor);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setBackground(MainFrame.midBgColor);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setBackground(MainFrame.midBgColor);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setBackground(null);
		this.setOpaque(false);
		
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
}