package Input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.game.BeforeGameManager;
import gui.gameInterface.GuiFrame;

public class ButtonClick implements ActionListener {
    
    private GuiFrame frame = BeforeGameManager.getInstance().getFrame();

    @Override
    public void actionPerformed(ActionEvent e) {
        // Refresh frame.
        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();
        
        switch (e.getActionCommand()) {
            case "join_game":
                break;
        }
        
        frame.setVisible(true);
    }
}
