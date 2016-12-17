package gui.component;

import java.awt.Graphics;

import renderer.data.DynamicObjectManager;

public class GuiComponent {

	protected int x, y;
	protected final int arcWidth = 10;
	protected DynamicObjectManager dom = null;
	
	public GuiComponent() {
		dom = DynamicObjectManager.getInstance();
	}
	
	public void render(Graphics g) {
		
	}
	
	public void update() {
		
	}
}
