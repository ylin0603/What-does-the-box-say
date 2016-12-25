package gui.task;

import java.util.TimerTask;

import gui.game.BeforeGameManager;
import tcp.tcpClient.RealTcpClient;

public class CountDownTask extends TimerTask {
    
    private int count = 5;

    @Override
    public void run() {
        if (count == 1) {
            RealTcpClient.getInstance().startGame();
        }
        BeforeGameManager.getInstance().getStartButton().setText(count+"!");
        count--;
        BeforeGameManager.getInstance().addRoomPanel();
    }

}
