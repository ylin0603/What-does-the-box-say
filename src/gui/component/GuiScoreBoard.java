package gui.component;

import java.awt.Color;
import java.awt.Graphics;

public class GuiScoreBoard extends GuiPanel {

	private final String[] heading = {
			"Name",
			"Kill Count",
			"Die Count",
			"Mistaken Attack Count"
	};
	
	public GuiScoreBoard() {
		
	}
	
	public void render(Graphics g) {
		if (this.visible) {
			g.setColor(this.backgroundColor);
			g.fillRoundRect(x, y, 840, 420, arcWidth, arcWidth);
			g.setColor(Color.WHITE);
			for (int i=0; i<heading.length; i++) {
				g.drawString(heading[i], this.infoX+i*150, this.infoY);
			}
		}
	}
}
