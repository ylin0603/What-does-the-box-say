package gui.gameInterface;

import javax.swing.JButton;

import gui.game.BeforeGameManager;

@SuppressWarnings("serial")
public class GuiButton extends JButton {

    public GuiButton(String name, String command) {
        super(name);
        this.addActionListener(
                BeforeGameManager.getInstance().getButtonClick());
        this.setActionCommand(command);
    }
}
