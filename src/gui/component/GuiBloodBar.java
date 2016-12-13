package gui.component;

import java.awt.Color;
import java.awt.Graphics;

import pseudomain.Game;

public class GuiBloodBar extends GuiComponent {

	private final static int BLOODBAR_X = 30;
	private final static int BLOODBAR_Y = 440;
	private final static int BLOODBAR_WIDTH = 300;
	private final static int BLOODBAR_HEIGHT = 20;
	
	private static int bloodWidth = BLOODBAR_WIDTH;
	
	private GuiLabel bloodLabel;
	private String bloodText = " / 100";
	
	public GuiBloodBar() {
		this.x = BLOODBAR_X;
		this.y = BLOODBAR_Y;
		bloodLabel = new GuiLabel(bloodWidth/Game.scale + bloodText, 
				x+5, y+15, Color.BLACK);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, BLOODBAR_WIDTH, BLOODBAR_HEIGHT);
		g.setColor(Color.RED);
		g.fillRect(x, y, bloodWidth, BLOODBAR_HEIGHT);
		bloodLabel.render(g);
	}
	
	public void update(int blood) {
		bloodWidth = blood * Game.scale;
		bloodLabel.setString(bloodWidth/Game.scale + bloodText);
	}
}
