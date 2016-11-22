package gamelikegod.core.rain.level;

import gamelikegod.core.graphics.Screen;
import gamelikegod.core.scenerendermodule.level.tile.Tile;

public class Level {

	protected int width;
	protected int height;
	protected int[] tiles;
	
	public static Level SPAWN = new SpawnLevel("/levels/spawn.png");
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	private void time() {

	}

	protected void loadLevel(String path) {

	}

	protected void generateLevel() {

	}

	public void update() {

	}

	// Parameter xScroll, yScroll is pixel position
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);

		// divided by 16 to get tile position
		int x0 = xScroll >> 4;
		int y0 = yScroll >> 4;
		int x1 = (xScroll + screen.getWidth() + 16) >> 4;
		int y1 = (yScroll + screen.getHeight() + 16) >> 4;

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
		int index = x + y * width;
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if (tiles[index] == Tile.COLOR_SPAWN_FLOOR) return Tile.SPAWN_FLOOR;
		else if (tiles[index] == Tile.COLOR_SPAWN_GRASS) return Tile.SPAWN_GRASS;
		else if (tiles[index] == Tile.COLOR_SPAWN_GRASS_2) return Tile.SPAWN_GRASS_2;
		else if (tiles[index] == Tile.COLOR_SPAWN_WATER) return Tile.SPAWN_WATER;
		else if (tiles[index] == Tile.COLOR_SPAWN_WALL) return Tile.SPAWN_WALL;
		else if (tiles[index] == Tile.COLOR_SPAWN_HEDGE) return Tile.SPAWN_HEDGE;
		else{
			return Tile.voidTile;
		}
	}

}
