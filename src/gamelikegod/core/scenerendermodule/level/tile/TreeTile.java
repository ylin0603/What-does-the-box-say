package gamelikegod.core.scenerendermodule.level.tile;

import gamelikegod.core.graphics.Screen;
import gamelikegod.core.graphics.Sprite;

public class TreeTile extends Tile {

	public TreeTile(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	public void render(int x,int y, Screen screen) {
		screen.renderTile(x, y, Tile.GRASS);
		screen.renderTile(x, y, this);
	}
	
	public boolean solid() {
		return false;
	}

}
