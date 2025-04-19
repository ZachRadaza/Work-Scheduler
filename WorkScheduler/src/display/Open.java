package display;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Open extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//incorporates task bar at the left, templates, and recents. Follow word display
	protected Open(){
		this.setBackground(MainFrame.darkMidBgColor);
		this.setLayout(new BorderLayout());
		
		this.add(new JLabel("Open"));
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
}