package display;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Home extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panelMain;

	//incorporates task bar at the left, templates, and recents. Follow word display
	protected Home(){
		super("home");
		
		setBody();
		this.add(panelMain, BorderLayout.CENTER);
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
	
	private void setBody(){
		panelMain = new JPanel();
		
		panelMain.add(new JLabel("testeaerarsfsfscessesefsefesfesfes"));
	
		panelMain.isVisible();
		panelMain.revalidate();
		panelMain.repaint();
	}
}