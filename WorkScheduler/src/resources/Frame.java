package resources;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Frame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Color bgColor = Color.WHITE;

	public Frame(){
		
		this.setTitle("Work Scheduler");
		this.setSize(1080, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setBackground(bgColor);
		
		this.setMinimumSize(new Dimension(500, 500));
		
		ImageIcon image = new ImageIcon("media/WorkSchedulerLogo.png");
		this.setIconImage(image.getImage());
		
		this.setVisible(true);
	}
}