package display;

public class About extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//incorporates task bar at the left, templates, and recents. Follow word display
	protected About(){
		super("about");
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
}