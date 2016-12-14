package gui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;

public class GuiSmallMap extends GuiComponent {

	private final static int MAP_WIDTH = 120;
	private final static int MAP_HEIGHT = 225;
	private final static int MAP_X = Game.WIDTH*Game.scale - MAP_WIDTH - 20;
	private final static int MAP_Y = Game.HEIGHT*Game.scale - MAP_HEIGHT - 20;
	
	private Color backgroundColor = new Color(0,0,0,191); 
	
	private GuiCircle localPlayer;
	private Point p; // local player
	
	public GuiSmallMap() {
		this.x = MAP_X;
		this.y = MAP_Y;
		//p = DynamicObjectManager.getInstance().getVirtualCharacterXY();
		p = new Point(MAP_X+5, MAP_Y+5);
		localPlayer = new GuiCircle(p.x, p.y, Color.RED);
	}
	
	public void render(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRoundRect(x, y, MAP_WIDTH, MAP_HEIGHT, arcWidth, arcWidth);
		localPlayer.render(g);
	}
	
	public void update() {
		p = DynamicObjectManager.getInstance().getVirtualCharacterXY();
	}
}
