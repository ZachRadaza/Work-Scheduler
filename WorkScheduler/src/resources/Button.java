package resources;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import display.Home;
import display.MainFrame;
import display.NewPage.New;
import display.OpenPage.Open;

public class Button extends JPanel implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private int width;
	private int height;
	private int fontSize;
	private int buttonNumber;
	private boolean photo;
	private String filePath;
	
	private JPanel mainPanel;
	
	private MatteBorder underline;
	
	private boolean hyperlink;
	
	public Button(String name, int width, int height, int fontSize, int buttonNumber, boolean photo, String filePath){
		addMouseListener(this);
		
		//capitalizes first letter
		Character temp = name.charAt(0);
		String nameCaps = temp.toString().toUpperCase() + name.substring(1);
		this.name = nameCaps;
		
		this.width = width;
		this.height = height;
		this.fontSize = fontSize;
		this.buttonNumber = buttonNumber;
		this.photo = photo;
		this.filePath = filePath;
		this.hyperlink = false;
		
		setMainPanel();
		this.add(mainPanel);
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}

	public Button(String name, int fontSize, int buttonNumber){
		addMouseListener(this);
		
		Character temp = name.charAt(0);
		String nameCaps = temp.toString().toUpperCase() + name.substring(1);
		this.name = nameCaps;
		
		this.fontSize = fontSize;
		this.buttonNumber = buttonNumber;
		
		this.width = 0;
		this.height = 0;
		this.photo = false;
		this.filePath = "";
		this.hyperlink = false;
		
		
		setMainPanel();
		this.add(mainPanel);
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
	
	public Button(String name, int fontSize, int buttonNumber, boolean hyperlink){
		addMouseListener(this);
		
		Character temp = name.charAt(0);
		String nameCaps = temp.toString().toUpperCase() + name.substring(1);
		this.name = nameCaps;
		
		this.fontSize = fontSize;
		this.buttonNumber = buttonNumber;
		this.hyperlink = hyperlink;
		
		this.width = 0;
		this.height = 0;
		this.photo = false;
		this.filePath = "";
		
		
		setMainPanel();
		this.add(mainPanel);
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
	
	private void setMainPanel(){
		mainPanel = new JPanel();
		
		if(!(width == 0 && height == 0)) mainPanel.setPreferredSize(new Dimension(this.width, this.height));
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setOpaque(false);
		
		underline = new MatteBorder(0, 0, 2, 0, MainFrame.darkMidBgColor);
		mainPanel.setBorder(underline);
		
		setName();
		
		this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.setOpaque(false);
		
	}
	
	private void setName(){
		JLabel label = new JLabel(this.name);
		
		if(!hyperlink){
			label.setFont(new Font("Microsoft JhengHei", Font.PLAIN, this.fontSize));
			label.setForeground(MainFrame.brightBgColor);
		} else {
			label.setFont(new Font("Microsoft JhengHei", Font.ITALIC, this.fontSize));
			label.setForeground(MainFrame.midBgColor);
			label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, MainFrame.midBgColor));
		}
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		mainPanel.add(label, BorderLayout.CENTER);
		
		if(this.photo){
			JLabel labelIcon = new JLabel();
			labelIcon.setIcon(new ImageIcon(new ImageIcon(this.filePath).getImage().getScaledInstance(this.width - 10, this.height - 10, Image.SCALE_DEFAULT)));
			labelIcon.setAlignmentX(CENTER_ALIGNMENT);
			labelIcon.setAlignmentY(CENTER_ALIGNMENT);
			labelIcon.setOpaque(false);
			labelIcon.setVisible(true);
			labelIcon.revalidate();
			labelIcon.repaint();
			mainPanel.add(labelIcon, BorderLayout.CENTER);
			mainPanel.add(label, BorderLayout.SOUTH);
		}
	}
	
	private void unpress(){
		
		underline = new MatteBorder(0, 0, 2, 0, MainFrame.darkMidBgColor);
		mainPanel.setBorder(underline);
		
		mainPanel.setOpaque(false);
		mainPanel.setBackground(null);
		this.setOpaque(false);
		this.setBackground(null);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		underline = new MatteBorder(0, 0, 2, 0, MainFrame.darkBgColor);
		mainPanel.setBorder(underline);
		
		mainPanel.setOpaque(true);
		this.setOpaque(true);
		mainPanel.setBackground(MainFrame.darkBgColor);
		this.setBackground(MainFrame.darkBgColor);
		
		switch(MainFrame.getLastPanelInt()){
			case 0:
				Home.buttonPress(buttonNumber);
				break;
			case 1:
				New.buttonPress(buttonNumber);
				break;
			case 2:
				Open.buttonPress(buttonNumber);
				break;
			case 3:
				break;
			case 4:
				break;
		}
		
		unpress();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!photo){
			underline = new MatteBorder(0, 0, 2, 0, MainFrame.darkBgColor);
			mainPanel.setBorder(underline);
		} else {
			this.setBorder(BorderFactory.createLineBorder(MainFrame.brightBgColor, 2, true));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!photo){
			underline = new MatteBorder(0, 0, 2, 0, MainFrame.darkMidBgColor);
			mainPanel.setBorder(underline);
		} else {
			this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mainPanel.setOpaque(true);
		this.setOpaque(true);
		mainPanel.setBackground(MainFrame.darkBgColor);
		this.setBackground(MainFrame.darkBgColor);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.setOpaque(false);
		mainPanel.setOpaque(false);
		mainPanel.setBackground(null);
		this.setBackground(null);
	}
}