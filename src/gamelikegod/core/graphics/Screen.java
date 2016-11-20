package gamelikegod.core.graphics;

import java.util.Random;

import gamelikegod.core.rain.entity.mob.Player;
import gamelikegod.core.rain.level.tile.Tile;

public class Screen {
	private int width;
	private int height;
	public int[] pixels;

	public final int MAP_SIZE = 256 * 256;
	public final int MAP_SQUARE_SIDE = 256;
	public final int MAP_MASK = MAP_SQUARE_SIDE - 1;

	public int xOffset, yOffset;

	public int[] tiles = new int[MAP_SIZE];

	private Random random = new Random();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new int[width * height];
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = random.nextInt(0xFFFFFF);
		}
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

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
