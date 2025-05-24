package display;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class About extends Page{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final JPanel panelMain = new JPanel();
	private static final JScrollPane panelScroll = new JScrollPane(panelMain);

	//incorporates task bar at the left, templates, and recents. Follow word display
	protected About(){
		super("about");
		
		setBody(panelScroll, panelMain);
		setPanel();
		this.add(panelScroll);
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
	
	private static void setPanel(){
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
		
		panelMain.add(setHeader("Work Scheduler"));
		panelMain.add(setAppDesc());
		
		panelMain.setVisible(true);
		panelMain.revalidate();
		panelMain.repaint();
	}
	
	private static JPanel setAppDesc(){
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panelImage = new JPanel();
		panelImage.setOpaque(false);
		
		JPanel panelDesc = new JPanel();
		panelDesc.setOpaque(false);
		panelDesc.setLayout(new BoxLayout(panelDesc, BoxLayout.Y_AXIS));
		
		JLabel image = new JLabel();
		image.setIcon(new ImageIcon(new ImageIcon("media/taskBar/workSchedulerIcon.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT)));
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setVerticalAlignment(SwingConstants.CENTER);
		image.setOpaque(false);
		panelImage.add(image);
		
		String filePath = "src/resources/AboutDesc.txt";
		String text = "";
		try{
			File file = new File(filePath);
			Scanner scanner = new Scanner(file);
			String token = "";
			int counter = 0;
			while(scanner.hasNext()){
				token = scanner.next();
				text += " " + token.trim();
				counter += token.length();
				if(counter >= 50){
					panelDesc.add(setLine(text, false));
					text = "";
					counter = 0;
				}
			}
			scanner.close();
		} catch(FileNotFoundException e){
			//System.err.println(e);
		}
		
		panelDesc.add(setLine("Created By: Zachary Juls Aballe Radaza", true));
		
		panel.add(panelImage);
		panel.add(panelDesc);
		return panel;
	}
	
	private static JPanel setLine(String line, boolean italic){
		JLabel label = new JLabel(line.trim());
		label.setOpaque(false);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));
		if(italic) label.setFont(new Font("Microsoft JhengHei", Font.ITALIC, 20));
		label.setForeground(MainFrame.brightBgColor);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panel.add(label);
		return panel;
	}
}