package gui.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;

import Input.ButtonClick;
import gui.gameInterface.GuiButton;
import gui.gameInterface.GuiFrame;
import gui.gameInterface.InitialPanel;
import gui.gameInterface.JoinGamePanel;
import gui.gameInterface.LoadingPanel;
import gui.task.CountDownTask;
import gui.task.GameStartTask;
import gui.task.RoomPanelTask;
import gui.gameInterface.RoomPanel;
import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import tcp.tcpClient.RealTcpClient;

public class BeforeGameManager {

    private final static int ITEM_NUM = 32;
    private static BeforeGameManager beforeGameManager;
    private GuiFrame frame;
    private ButtonClick buttonClick;
    private InitialPanel initialPanel;
    private Timer timer, countTimer;
    private GuiButton startBtn;
    private ArrayList<String> nameList;
    
    private BeforeGameManager() {
        frame = new GuiFrame();
    }
    
    public static BeforeGameManager getInstance() {
        if (beforeGameManager == null) {
            beforeGameManager = new BeforeGameManager();
        }
        return beforeGameManager;
    }
    
    public GuiFrame getFrame() {
        return frame;
    }
    
    public ButtonClick getButtonClick() {
        return buttonClick;
    }
    
    public GuiButton getStartButton() {
        return startBtn;
    }
    
    // Only called by Main.
    public void setInitialPanel() {
        buttonClick = new ButtonClick();
        startBtn = new GuiButton("START!", "start_game");
        initialPanel = new InitialPanel();
        frame.add(initialPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    // Called when user do not input nickname.
    public void addInitialPanel() {
        frame.add(initialPanel);
        frame.setVisible(true);
    }
    
    // Only called when user click JOIN button.
    public void setJoinGamePanel() {
        JoinGamePanel joinGamePanel = new JoinGamePanel();
        frame.add(joinGamePanel);
    }
    
    // If connect success, call this to refresh every 1 second.
    public void addRoomPanel() {
        frame.getContentPane().removeAll();
        frame.add(new RoomPanel());
        frame.getContentPane().repaint();
        frame.setVisible(true);
    }
    
    // Called by JoinRoomPanel.
    public void startTimer() {
        timer = new Timer();
        timer.schedule(new RoomPanelTask(), 0, 1000);
    }
    public void stopTimer() {
        timer.cancel();
        timer.purge();
    }
    
    // Called by ButtonClick (Start Button).
    public void startCountDown() {
        countTimer = new Timer();
        countTimer.schedule(new CountDownTask(), 0, 1000);
        startBtn.setEnabled(false);
        startBtn.setBackground(Color.BLACK);
    }
    public void stopCountDown() {
        countTimer.cancel();
        countTimer.purge();
    }
    
    // Called by RoomPanelTask. When game is loaded, start loading.
    public void startLoading() {
        stopTimer();
        if (countTimer != null) stopCountDown();
        frame.add(new LoadingPanel());
        frame.getContentPane().repaint();
        frame.setVisible(true);
        Timer loadingTimer = new Timer();
        loadingTimer.schedule(new GameStartTask(), 5000);
        DynamicObjectManager dom = DynamicObjectManager.getInstance();
        RealTcpClient.getInstance().recvedGameLoad();
        int clientTotalNum = RealTcpClient.getInstance().getTotalPlayerNumer();
        while (dom.getCharacterList().size() != clientTotalNum &&
                dom.getItemList().size() != ITEM_NUM );
    }
    
    // Called by GameStartTask when game start!
    public void setGameCanvas() {
        frame.getContentPane().removeAll();
        Game game = new Game();
        frame.add(game);
        frame.getContentPane().repaint();
        frame.setVisible(true);
        game.start();
    }
    
    public void updateNameList() {
        nameList = RealTcpClient.getInstance().getNameList();
    }
    public ArrayList<String> getNameList() {
        return nameList;
    }
}
