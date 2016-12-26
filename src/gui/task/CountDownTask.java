package gui.task;

import java.util.TimerTask;

import gui.game.BeforeGameManager;

public class CountDownTask extends TimerTask {
    
    private int count = 5;

    @Override
    public void run() {
        if (count == 0) {
            BeforeGameManager.getInstance().startLoading();
        }
        BeforeGameManager.getInstance().getStartButton().setText(count+"!");
        count--;
    }

}
