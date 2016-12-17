package gui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import renderer.data.entity.Item;

public class GuiSmallMap extends GuiComponent {

	private final static int MAP_WIDTH = 120;
	private final static int MAP_HEIGHT = 225;
	private final static int MAP_X = Game.WIDTH*Game.scale - MAP_WIDTH - 20;
	private final static int MAP_Y = Game.HEIGHT*Game.scale - MAP_HEIGHT - 20;
	
	private final int mapScale = 6;
	private Color backgroundColor = new Color(0,0,0,191); 
	
	private List<Item> itemList = null;
	private GuiCircle localPlayer;
	private ArrayList<GuiCircle> functionBags;
	private Point p; // local player
	
	public GuiSmallMap() {
		this.x = MAP_X;
		this.y = MAP_Y;
		p = new Point(MAP_X+5, MAP_Y+5);
		localPlayer = new GuiCircle(p.x, p.y, Color.RED, true);
		functionBags = new ArrayList<GuiCircle>();
		renderBags();
	}
	
	public void render(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRoundRect(x, y, MAP_WIDTH, MAP_HEIGHT, arcWidth, arcWidth);
		localPlayer.render(g);
		for(GuiCircle bag: functionBags) {
			if(bag.isVisible())
				bag.render(g);
		}
	}
	
	public void update() {
		p = DynamicObjectManager.getInstance().getVirtualCharacterXY();
		localPlayer.setLocation(MAP_X+p.x/mapScale, MAP_Y+p.y/mapScale);
		renderBags();
	}
	
	private void renderBags() {
		itemList = DynamicObjectManager.getInstance().getItemList();
		for(Item item: itemList) {
			if(item.getType() == 1) // blood bag
				functionBags.add(new GuiCircle(MAP_X + item.x/mapScale,
						MAP_Y + item.y/mapScale, Color.GREEN, item.getIsDead()));
			if(item.getType() == 2) // bullet bag
				functionBags.add(new GuiCircle(MAP_X + item.x/mapScale,
						MAP_Y + item.y/mapScale, Color.YELLOW, item.getIsDead()));
		}
	}
}
