package renderer.data;

import renderer.level.Level;
import renderer.level.SpawnLevel;

public class SceneManager {
	private int[] pixels;

	public static final int MAP_SIZE = 256 * 256;
	public final static int MAP_SIZE_X = 640;
    public final static int MAP_SIZE_Y = 1200;

	private Level level = new SpawnLevel("/levels/spawn.png");
	public int[] tiles = new int[MAP_SIZE];

	private static SceneManager instance = null;

	private SceneManager() {
		System.out.println("SceneManager created");
	}

	public static SceneManager getInstance() {
		if (instance == null) {
			instance = new SceneManager();
		}
		return instance;
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		assert (level != null);
		return level;
	}

}
