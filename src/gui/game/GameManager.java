package gui.game;

import java.awt.Graphics;
import java.util.ArrayList;
import gui.component.GuiBloodBar;
import gui.component.GuiComponent;
import gui.component.GuiHelpPanel;
import gui.component.GuiRoomInfo;
import gui.component.GuiScoreBoard;
import gui.component.GuiSmallMap;

public class GameManager {

	private static GameManager gameManager;
	private ArrayList<GuiComponent> componentList = new ArrayList<GuiComponent>();
	
	private GuiBloodBar bloodBar;
	private GuiRoomInfo roomInfo;
	private GuiSmallMap smallMap;
	private GuiHelpPanel helpPanel;
	private GuiScoreBoard scoreBoard;
	
	private GameManager() {
		init();
		componentList.add(bloodBar);
		componentList.add(roomInfo);
		componentList.add(smallMap);
		componentList.add(helpPanel);
		componentList.add(scoreBoard);
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
		smallMap = new GuiSmallMap();
		helpPanel = new GuiHelpPanel();
		scoreBoard = new GuiScoreBoard();
	}
	
	public void render(Graphics g) {
		for (GuiComponent component: this.componentList) {
			component.render(g);
		}
	}
	
	public void update() {
		for (GuiComponent component: this.componentList) {
			component.update();
		}
	}
	
	public void setWeapon() {
		roomInfo.setWeopon();
	}
	
	public void openInfo(String type) {
		if (type == "help") {
			helpPanel.setVisible();
			scoreBoard.setInvisible();
		} else if (type == "tab"){
			helpPanel.setInvisible();
			scoreBoard.setVisible();
		}
	}
}
