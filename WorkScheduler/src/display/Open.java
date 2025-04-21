package display;

public class Open extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//incorporates task bar at the left, templates, and recents. Follow word display
	protected Open(){
		super("open");
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
}