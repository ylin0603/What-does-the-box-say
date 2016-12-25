package gui.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import renderer.data.entity.Character;

public class GuiRankBoard extends GuiPanel {

    private Graphics g;
    private List<Character> characterList = null;
    private ArrayList<Integer> score;
    private final String[] heading =
            {"Name", "Kill Count", "Dead Count", "Score", "Rank"};

    public GuiRankBoard() {
        score = new ArrayList<Integer>();
    }

    public void render(Graphics g) {
        if (this.visible) {
            characterList = dom.getCharacterList();
            this.g = g;

            g.setColor(new Color(0,0,0,220));
            g.fillRoundRect(x, y, 840, 420, arcWidth, arcWidth);

            g.setColor(Color.WHITE);
            for (int i = 0; i < heading.length; i++) {
                g.drawString(heading[i], this.infoX + i * 150, this.infoY);
            }
            renderScore();
            renderRank();
        }
    }

    private void renderScore() {
        for (int i = 0; i < characterList.size(); i++) {
            Character player = characterList.get(i);
            int rowY = this.infoY + (i + 1) * 30;
            g.drawString(player.getNickName(), this.infoX, rowY);
            g.drawString(player.getKillCount() + "", this.infoX + 150, rowY);
            g.drawString(player.getDeadCount() + "", this.infoX + 300, rowY);
            int playerScore = player.getKillCount() * 2 - player.getDeadCount();
            score.add(playerScore);
            g.drawString(playerScore + "", this.infoX + 450, rowY);
        }
    }
    
    private void renderRank() {
        int n = 3;
        if (n > score.size()) n = score.size();
        int[] rank = new int[n];
        for (int i = 0; i < n; n--) { // the max of n is 3
            int max = i;
            for (int j=i; j < score.size(); j++) {
                if (score.get(i) < score.get(j))
                    max = j;
            }
            score.remove(max);
            rank[i] = max;
        }
        g.setColor(Color.YELLOW);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        for (int i=0; i<n; i++) {
            int rowY = this.infoY + (rank[i] + 1) * 30;
            g.drawString(i+1 + "", this.infoX + 600, rowY);
        }
    }
}
