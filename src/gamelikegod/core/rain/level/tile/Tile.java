package gamelikegod.core.rain.level.tile;

import gamelikegod.core.graphics.Screen;
import gamelikegod.core.graphics.Sprite;
import gamelikegod.core.rain.level.tile.spawnlevel.SpawnLevelFloorTile;
import gamelikegod.core.rain.level.tile.spawnlevel.SpawnLevelGrassTile;
import gamelikegod.core.rain.level.tile.spawnlevel.SpawnLevelHedgeTile;
import gamelikegod.core.rain.level.tile.spawnlevel.SpawnLevelWallTile;
import gamelikegod.core.rain.level.tile.spawnlevel.SpawnLevelWaterTile;

public class Tile {
	
	public int x,y;
	public Sprite sprite;
	
	public static Tile GRASS = new GrassTile(Sprite.GRASS);
	public static Tile STONE = new StoneTile(Sprite.STONE);
	public static Tile TREE = new TreeTile(Sprite.TREE);
	public static Tile voidTile = new VoidTile(Sprite.VOID_SPRITE);
	
	public static Tile SPAWN_GRASS = new SpawnLevelGrassTile(Sprite.SPWAN_GRASS);
	public static Tile SPAWN_GRASS_2 = new SpawnLevelGrassTile(Sprite.SPWAN_GRASS_2);
	public static Tile SPAWN_HEDGE = new SpawnLevelHedgeTile(Sprite.SPWAN_HEDGE);
	public static Tile SPAWN_WATER = new SpawnLevelWaterTile(Sprite.SPWAN_WATER);
	public static Tile SPAWN_WALL = new SpawnLevelWallTile(Sprite.SPWAN_WALL);
	public static Tile SPAWN_FLOOR = new SpawnLevelFloorTile(Sprite.SPWAN_FLOOR);

	public final static int COLOR_SPAWN_GRASS = 0xff00ff00;
	public final static int COLOR_SPAWN_GRASS_2 = 0xffffff00;

	public final static int COLOR_SPAWN_HEDGE = 0;
	public final static int COLOR_SPAWN_FLOOR = 0;
	public final static int COLOR_SPAWN_WATER = 0xff0000FF;
	public final static int COLOR_SPAWN_WALL =  0xffff0000;
	
	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen){
		
	}
	
	public boolean solid(){
		return false;
	}
	
}
