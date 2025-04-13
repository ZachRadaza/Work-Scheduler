package display;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class HomeTaskBar extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Button[] buttons; //buttons at the side bar
	
	//task bar for home screen
	protected HomeTaskBar(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(MainFrame.darkBgColor);
		this.setMinimumSize(new Dimension(130, 300));
		this.setPreferredSize(new Dimension(130, 720));
		
		addButtons(130, 130);
		
		this.revalidate();
		this.repaint();
		this.isVisible();
	}
	//creates all buttons
	private void addButtons(int width, int length){
		String[] names = {"Home", "New", "Open", "Options", "about"};
		String[] filePaths = {"media/WorkSchedulerLogoClearBg.png", "media/WorkSchedulerLogoClearBg.png", "media/WorkSchedulerLogoClearBg.png", "media/WorkSchedulerLogoClearBg.png", "media/WorkSchedulerLogoClearBg.png"};
		buttons = new Button[names.length];
		for(int i = 0; i < names.length; i++){
			buttons[i] = new Button(names[i], filePaths[i], width, length, i);
			this.add(buttons[i]);
		}
	}
	//checks if other buttons are pressed to give only one rectangle
	protected static void buttonCheck(int n){
		for(int i = 0; i < buttons.length; i++)	if(i != n) buttons[i].unPress();
	}
	
}