package gui.game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gui.component.GuiBloodBar;
import gui.component.GuiComponent;
import gui.component.GuiRoomInfo;

public class GameManager {

	private static GameManager gameManager;
	private List<GuiComponent> componentList 
		= Collections.synchronizedList(new ArrayList<GuiComponent>());
	
	private GuiBloodBar bloodBar;
	private GuiRoomInfo roomInfo;
	
	private GameManager() {
		init();
		componentList.add(bloodBar);
		componentList.add(roomInfo);
	}
	
	public static GameManager getInsatance() {
		if (gameManager == null) {
			gameManager = new GameManager();
		}
		return gameManager;
	}
	
	private void init() {
		bloodBar = new GuiBloodBar();
		roomInfo = new GuiRoomInfo();
	}
	
	public void render(Graphics g) {
		for (GuiComponent component: this.componentList) {
			component.render(g);
		}
	}
}
