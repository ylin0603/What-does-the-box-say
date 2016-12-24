package gui.gameInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import pseudomain.Game;

@SuppressWarnings("serial")
public class InitialPanel extends JPanel {

    private GuiButton joinGame, exitGame;
    private Graphics g;
    private int rectSize = 30;

    public InitialPanel() {
        Dimension size = new Dimension(Game.WIDTH * Game.scale,
                Game.HEIGHT * Game.scale);
        this.setPreferredSize(size);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createGlue());
        this.add(Box.createGlue());
        this.add(showButtons());
    }

    private JPanel showButtons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.setOpaque(false);
        joinGame = new GuiButton("JOIN", "join_game");
        exitGame = new GuiButton("EXIT", "exit_game");
        buttons.add(Box.createGlue());
        buttons.add(Box.createGlue());
        buttons.add(joinGame);
        buttons.add(Box.createGlue());
        buttons.add(exitGame);
        buttons.add(Box.createGlue());
        buttons.add(Box.createGlue());
        return buttons;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        // sky
        g.setColor(new Color(120,204,240));
        g.fillRect(0, 0, rectSize*30, rectSize*13);
        // grass
        g.setColor(new Color(84,198,0));
        g.fillRect(0, rectSize*13, rectSize*30, rectSize*4);
        // cloud
        g.setColor(Color.WHITE);
        drawCloud(rectSize*5, rectSize*8);
        drawCloud(rectSize*16, rectSize);
        drawCloud(rectSize*23, rectSize*5);
        // box title
        g.setColor(new Color(128,78,25));
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 210));
        g.drawString("BOX", rectSize*7, rectSize*9);
    }
    
    private void drawCloud(int x, int y) {
        g.fillRect(x, y, rectSize*2, rectSize*2);
        g.fillRect(x-rectSize, y+rectSize/2, rectSize, rectSize);
        g.fillRect(x+rectSize*2, y+rectSize/2, rectSize, rectSize);
    }
}
