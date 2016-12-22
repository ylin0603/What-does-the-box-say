package gui.gameInterface;

import java.util.TimerTask;

import gui.game.BeforeGameManager;
import tcp.tcpClient.RealTcpClient;

public class RoomPanelTask extends TimerTask {

    @Override
    public void run() {
        BeforeGameManager bgm = BeforeGameManager.getInstance();
        bgm.updateNameList();
        if (!bgm.isRoomOwner()) {
            if (RealTcpClient.getInstance().loadGame())
                bgm.setGameCanvas();
        }
        bgm.addRoomPanel();
    }

}
