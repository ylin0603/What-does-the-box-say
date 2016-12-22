package gui.gameInterface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.game.BeforeGameManager;
import tcp.tcpClient.RealTcpClient;

@SuppressWarnings("serial")
public class RoomPanel extends JPanel {

    private ArrayList<String> nameList;
    
    public RoomPanel() {
        nameList = BeforeGameManager.getInstance().getNameList();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createGlue());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createGlue());
        panel.add(showNameList());
        panel.add(showRoomInfo());
        panel.add(Box.createGlue());
        this.add(panel);
        this.add(Box.createGlue());
    }
    
    private JPanel showNameList() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(10, 1));
        listPanel.setPreferredSize(new Dimension(150, 250));
        listPanel.add(addLabel("Player List :"));
        for (String name: nameList) {
            listPanel.add(addLabel(name));
        }
        return listPanel;
    }
    
    private JPanel showRoomInfo() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(10, 1));
        infoPanel.setPreferredSize(new Dimension(100, 250));
        infoPanel.add(addLabel("Room Owner : "+nameList.get(0)));
        int clientNo = RealTcpClient.getInstance().getClientNo();
        infoPanel.add(addLabel("You are : "+nameList.get(clientNo)));
        if (clientNo == 0) {
            for (int i=0; i<7; i++)
                infoPanel.add(Box.createGlue());
            infoPanel.add(new GuiButton("START!", "start_game"));
        }
        return infoPanel;
    }
    
    private JLabel addLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        return label;
    }
}
