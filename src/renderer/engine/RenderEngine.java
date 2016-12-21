package renderer.engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import gui.game.GameManager;
import pseudomain.Game;

public class RenderEngine {
	private static RenderEngine renderEngine;

	private BufferedImage image = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_RGB);
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

		sceneRE.renderScene(pixels);
		entityRE.render(pixels);

		System.arraycopy(sceneRE.getPixels(), 0, pixels, 0, pixels.length);

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		GameManager.getInstance().render(g);
		g.dispose();
		bs.show();
	}
}
