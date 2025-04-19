package display;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class TaskBar extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static TaskBarButton[] buttons; //buttons at the side bar
	
	//task bar for home screen
	protected TaskBar(){
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
		String[] filePaths = {"media/taskBar/homeIcon.png", "media/taskBar/newIcon.png", "media/taskBar/openIcon.png", "media/taskBar/optionsIcon.png", "media/taskBar/workSchedulerIcon.png"};
		buttons = new TaskBarButton[names.length];
		for(int i = 0; i < names.length; i++){
			buttons[i] = new TaskBarButton(names[i], filePaths[i], width, length, i);
			this.add(buttons[i]);
		}
	}
	//checks if other buttons are pressed to give only one rectangle
	protected static void buttonCheck(int n){
		for(int i = 0; i < buttons.length; i++)	if(i != n) buttons[i].unPress();
		
	}
	//checks which button was pressed, changes panels based on it
	protected static void buttonPressed(int n){
		switch(n){
			case 0:
				MainFrame.openHomePanel();
				break;
			case 1:
				MainFrame.openNewPanel();
				break;
			case 2:
				MainFrame.openOpenPanel();
				break;
			case 3:
				MainFrame.openSettingPanel();
				break;
			case 4:
				MainFrame.openAboutPanel();
				break;
		}
	}
	
}