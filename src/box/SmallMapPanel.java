package box;

import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SmallMapPanel extends JPanel {

	private Color backgroundColor = new Color(0,0,0,191);
	
	public SmallMapPanel() {
		this.setBackground(backgroundColor);
		this.setBounds(460, 420, 110, 130);
	}
}
