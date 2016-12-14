package gui.component;

import java.awt.Color;
import java.awt.Graphics;

public class GuiCircle extends GuiComponent {

	private Color color;
	private final static int DIAMETER = 5;
	
	public GuiCircle(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, DIAMETER, DIAMETER);
	}
}
