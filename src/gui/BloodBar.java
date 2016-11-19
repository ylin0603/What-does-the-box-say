package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BloodBar extends JPanel{
	
	private int width = 200, height = 15;
	private int blood;

	public BloodBar() {
		blood = 200;
		this.setBounds(10, 530, width, height);
		this.setLayout(new BorderLayout());
		JLabel showBlood = new JLabel(blood/2+" / 100");
		showBlood.setForeground(Color.BLACK);
		this.add(showBlood, BorderLayout.CENTER);
	}
	
	// Draw blood bar.
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(0, 0, blood, height);
        g.setColor(Color.WHITE);
        g.fillRect(blood, 0, width-blood, height);
    }
}
