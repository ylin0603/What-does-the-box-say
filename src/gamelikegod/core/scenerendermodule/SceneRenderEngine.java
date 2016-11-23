package gamelikegod.core.scenerendermodule;

import gamelikegod.core.graphics.Screen;
import gamelikegod.core.rain.Game;
import gamelikegod.core.rain.level.Level;
import gamelikegod.core.scenerendermodule.level.tile.Tile;
import gamelikegod.data.dynamicobjectmodule.EntityManager;
import gamelikegod.data.scenedatamodule.SceneManager;

public class SceneRenderEngine {
	private int[] pixels;
	private Level level;

	public SceneRenderEngine() {
		level = SceneManager.getInstance().getLevel();
	}

	public void renderScene(int[] pixels) {
		this.pixels = pixels;
		int xScroll = EntityManager.getInstance().getVirtualCharacterXY()[0];
		int yScroll = EntityManager.getInstance().getVirtualCharacterXY()[1];

		renderLevel(xScroll, yScroll);
	}

	public void renderTile(int xp, int yp, int xOffset, int yOffset, Tile tile) {
		xp -= xOffset; // TODO: offsets should get from DOM
		yp -= yOffset;
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

	public void renderLevel(int xScroll, int yScroll) {

		// divided by 16 to get tile position
		int x0 = xScroll >> 4;
		int y0 = yScroll >> 4;
		int x1 = (xScroll + Game.WIDTH + 16) >> 4;
		int y1 = (yScroll + Game.HEIGHT + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				// convert tile position to pixel position
				// ( because we want it to be rendered on screen)
				Tile tile = level.getTile(x, y);
				renderTile(x * 16, y * 16, xScroll, yScroll, tile);
			}
		}
	}

	public int[] getPixels() {
		return pixels;
	}

	// Parameter x, y is tile position
}
