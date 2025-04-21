package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Page extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Color bgColor = MainFrame.darkMidBgColor;
	
	protected Page(String title){
		this.setBackground(bgColor);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		
		this.add(setTitle(title), BorderLayout.NORTH);
		
		this.isVisible();
		this.revalidate();
		this.repaint();
	}
	
	private JPanel setTitle(String title){
		JPanel panel = new JPanel();
		panel.setBackground(bgColor);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		//capitalizes first letter
		Character temp = title.charAt(0);
		String titleCaps = temp.toString().toUpperCase() + title.substring(1);
		
		JLabel label = new JLabel(titleCaps);
		label.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 25));
		label.setForeground(MainFrame.brightBgColor);
		label.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		
		panel.add(label);
		
		panel.isVisible();
		panel.revalidate();
		panel.repaint();
		return panel;
	}
	
}