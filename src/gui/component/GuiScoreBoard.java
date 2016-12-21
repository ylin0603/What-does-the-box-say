package gui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import renderer.data.entity.Character;

public class GuiScoreBoard extends GuiPanel {

    private Graphics g;

    private List<Character> characterList = null;

    private final String[] heading = {"Name", "Kill Count", "Dead Count"};

    public GuiScoreBoard() {

    }

    public void render(Graphics g) {
        if (this.visible) {
            characterList = dom.getCharacterList();
            this.g = g;

            g.setColor(this.backgroundColor);
            g.fillRoundRect(x, y, 720, 420, arcWidth, arcWidth);

            g.setColor(Color.WHITE);
            for (int i = 0; i < heading.length; i++) {
                g.drawString(heading[i], this.infoX + i * 200, this.infoY);
            }
            renderScore();
        }
    }

    private void renderScore() {
        for (int i = 0; i < characterList.size(); i++) {
            Character player = characterList.get(i);
            int rowY = this.infoY + (i + 1) * 30;
            g.drawString(player.getNickName(), this.infoX, rowY);
            g.drawString(player.getKillCount() + "", this.infoX + 200, rowY);
            g.drawString(player.getDeadCount() + "", this.infoX + 400, rowY);
        }
    }
}
