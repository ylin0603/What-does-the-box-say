package gamelikegod.core.rain.level;

import gamelikegod.core.graphics.Screen;
import gamelikegod.core.rain.level.tile.Tile;

public class Level {

	protected int width;
	protected int height;
	protected int[] tiles;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new int[width * height];
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
	}

	private void time() {

	}

	private void loadLevel(String path) {

	}

	protected void generateLevel() {

	}

	public void update() {

	}

	// Parameter xScroll, yScroll is pixel position
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);

		// divided by 16 to get tile position
		int x0 = xScroll / 16;
		int y0 = yScroll / 16;
		int x1 = (xScroll + screen.getWidth() + 16) / 16;
		int y1 = (yScroll + screen.getHeight() + 16) / 16;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				// convert tile position to pixel position
				// ( because we want it to be rendered on screen)
				getTile(x, y).render(x * 16, y * 16, screen);
			}
		}
	}

	// Parameter x, y is tile position
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == 0)
			return Tile.grass;
		else
			return Tile.voidTile;

	}

}
