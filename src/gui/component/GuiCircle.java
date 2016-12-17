package gui.component;

import java.awt.Color;
import java.awt.Graphics;

public class GuiCircle extends GuiComponent {

	private final static int DIAMETER = 5;
	private Color color;
	private boolean visible = true;
	
	public GuiCircle(int x, int y, Color color, boolean visible) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.visible = visible;
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, DIAMETER, DIAMETER);
	}
	
	public void setLocation(int locX, int locY) {
		x = locX;
		y = locY;
	}
	
	public boolean isVisible() {
		return visible;
	}
}
