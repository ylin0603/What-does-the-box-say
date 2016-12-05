package renderer.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import renderer.level.tile.Tile;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	// Grass = 0xFF00
	// Tree = 0xFFFF00
	// Rock = ox7f7f00
	protected void generateLevel() {

		for (int i = 0; i < tiles.length; i++) {
		}
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			tiles = new int[width * height];
			image.getRGB(0, 0, width, height, tiles, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
