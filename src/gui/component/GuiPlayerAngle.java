package gui.component;

import java.awt.Color;

import renderer.data.entity.Character;

public class GuiPlayerAngle extends GuiLabel {

    public GuiPlayerAngle() {
        super("degree", 500, 250, Color.WHITE);
    }

    public GuiPlayerAngle(String text, int x, int y, Color color) {
        super(text, x, y, color);
    }

    public void update() {
        Character player = dom.getLocalCharacter();
        this.setText(player.getFaceAngle() + " degree");
    }
}
