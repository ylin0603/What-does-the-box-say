package gui.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import renderer.data.entity.Character;

public class GuiRankBoard extends GuiPanel {

    private Graphics g;
    private List<Character> characterList = null;
    private ArrayList<Integer> score, rank;
    private final String[] heading =
            {"Name", "Kill Count", "Dead Count", "Score", "Rank"};
    private int rankSize = 3;

    public GuiRankBoard() {
        score = new ArrayList<Integer>();
        rank = new ArrayList<Integer>();
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
        score.clear();
        rank.clear();
        for (int i = 0; i < characterList.size(); i++) {
            Character player = characterList.get(i);
            int rowY = this.infoY + (i + 1) * 30;
            g.drawString(player.getNickName(), this.infoX, rowY);
            g.drawString(player.getKillCount() + "", this.infoX + 150, rowY);
            g.drawString(player.getDeadCount() + "", this.infoX + 300, rowY);
            int playerScore = player.getKillCount() * 2 - player.getDeadCount();
            score.add(playerScore);
            rank.add(i);
            g.drawString(playerScore + "", this.infoX + 450, rowY);
        }
    }
    
    private void renderRank() {
        g.setColor(Color.YELLOW);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        if (rankSize > score.size()) rankSize = score.size();
        
        int place = 1, maxValue;
        for (int i=0; i < rankSize; i++) {
            // Find max score.
            int maxIndex = i;
            for (int j=i; j < score.size(); j++) {
                if (score.get(maxIndex) < score.get(j))
                    maxIndex = j;
            }
            maxValue = score.get(maxIndex);
            swapAndDraw(i, maxIndex, place);
            
            // Find score that is same as max.
            for (int j=maxIndex+1; j < score.size(); j++) {
                if (score.get(j) == maxValue) {
                    i++;
                    swapAndDraw(i, j, place);
                }
            }
            
            place++;
            if (place > 3) break;
        }
    }
    
    private void swapAndDraw(int a, int b, int place) {
        Collections.swap(score, a, b);
        Collections.swap(rank, a, b);
        int rowY = this.infoY + (rank.get(a) + 1) * 30;
        g.drawString(place + "", this.infoX + 600, rowY);
    }
}
