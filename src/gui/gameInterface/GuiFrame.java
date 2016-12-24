package gui.gameInterface;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GuiFrame extends JFrame {

    public GuiFrame() {
        super("What Does the Box Say?");
        initUI();
    }

    // Main frame's setting
    private void initUI() {
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //ImageIcon icon = new ImageIcon("res/box.png");
        //this.setIconImage(icon.getImage());
    }
}