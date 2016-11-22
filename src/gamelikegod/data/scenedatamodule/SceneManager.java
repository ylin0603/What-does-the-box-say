package gamelikegod.data.scenedatamodule;

import gamelikegod.core.rain.level.Level;
import gamelikegod.core.rain.level.SpawnLevel;

public class SceneManager {
	private int[] pixels;

	public static final int MAP_SIZE = 256 * 256;
	public static final int MAP_SQUARE_SIDE = 256;

	private Level level;
	public int[] tiles = new int[MAP_SIZE];
	
	private static SceneManager instance = null;

	private SceneManager() {
	}

	public static SceneManager getInstance() {
		if (instance == null) {
			instance = new SceneManager();
		}
		return instance;
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

}
