package gui.component;

import java.awt.Color;
import java.awt.Graphics;

import pseudomain.Game;

public class GuiRoomInfo extends GuiComponent {

	private final static int INFO_HEIGHT = 30;
	private final static String KNIFE = "Knife";
	private final static String GUN = "Gun";
	
	private GuiLabel roomOwner, playerName, weaponName, bulletCount;
	private String weapon = KNIFE;
	private int bullet = 2;
	
	public GuiRoomInfo() {
		this.x = 0;
		this.y = 0;
		roomOwner = new GuiLabel("Room Owner : ", x+30, y+20, Color.WHITE); // DOM.get
		playerName = new GuiLabel("Player Name : ", x+230, y+20, Color.WHITE); // DOM.get
		weaponName = new GuiLabel("Weapon : "+weapon, x+430, y+20, Color.WHITE);
		bulletCount = new GuiLabel("Bullet : "+bullet, x+630, y+20, Color.WHITE);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, Game.WIDTH * Game.scale, INFO_HEIGHT);
		roomOwner.render(g);
		playerName.render(g);
		weaponName.render(g);
		bulletCount.render(g);
	}
	
	public void update() {
		bullet = 2; // bullet => DOM.get
		bulletCount.setText("Bullet : "+bullet);
	}
	
	public void setWeopon() {
		if (weapon == KNIFE) {
			weapon = GUN;
		} else {
			weapon = KNIFE;
		}
		weaponName.setText("Weapon : "+weapon);
	}
}
