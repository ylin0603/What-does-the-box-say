package gui.gameInterface;

import java.util.TimerTask;

import gui.game.BeforeGameManager;

public class RoomPanelTask extends TimerTask {

    @Override
    public void run() {
        BeforeGameManager.getInstance().addRoomPanel();
    }

}
