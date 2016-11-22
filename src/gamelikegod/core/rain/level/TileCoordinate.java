package gamelikegod.core.rain.level;

import gamelikegod.core.rain.level.tile.Tile;

public class TileCoordinate {
	private int TILE_SIZE = 16;
	private int x,y;
	public TileCoordinate(int x, int y) {
		this.x = x * TILE_SIZE;
		this.y = y * TILE_SIZE;
	}


	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

}
