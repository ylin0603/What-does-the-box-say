package gui.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GuiLabel extends GuiComponent {

	private String text;
	private Color textColor;
	
	public GuiLabel(String text, int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.textColor = color;
	}
	
	public void render(Graphics g) {
		g.setColor(textColor);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		g.drawString(text, x, y);
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
