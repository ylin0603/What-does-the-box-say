package gui.game;

import java.util.ArrayList;
import java.util.Timer;

import Input.ButtonClick;
import gui.gameInterface.GuiFrame;
import gui.gameInterface.InitialPanel;
import gui.gameInterface.JoinGamePanel;
import gui.gameInterface.RoomPanelTask;
import gui.gameInterface.RoomPanel;
import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import tcp.tcpClient.RealTcpClient;

public class BeforeGameManager {

    private final static int ITEM_NUM = 32;
    private static BeforeGameManager beforeGameManager;
    private RealTcpClient realTcpClient;
    private GuiFrame frame;
    private ButtonClick buttonClick;
    private InitialPanel initialPanel;
    private Timer timer;
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
    
    public RealTcpClient getTcpClient() {
        return realTcpClient;
    }
    
    // Only called by Main.
    public void setInitialPanel() {
        realTcpClient = RealTcpClient.getInstance();
        buttonClick = new ButtonClick();
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
    
    public void startTimer() {
        timer = new Timer();
        timer.schedule(new RoomPanelTask(), 0, 1000);
    }
    
    public void stopTimer() {
        timer.cancel();
        timer.purge();
    }
    
    // Only called when game start!
    public void setGameCanvas() {
        stopTimer();
        frame.getContentPane().removeAll();
        
        DynamicObjectManager dom = DynamicObjectManager.getInstance();
        RealTcpClient.getInstance().recvedGameLoad();
        int clientTotalNum = RealTcpClient.getInstance().getTotalPlayerNumer();
        //while (dom.getCharacterList().size() != clientTotalNum &&
        //    dom.getItemList().size() != 32 );
        System.out.println("game start");
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
