package display;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Settings extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//incorporates task bar at the left, templates, and recents. Follow word display
	protected Settings(){
		this.setBackground(MainFrame.darkMidBgColor);
		this.setLayout(new BorderLayout());
		
		this.add(new JLabel("Settings"));
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
}