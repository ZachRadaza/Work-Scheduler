package display;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

//home screen
public class MainFrame{
	
	protected static Frame mainFrame;
	
	protected static Color darkBgColor = new Color(39, 48, 67);
	protected static Color darkMidBgColor = new Color(79, 88, 107);
	protected static Color midBgColor = new Color(175, 181, 204);
	protected static Color brightBgColor = new Color(239, 246, 238);
	
	
	//main frame to use, holds all panels
	public MainFrame(){
		mainFrame = new Frame();
		mainFrame.isVisible();
		startUp();
		
		mainFrame.revalidate();
		mainFrame.repaint();
		mainFrame.isVisible();
		
		//listFonts();
	}
	
	private void startUp(){
		mainFrame.add(new Home());
	}
	
	private void listFonts(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
        String[] fontNames = ge.getAvailableFontFamilyNames();

        for (String fontName : fontNames) {
            System.out.println(fontName);
        }
	}
	
}