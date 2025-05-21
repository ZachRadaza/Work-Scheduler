package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import resources.Button;

public class Page extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Color bgColor = MainFrame.darkMidBgColor;
	
	//holds page of main display
	protected Page(String title){
		this.setBackground(bgColor);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		
		this.add(setTitle(title), BorderLayout.NORTH);
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
	
	protected static void setBody(JScrollPane panelScroll, JPanel mainPanel){
		mainPanel.setBackground(MainFrame.darkMidBgColor);
		
		panelScroll.setBorder(null);
		panelScroll.setBackground(MainFrame.darkMidBgColor);
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
	
	private JPanel setTitle(String title){
		JPanel panel = new JPanel();
		panel.setBackground(bgColor);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		//capitalizes first letter
		Character temp = title.charAt(0);
		String titleCaps = temp.toString().toUpperCase() + title.substring(1);
		
		JLabel label = new JLabel(titleCaps);
		label.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 25));
		label.setForeground(MainFrame.brightBgColor);
		label.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		
		panel.add(label);
		
		panel.isVisible();
		panel.revalidate();
		panel.repaint();
		return panel;
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
		buttons.add(new Button("back", 15, 0));
		buttons.get(0).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		panel.add(buttons.get(0), BorderLayout.WEST);
		
		//sets up next panel
		buttons.add(new Button("next", 15, 1));
		buttons.get(1).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		panel.add(buttons.get(1), BorderLayout.EAST);
		
		return panel;
	}
	
	
}