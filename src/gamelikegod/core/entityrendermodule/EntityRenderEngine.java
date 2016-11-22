package gamelikegod.core.entityrendermodule;

import gamelikegod.core.graphics.Sprite;

public class EntityRenderEngine {
	public void renderPlayer(int xp, int yp, Sprite sprite) {
		xp -= xOffset; //TODO: offsets should get from DOM
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				// TODO: 
				if (xa < 0) xa = 0;
				int color = sprite.pixels[x + y * sprite.SIZE];
				if (color != 0xffffffff) pixels[xa + ya * width] = color;
			}
		}

	}
}
