package display;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JTextField;

public class HintTextField extends JTextField {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hint;
	
	//copy and pasted form Internet
    public HintTextField(String hint, int columns) {
        super(columns);
        this.hint = hint;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty() && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(MainFrame.darkMidBgColor); // hint text color
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            Insets insets = getInsets();
            FontMetrics fm = g2.getFontMetrics();
            int x = insets.left;
            int y = getHeight() / 2 + fm.getAscent() / 2 - 2;
            g2.drawString(hint, x, y);
            g2.dispose();
        }
    }
}