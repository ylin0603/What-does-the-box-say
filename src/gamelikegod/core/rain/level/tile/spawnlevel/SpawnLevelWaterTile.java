package gamelikegod.core.rain.level.tile.spawnlevel;

import gamelikegod.core.graphics.Screen;
import gamelikegod.core.graphics.Sprite;
import gamelikegod.core.scenerendermodule.level.tile.Tile;

public class SpawnLevelWaterTile extends Tile {

	public SpawnLevelWaterTile(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x, y, this);
	}

}
