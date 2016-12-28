package Input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.game.BeforeGameManager;
import gui.gameInterface.GuiFrame;
import tcp.tcpClient.RealTcpClient;

public class ButtonClick implements ActionListener {
    
    private GuiFrame frame = BeforeGameManager.getInstance().getFrame();

    @Override
    public void actionPerformed(ActionEvent e) {
        // Refresh frame.
        frame.getContentPane().removeAll();
        
        switch (e.getActionCommand()) {
            case "join_game":
                BeforeGameManager.getInstance().setJoinGamePanel();
                break;
            case "exit_game":
                System.exit(0);
                break;
            case "start_game":
                RealTcpClient.getInstance().startGame();
                BeforeGameManager.getInstance().addRoomPanel();
                break;
            default:
                break;
        }
        frame.getContentPane().repaint();
        frame.setVisible(true);
    }
}
