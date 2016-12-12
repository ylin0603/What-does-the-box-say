package gui.component;

import java.awt.Color;
import java.awt.Graphics;

public class GuiBloodBar extends GuiComponent {

	private final static int BLOODBAR_X = 30;
	private final static int BLOODBAR_Y = 440;
	private final static int BLOODBAR_WIDTH = 300;
	private final static int BLOODBAR_HEIGHT = 20;
	
	private static int scale = 3;
	private static int bloodWidth = BLOODBAR_WIDTH;
	private Graphics g;
	
	public GuiBloodBar() {
		this.x = BLOODBAR_X;
		this.y = BLOODBAR_Y;
	}
	
	public void render(Graphics g) {
		this.g = g;
		this.g.setColor(Color.WHITE);
		this.g.fillRect(x, y, BLOODBAR_WIDTH, BLOODBAR_HEIGHT);
		drawBlood();
	}
	
	private void drawBlood() {
		g.setColor(Color.RED);
		g.fillRect(x, y, bloodWidth, BLOODBAR_HEIGHT);
	}
	
	public void update(int blood) {
		bloodWidth = blood * scale;
		drawBlood();
	}
}
