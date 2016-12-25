package gui.gameInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoadingPanel extends JPanel {

    private int rectSize = 30;
    
    public LoadingPanel() {
        
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
        // Loading
        g.setColor(new Color(128,78,25));
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 120));
        g.drawString("Loading...", rectSize*6, rectSize*9);
    }
}
