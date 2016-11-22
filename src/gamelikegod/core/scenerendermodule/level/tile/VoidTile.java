package gamelikegod.core.scenerendermodule.level.tile;

import gamelikegod.core.graphics.Screen;
import gamelikegod.core.graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x, y, this);
	}
}
