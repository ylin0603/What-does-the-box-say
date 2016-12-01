package pseudomain;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import renderer.engine.RenderEngine;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public final static int WIDTH = 300;
	public final static int HEIGHT = WIDTH / 16 * 9;
	public static int scale = 3;

	private JFrame frame;
	private Thread thread;
	private boolean running = false;

	public Game() {
		Dimension size = new Dimension(WIDTH * scale, HEIGHT * scale);
		setPreferredSize(size);
		frame = new JFrame();
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; // time(ns.) take per frame if
												// FPS = 60
		double delta = 0;
		int frames = 0;
		int updates = 0;

		requestFocus();

		// main game loop
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns; // how many frame 1 = 60frame
			lastTime = now;
			while (delta >= 1) {
				update(); // 60 times per second
				updates++;
				delta--;
			}
			RenderEngine.getInstance().render(this); // As much as you can
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + "ups, " + frames + "fps");
				frames = 0;
				updates = 0;
			}
		}
	}

	public void update() {

	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Rain");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}

}
