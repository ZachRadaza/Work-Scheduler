package display;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class About extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//incorporates task bar at the left, templates, and recents. Follow word display
	protected About(){
		this.setBackground(MainFrame.darkMidBgColor);
		this.setLayout(new BorderLayout());
		
		this.add(new JLabel("About"));
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
}