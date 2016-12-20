package gui.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import pseudomain.Game;
import renderer.data.entity.Character;

public class GuiPlayerAngle extends GuiComponent {

    private final static int DIAMETER = 60;
    private GuiLabel showAngle;
    private int angle = 0;
    
    public GuiPlayerAngle() {
        showAngle = new GuiLabel("degree", 500, 250, Color.WHITE);
    }
    
    public void render(Graphics g) {
        if (dom.getLocalCharacter().isDead()) return;
        showAngle.render(g);
        g.setColor(Color.RED);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(7));
        g.drawArc(Game.WIDTH*Game.scale/2-6, Game.HEIGHT*Game.scale/2-6, 
                DIAMETER, DIAMETER, angle+90, 1);
    }

    public void update() {
        Character player = dom.getLocalCharacter();
        showAngle.setText(player.getFaceAngle() + " degree");
        angle = -(int)player.getFaceAngle();
    }
}
