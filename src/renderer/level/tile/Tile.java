package renderer.level.tile;

import renderer.graphics.Sprite;

public class Tile {
	public static final int SIZE = 16;
	
	
	public Sprite sprite;
	
	public static Tile VOID_TILE = new VoidTile(Sprite.VOID_SPRITE);
	public static Tile SPAWN_GRASS = new SpawnLevelGrassTile(Sprite.SPAWN_GRASS);
	public static Tile SPAWN_GRASS_2 = new SpawnLevelGrassTile(Sprite.SPAWN_GRASS_2);
	public static Tile SPAWN_HEDGE = new SpawnLevelHedgeTile(Sprite.SPAWN_HEDGE);
	public static Tile SPAWN_WATER = new SpawnLevelWaterTile(Sprite.SPAWN_WATER);
	public static Tile SPAWN_WALL = new SpawnLevelWallTile(Sprite.SPAWN_WALL);
	public static Tile SPAWN_FLOOR = new SpawnLevelFloorTile(Sprite.SPAWN_FLOOR);

	public final static int COLOR_SPAWN_GRASS = 0xff00ff00;
	public final static int COLOR_SPAWN_GRASS_2 = 0xffffff00;
	public final static int COLOR_SPAWN_HEDGE = 0;
	public final static int COLOR_SPAWN_FLOOR = 0;
	public final static int COLOR_SPAWN_WATER = 0xff0000FF;
	public final static int COLOR_SPAWN_WALL =  0xffff0000;
	
	public Tile(Sprite sprite){
		this.sprite = sprite;
		// default tile size is 16.
	}
	
	public boolean solid(){
		return false;
	}
	
	
}
