package gui.game;

import java.awt.Graphics;
import java.util.ArrayList;
import gui.component.GuiBloodBar;
import gui.component.GuiComponent;
import gui.component.GuiHelpPanel;
import gui.component.GuiPlayerAngle;
import gui.component.GuiStatusBar;
import gui.component.GuiScoreBoard;
import gui.component.GuiSmallMap;

public class GameManager {

    private static GameManager gameManager;
    private ArrayList<GuiComponent> componentList =
            new ArrayList<GuiComponent>();

    private GuiBloodBar bloodBar;
    private GuiStatusBar statusBar;
    private GuiSmallMap smallMap;
    private GuiPlayerAngle playerAngle;
    private GuiHelpPanel helpPanel;
    private GuiScoreBoard scoreBoard;

    private GameManager() {
        init();
        componentList.add(bloodBar);
        componentList.add(statusBar);
        componentList.add(smallMap);
        componentList.add(playerAngle);
        componentList.add(helpPanel);
        componentList.add(scoreBoard);
    }

    public static GameManager getInstance() {
        if (gameManager == null) {
            gameManager = new GameManager();
        }
        return gameManager;
    }

    private void init() {
        bloodBar = new GuiBloodBar();
        statusBar = new GuiStatusBar();
        smallMap = new GuiSmallMap();
        playerAngle = new GuiPlayerAngle();
        helpPanel = new GuiHelpPanel();
        scoreBoard = new GuiScoreBoard();
    }

    public void render(Graphics g) {
        for (GuiComponent component : this.componentList) {
            component.render(g);
        }
    }

    public void update() {
        for (GuiComponent component : this.componentList) {
            component.update();
        }
    }

    public void openInfo(String type) {
        if (type == "help")
            helpPanel.setVisible();
        if (type == "tab")
            scoreBoard.setVisible();
    }

    public void closeInfo(String type) {
        if (type == "help")
            helpPanel.setInvisible();
        if (type == "tab")
            scoreBoard.setInvisible();
    }
    
}
