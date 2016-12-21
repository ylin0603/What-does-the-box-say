package gui.gameInterface;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import pseudomain.Game;

@SuppressWarnings("serial")
public class InitialPanel extends JPanel {

    private GuiButton joinGame, exitGame;

    public InitialPanel() {
        Dimension size = new Dimension(Game.WIDTH * Game.scale,
                Game.HEIGHT * Game.scale);
        this.setPreferredSize(size);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createGlue());
        this.add(showButtons());
        this.add(Box.createGlue());
    }

    private JPanel showButtons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        joinGame = new GuiButton("JOIN", "join_game");
        exitGame = new GuiButton("EXIT", "exit_game");
        buttons.add(Box.createGlue());
        buttons.add(joinGame);
        buttons.add(Box.createGlue());
        buttons.add(exitGame);
        exitGame.setVisible(true);
        buttons.add(Box.createGlue());
        return buttons;
    }
}
