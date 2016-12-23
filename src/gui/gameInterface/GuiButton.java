package gui.gameInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;

import gui.game.BeforeGameManager;

@SuppressWarnings("serial")
public class GuiButton extends JButton {

    public GuiButton(String name, String command) {
        super(name);
        super.setContentAreaFilled(false);
        this.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
        this.setBackground(new Color(193,128,62));
        this.setPreferredSize(new Dimension(100, 80));
        this.setMargin(new Insets(20,5,20,5));
        this.addActionListener(
                BeforeGameManager.getInstance().getButtonClick());
        this.setActionCommand(command);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
