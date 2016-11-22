package gamelikegod.core.scenerendermodule;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import gamelikegod.core.entityrendermodule.EntityRenderEngine;
import gamelikegod.core.graphics.Sprite;
import gamelikegod.core.scenerendermodule.level.tile.Tile;

public class RenderEngine {
	private static RenderEngine renderEngine;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	private EntityRenderEngine entityRE;
	private SceneRenderEngine sceneRE;
	
	private RenderEngine() {
		entityRE = new EntityRenderEngine();
		sceneRE = new SceneRenderEngine();
	}

	public static RenderEngine getInstance() {
		if (renderEngine == null) {
			renderEngine = new RenderEngine();
		}
		return renderEngine;
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void render(Canvas canvas) {
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		clear();

		int xScroll = player.x - screen.getWidth() / 2;
		int yScroll = player.y - screen.getHeight() / 2;

		sceneRE.render(pixels);
		entityRE.render(pixels);

		System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		g.dispose();
		bs.show();
	}
}
