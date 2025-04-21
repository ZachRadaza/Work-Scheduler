package display;

public class Settings extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//incorporates task bar at the left, templates, and recents. Follow word display
	protected Settings(){
		super("settings");
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
}