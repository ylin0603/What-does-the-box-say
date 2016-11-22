package gamelikegod.data.scenedatamodule;

import gamelikegod.core.graphics.Sprite;
import gamelikegod.core.scenerendermodule.level.tile.Tile;

public class SceneManager {
	private int width;
	private int height;
	private int[] pixels;

	public final int MAP_SIZE = 256 * 256;
	public final int MAP_SQUARE_SIDE = 256;

	public int xOffset, yOffset;

	public int[] tiles = new int[MAP_SIZE];

	public SceneManager(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int color = tile.sprite.pixels[x + y * tile.sprite.SIZE];
				if (color != 0xffffffff) pixels[xa + ya * width] = color;
			}
		}

	}

	public void renderPlayer(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int color = sprite.pixels[x + y * sprite.SIZE];
				if (color != 0xffffffff) pixels[xa + ya * width] = color;
			}
		}

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}
