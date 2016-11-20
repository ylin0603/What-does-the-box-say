package gamelikegod.core.rain;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gamelikegod.core.graphics.Screen;
import gamelikegod.core.input.Keyboard;
import gamelikegod.core.rain.entity.mob.Player;
import gamelikegod.core.rain.level.Level;
import gamelikegod.core.rain.level.SpawnLevel;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;

	private JFrame frame;
	private Keyboard key;
	private Thread thread;
	private boolean running = false;
	private Level level;
	private Player player;
	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		key = new Keyboard();
		frame = new JFrame();
		screen = new Screen(width, height);
		level = Level.SPAWN;
		player = new Player(key);

		this.addKeyListener(key);
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

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns; // how many frame 1 = 60frame
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render(); // As much as you can
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
		key.update();
		player.update();

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		int xScroll = player.x - screen.getWidth() / 2;
		int yScroll = player.y - screen.getHeight() / 2;
		level.render(xScroll, yScroll, screen);
		player.render(screen);
		System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
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
