package gui.task;

import java.util.TimerTask;

import gui.game.BeforeGameManager;
import tcp.tcpClient.RealTcpClient;

public class RoomPanelTask extends TimerTask {

    @Override
    public void run() {
        BeforeGameManager bgm = BeforeGameManager.getInstance();
        if (!RealTcpClient.getInstance().isGameload()) {
            bgm.updateNameList();
            bgm.addRoomPanel();
        } else {
            bgm.startLoading();
        }
    }

}
