package display;

import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import display.NewPage.New;
import display.OpenPage.Open;
import display.OpenPage.SchedButton;
import resources.Button;

public class Home extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//gui components
	private static JPanel panelMain = new JPanel();
	private static JScrollPane panelScroll = new JScrollPane(panelMain);
	
	//data
	private static LinkedList<Button> createButtons = new LinkedList<>();
	private static LinkedList<SchedButton> editButtons = new LinkedList<>();

	//incorporates task bar at the left, templates, and recents. Follow word display
	protected Home(){
		super("home");
		
		setBody(panelScroll, panelMain);
		setPanel();
		this.add(panelScroll);
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
	
	private void setPanel(){		
		panelMain.setBackground(MainFrame.darkMidBgColor);
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
		
		JPanel create = setCreatePanel();
		JPanel edit = setOpenPanel();
		
		panelMain.add(create);
		panelMain.add(edit);
		
		panelMain.setVisible(true);
		panelMain.revalidate();
		panelMain.repaint();
		//this.add(panelMain);
	}
	
	private static JPanel setCreatePanel(){
		JPanel panel = setPanelHolder("Create Schedules");
		JPanel create = (JPanel) panel.getComponent(1);
		
		JPanel panelBorder = new JPanel();
		panelBorder.setBackground(MainFrame.darkMidBgColor);
		panelBorder.setLayout(new BoxLayout(panelBorder, BoxLayout.X_AXIS));
		panelBorder.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(MainFrame.darkBgColor, 2, true), "Create Schedule for Week", 1, 0, new Font("Microsoft JhengHei", Font.PLAIN, 20), MainFrame.brightBgColor));
		
		JPanel panelHome = new JPanel();
		panelHome.setOpaque(false);
		panelHome.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panelBorder.add(panelHome);
		
		String[] buttonStrings = {"Manually Add Elements", "Use Templates"};
		String[] buttonFilePaths = {"media/NewPage/Create7.png", "media/NewPage/Template7.png"};
		for(int i = 0; i < 2; i++){
			createButtons.add(new Button(buttonStrings[i], 300, 300, 15, i, true, buttonFilePaths[i]));
			panelHome.add(createButtons.get(createButtons.size() - 1));
		}
		panelHome.revalidate();
		panelHome.repaint();
		
		create.add(panelBorder);
		
		panel.revalidate();
		panel.repaint();
		
		return panel;
	}
	
	private static JPanel setOpenPanel(){
		JPanel panel = setPanelHolder("Open Schedules");
		JPanel edit = (JPanel) panel.getComponent(1);
		edit.setMinimumSize(new Dimension(750, 200));
		edit.setMaximumSize(new Dimension(750, 200));
		
		int size = Open.getStationDataSize();
		
		if(size != 0){
			edit.setLayout(new BoxLayout(edit, BoxLayout.X_AXIS));
			for(int i = 0; i < 3; i ++){
				if(i < size){
					JPanel holder = new JPanel();
					holder.setOpaque(false);
					holder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					
					editButtons.add(new SchedButton(3 + (3 * i), Open.getStationData(i).get(0), Open.getEmployeeData(i), Open.getSchedulesNames(i), true));
					holder.add(editButtons.get(editButtons.size() - 1));
					edit.add(holder);
				} else {
					JPanel empty = new JPanel();
					empty.setOpaque(false);
					edit.add(empty);
				}
			}	
		} else {
			JLabel label = new JLabel("No Schedules Found ");
			label.setForeground(MainFrame.brightBgColor);
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.setFont(new Font("Microsoft JhengHei", Font.ITALIC, 20));
			
			edit.add(label);
		}
		
		panel.revalidate();
		panel.repaint();
		
		return panel;
	}
	
	private static JPanel setPanelHolder(String title){
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//title section
		JPanel titleHolder = new JPanel(); //empty border for spacing
		titleHolder.setOpaque(false);
		titleHolder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JPanel titleBorder = new JPanel();//border for line
		titleBorder.setOpaque(false);
		titleBorder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, MainFrame.brightBgColor));
		
		JLabel label = new JLabel(title.toUpperCase());
		label.setForeground(MainFrame.brightBgColor);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 25));
		titleBorder.add(label);
		titleHolder.add(titleBorder);
		
		//section to edit
		JPanel panelSection = new JPanel();
		panelSection.setOpaque(false);
		panelSection.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		Dimension dim = titleBorder.getPreferredSize();
		dim.width = 750;
		titleBorder.setPreferredSize(dim);
		
		panel.add(titleHolder);
		panel.add(panelSection);
		return panel;
	}
	
	public static void updateOpenPanel(){
		panelMain.remove(1);
		panelMain.add(setOpenPanel());
		
		panelMain.revalidate();
		panelMain.repaint();
	}
	
	public static void buttonPress(int buttonNumber){
		if(buttonNumber < 2){
			//try catch done for mouse click and null arguement
			try{
				MainFrame.doTaskBarClick(1);
				New.getButtonPanel0(2 + buttonNumber).mouseClicked(null);
			} catch(NullPointerException e){}
		} else {
			try{
				MainFrame.doTaskBarClick(2);
				Open.getSchedButton(buttonNumber - 3).mouseClicked(null);
			} catch(NullPointerException e){}
		}
	}
}