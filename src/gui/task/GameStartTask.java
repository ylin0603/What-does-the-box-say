package gui.task;

import java.util.TimerTask;

import gui.game.BeforeGameManager;

public class GameStartTask extends TimerTask {

    @Override
    public void run() {
        BeforeGameManager.getInstance().setGameCanvas();
    }

}
