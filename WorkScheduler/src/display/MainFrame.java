package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;

import javax.swing.JPanel;

import display.NewPage.New;
import resources.Frame;

//home screen
public class MainFrame{
	//main frame being used
	protected static Frame mainFrame;
	
	public static final Color darkBgColor = new Color(39, 48, 67);
	public static final Color darkMidBgColor = new Color(79, 88, 107);
	public static final Color midBgColor = new Color(175, 181, 204);
	public static final Color brightBgColor = new Color(239, 246, 238);
	
	private TaskBar taskBar;
	//panels for the different pages
	private static Home homePanel;
	private static New newPanel;
	private static Open openPanel;
	private static Settings settingPanel;
	private static About aboutPanel;
	//holds the last/current panel being used, helps remove it for replacing the front
	private static JPanel lastPanel;
	private static int lastPanelInt; // 0 is home, 4 is about, for which buttons to check when clicked
	
	//main frame to use, holds all panels
	public MainFrame(){
		mainFrame = new Frame();
		mainFrame.isVisible();
		
		mainFrame.getContentPane().setLayout(new BorderLayout());
		
		taskBar = new TaskBar();
		mainFrame.add(taskBar, BorderLayout.WEST);

		homePanel = new Home();
		newPanel = new New();
		openPanel = new Open();
		settingPanel = new Settings();
		aboutPanel = new About();
		
		startUp();
		
		mainFrame.revalidate();
		mainFrame.repaint();
		mainFrame.isVisible();
		
		//listFonts();
	}
	
	protected void startUp(){
		mainFrame.add(homePanel);
		lastPanel = homePanel;
		lastPanelInt = 0;
	}
	
	protected static void openHomePanel(){
		mainFrame.remove(lastPanel);
		mainFrame.add(homePanel);
		homePanel.isVisible();
		homePanel.revalidate();
		homePanel.repaint();
		lastPanel = homePanel;
		lastPanelInt = 0;
	}
	
	protected static void openNewPanel(){
		mainFrame.remove(lastPanel);
		mainFrame.add(newPanel);
		newPanel.isVisible();
		newPanel.revalidate();
		newPanel.repaint();
		lastPanel = newPanel;
		lastPanelInt = 1;
	}
	
	protected static void openOpenPanel(){
		mainFrame.remove(lastPanel);
		mainFrame.add(openPanel);
		openPanel.isVisible();
		openPanel.revalidate();
		openPanel.repaint();
		lastPanel = openPanel;
		lastPanelInt = 2;
	}
	
	protected static void openSettingPanel(){
		mainFrame.remove(lastPanel);
		mainFrame.add(settingPanel);
		settingPanel.isVisible();
		settingPanel.revalidate();
		settingPanel.repaint();
		lastPanel = settingPanel;
		lastPanelInt = 3;
	}
	
	protected static void openAboutPanel(){
		mainFrame.remove(lastPanel);
		mainFrame.add(aboutPanel);
		aboutPanel.isVisible();
		aboutPanel.revalidate();
		aboutPanel.repaint();
		lastPanel = aboutPanel;
		lastPanelInt = 4;
	}
	
	public static int getLastPanelInt(){
		return lastPanelInt;
	}
	
	private void listFonts(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
        String[] fontNames = ge.getAvailableFontFamilyNames();

        for (String fontName : fontNames) {
            System.out.println(fontName);
        }
	}
	
}