package renderer.level;

import renderer.level.tile.Tile;

public class Level {

	protected int width;
	protected int height;
	protected int[] tiles;
	
	
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

	// Parameter x, y is tile position
	public Tile getTile(int x, int y) {
		int index = x + y * width;
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.VOID_TILE;
		if (tiles[index] == Tile.COLOR_SPAWN_FLOOR) return Tile.SPAWN_FLOOR;
		else if (tiles[index] == Tile.COLOR_SPAWN_GRASS) return Tile.SPAWN_GRASS;
		else if (tiles[index] == Tile.COLOR_SPAWN_GRASS_2) return Tile.SPAWN_GRASS_2;
		else if (tiles[index] == Tile.COLOR_SPAWN_WATER) return Tile.SPAWN_WATER;
		else if (tiles[index] == Tile.COLOR_SPAWN_WALL) return Tile.SPAWN_WALL;
		else if (tiles[index] == Tile.COLOR_SPAWN_HEDGE) return Tile.SPAWN_HEDGE;
		else{
			return Tile.VOID_TILE;
		}
	}

}
