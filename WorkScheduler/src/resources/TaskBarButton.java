package resources;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import display.MainFrame;
import display.TaskBar;

public class TaskBarButton extends JPanel implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel main;
	private MatteBorder borderSide;
	private boolean pressed = false;
	private String name; //text
	private String filePath; //file path to picture
	private int width; //wanted width of button
	private int height; //wanted height of button
	private int buttonNumber; //number of button
	
	//buttons to use for program at the task bar
	public TaskBarButton(String name, String filePath, int width, int height, int buttonNumber){
		addMouseListener(this);
		
		main = new JPanel();//inner panel for inner border
		borderSide = new MatteBorder(0, 2, 0, 0, MainFrame.darkBgColor);
		
		this.name = name;
		this.filePath = filePath;
		this.width = width;
		this.height = height;
		this.buttonNumber = buttonNumber;
		//sets class panel
		this.setBackground(MainFrame.darkBgColor);
		this.setLayout(new GridBagLayout());
		
		this.setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 2));
		this.setMaximumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		this.add(main);
		
		main.setBackground(MainFrame.darkBgColor);
		main.setMaximumSize(new Dimension(width - 10, height - 10));
		main.setPreferredSize(new Dimension(width - 10, height - 10));
		main.setBorder(borderSide);
		
		addJLabel(MainFrame.brightBgColor);
		
		main.setAlignmentY(CENTER_ALIGNMENT);
		main.setAlignmentX(CENTER_ALIGNMENT);
		
		this.revalidate();
		this.repaint();
		this.isVisible();
	}
	//adds photo and text into main panel
	private void addJLabel(Color color){
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		
		JLabel labelIcon = new JLabel();
		labelIcon.setIcon(new ImageIcon(new ImageIcon(this.filePath).getImage().getScaledInstance(this.width/2, this.height/2, Image.SCALE_DEFAULT)));
		labelIcon.setAlignmentX(CENTER_ALIGNMENT);
		labelIcon.setAlignmentY(CENTER_ALIGNMENT);
		labelIcon.setOpaque(false);
		
		JLabel labelText = new JLabel(this.name.toUpperCase());
		labelText.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 17));
		labelText.setForeground(color);
		labelText.setAlignmentX(CENTER_ALIGNMENT);
		labelText.setAlignmentY(CENTER_ALIGNMENT);
		
		main.add(labelIcon);
		main.add(labelText);
		
		labelIcon.isVisible();
		labelText.isVisible();
	}

	//"unpresses" button and removes all effects
	public void unPress(){
		pressed = false;
		
		main.removeAll();
		
		addJLabel(MainFrame.brightBgColor);
		
		this.setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 2));
		
		main.setBorder(null);
		borderSide = new MatteBorder(0, 2, 0, 0, MainFrame.darkBgColor);
		main.setBorder(borderSide);
		
		this.revalidate();
		this.repaint();
		this.isVisible();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		pressed = true;
		
		TaskBar.buttonCheck(buttonNumber);
		TaskBar.buttonPressed(buttonNumber);
		
		main.removeAll();
		
		addJLabel(MainFrame.midBgColor);
		
		this.setBorder(BorderFactory.createLineBorder(MainFrame.brightBgColor, 2));
		borderSide = new MatteBorder(0, 2, 0, 0, MainFrame.midBgColor);
		main.setBorder(borderSide);
		
		this.revalidate();
		this.repaint();
		this.isVisible();
		
		System.out.println("test");
	}
	
	//when mouse is hovered
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!pressed){
			main.setBorder(null);
			borderSide = new MatteBorder(0, 2, 0, 0, MainFrame.darkMidBgColor);
			main.setBorder(borderSide);
		}
		
		this.revalidate();
		this.repaint();
		this.isVisible();
	}

	//when mouse is unhovered
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!pressed) {
			main.setBorder(null);
			borderSide = new MatteBorder(0, 2, 0, 0, MainFrame.darkBgColor);
			main.setBorder(borderSide);
		}
		
		this.revalidate();
		this.repaint();
		this.isVisible();
	}

	//when it is pressed
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setBorder(BorderFactory.createLineBorder(MainFrame.brightBgColor, 2));
		borderSide = new MatteBorder(0, 2, 0, 0, MainFrame.midBgColor);
		main.setBorder(borderSide);
		
		this.revalidate();
		this.repaint();
		this.isVisible();
	}

	//unheld?
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		this.setBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 2));
		
		main.setBorder(null);
		borderSide = new MatteBorder(0, 2, 0, 0, MainFrame.darkBgColor);
		main.setBorder(borderSide);
		
		this.revalidate();
		this.repaint();
		this.isVisible();
	}
}