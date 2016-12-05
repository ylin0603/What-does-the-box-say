package renderer.engine;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import renderer.data.SceneManager;
import renderer.level.Level;
import renderer.level.tile.Tile;

public class SceneRenderEngine {
	private int[] pixels;
	private Level level;

	public SceneRenderEngine() {
		level = SceneManager.getInstance().getLevel();
	}

	public void renderScene(int[] pixels) {
		this.pixels = pixels;
		// real x,y position need to get from renderer.data.DynamicObjectManager
		int xScroll = DynamicObjectManager.getInstance().getVirtualCharacterXY().x - Game.WIDTH / 2;
		int yScroll = DynamicObjectManager.getInstance().getVirtualCharacterXY().y - Game.HEIGHT / 2;

		renderLevel(xScroll, yScroll);
	}

	public void renderTile(int xp, int yp, int xOffset, int yOffset, Tile tile) {
		xp -= xOffset; // TODO: offsets should get from renderer.data.DynamicObjectManager
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
		int x0 = xScroll >> 4; // 2^4 is 16 so >> 4 :) <3 
		int y0 = yScroll >> 4;
		int x1 = (xScroll + Game.WIDTH + Tile.SIZE) >> 4;
		int y1 = (yScroll + Game.HEIGHT + Tile.SIZE) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				// convert tile position to pixel position
				// ( because we want it to be rendered on screen)
				Tile tile = level.getTile(x, y);
				renderTile(x * Tile.SIZE, y * Tile.SIZE, xScroll, yScroll, tile);
			}
		}
	}

	public int[] getPixels() {
		return pixels;
	}

	// Parameter x, y is tile position
}
