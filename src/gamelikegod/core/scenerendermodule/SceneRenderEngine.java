package gamelikegod.core.scenerendermodule;

import gamelikegod.core.rain.Game;
import gamelikegod.core.scenerendermodule.level.tile.Tile;
import gamelikegod.data.scenedatamodule.SceneManager;

public class SceneRenderEngine {
	private int[] pixels;

	public void SceneRenderEngine() {
	}

	public void renderScene(int[] pixels) {
		this.pixels = pixels;
		SceneManager.getInstance().getLevel().render(xScroll, yScroll, screen);
	}
	
	public void renderTile(Tile tile) {
		int xp -= xOffset; // TODO: offsets should get from DOM
		int yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= Game.WIDTH || ya < 0 || ya >= Game.HEIGHT) break;
				if (xa < 0) xa = 0;
				int color = tile.sprite.pixels[x + y * tile.sprite.SIZE];
				if (color != 0xffffffff) pixels[xa + ya * Game.WIDTH] = color;
			}
		}

	}
}
