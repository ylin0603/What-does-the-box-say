package gui.component;

import java.awt.Color;
import java.awt.Graphics;

public class GuiHelpPanel extends GuiPanel {

    private final String[] keyInfo = {
            "BOX", 
            "W : Forward", 
            "S : Backward",
            "A : Rotate Left", 
            "D : Rotate Right", 
            "J : Change Weapon",
            "SPACE : Attack", 
            "TAB : Show Score Board"
    };

    public GuiHelpPanel() {

    }

    public void render(Graphics g) {
        if (this.visible) {
            g.setColor(this.backgroundColor);
            g.fillRoundRect(x, y, 400, 420, arcWidth, arcWidth);
            g.setColor(Color.WHITE);
            for (int i = 0; i < keyInfo.length; i++) {
                g.drawString(keyInfo[i], this.infoX, this.infoY + i * 30);
            }
        }
    }
}
