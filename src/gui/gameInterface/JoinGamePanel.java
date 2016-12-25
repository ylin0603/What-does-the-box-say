package gui.gameInterface;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.game.BeforeGameManager;
import tcp.tcpClient.RealTcpClient;
import udp.update.server.UDP_Server;

@SuppressWarnings("serial")
public class JoinGamePanel extends JPanel {

    private JFrame frame = BeforeGameManager.getInstance().getFrame();
    private RealTcpClient realTcpClient;
    private String name, serverIp;

    public JoinGamePanel() {
        name = JOptionPane.showInputDialog(frame, "Please input your nickname :",
                "Join Room", JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.isEmpty())
            BeforeGameManager.getInstance().addInitialPanel();
        else
            askServerIp();
    }
    
    private void askServerIp() {
        serverIp = (String) JOptionPane.showInputDialog(frame,
                "Please input server ip:", "Server IP",
                JOptionPane.QUESTION_MESSAGE, null, null, "127.0.0.1");
        if (serverIp == null || serverIp.isEmpty())
            BeforeGameManager.getInstance().addInitialPanel();
        else
            connectServer();
    }

    private void connectServer() {
        realTcpClient = RealTcpClient.getInstance();
        boolean isConnect = realTcpClient.connectServer(serverIp);
        if (!isConnect) {
            JOptionPane.showMessageDialog(frame, "Connect fail!!!",
                    "Connect Fail", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        UDP_Server.initUDPServer();
        realTcpClient.joinRoom(name);
        BeforeGameManager.getInstance().startTimer();
    }

}
