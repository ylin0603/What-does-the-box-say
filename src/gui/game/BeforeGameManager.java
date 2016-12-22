package gui.game;

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

    private static BeforeGameManager beforeGameManager;
    private RealTcpClient realTcpClient;
    private GuiFrame frame;
    private ButtonClick buttonClick;
    private InitialPanel initialPanel;
    private Timer timer;
    
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
    
    public boolean isRoomOwner() {
        return realTcpClient.getClientNo() == 0;
    }
    
    // Only called when game start!
    public void setGameCanvas() {
        timer.cancel();
        frame.getContentPane().removeAll();
        Game game = new Game();
        frame.add(game);
        frame.getContentPane().repaint();
        frame.setVisible(true);
        DynamicObjectManager dom = DynamicObjectManager.getInstance();
        while (dom.getCharacterList().size() == 0) ;
        game.start();
    }
}
