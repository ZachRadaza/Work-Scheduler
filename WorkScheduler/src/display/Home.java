package display;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class Home extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//incorporates task bar at the left, templates, and recents. Follow word display
	protected Home(){
		this.setBackground(MainFrame.darkBgColor);
		this.setLayout(new BorderLayout());
		
		
		this.add(new HomeTaskBar(), BorderLayout.WEST);
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
}