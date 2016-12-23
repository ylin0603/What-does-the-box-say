package gui.gameInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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
    private int rectSize = 30;
    
    public RoomPanel() {
        nameList = BeforeGameManager.getInstance().getNameList();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createGlue());
        JPanel panel = new JPanel();
        panel.setOpaque(false);
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
        listPanel.setOpaque(false);
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
        infoPanel.setOpaque(false);
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
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // sky
        g.setColor(new Color(120,204,240));
        g.fillRect(0, 0, rectSize*30, rectSize*13);
        // grass
        g.setColor(new Color(84,198,0));
        g.fillRect(0, rectSize*13, rectSize*30, rectSize*4);
        // pane
        g.setColor(new Color(255,255,255,150));
        g.fillRoundRect(rectSize*4, rectSize*2, rectSize*22, rectSize*12, 10, 10);
    }
}
