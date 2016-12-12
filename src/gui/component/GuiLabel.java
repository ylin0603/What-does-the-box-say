package gui.component;

import java.awt.Color;
import java.awt.Graphics;

public class GuiLabel extends GuiComponent {

	private String text;
	
	public GuiLabel(String text, int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void render(Graphics g) {
		g.drawString(text, x, y);
	}
}
