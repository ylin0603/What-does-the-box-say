package gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class BoxBox extends JLabel {

	private int positionX, positionY;
	private ImageIcon boxImage;
	
	public BoxBox(int x, int y) {
		boxImage = new ImageIcon("res/box.png");
		setIcon(boxImage);
		setPosition(x, y);
	}
	
	public void setPosition(int x, int y) {
		this.positionX = x;
		this.positionY = y;
		setBounds(x, y, boxImage.getIconWidth(), boxImage.getIconHeight());
	}
	
	public int getPositionX() {
		return positionX;
	}
	
	public int getPositionY() {
		return positionY;
	}
}
