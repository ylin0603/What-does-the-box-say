package gui.component;

import java.awt.Color;

public class GuiPanel extends GuiComponent {

	protected final int infoX = 100;
	protected final int infoY = 120;
	
	protected Color backgroundColor = new Color(0,0,0,191);
	protected boolean visible = false;
	
	public GuiPanel() {
		this.x = 30;
		this.y = 50;
	}
	
	public void setVisible() {
		visible = true;
	}
	
	public void setInvisible() {
		visible = false;
	}
}
