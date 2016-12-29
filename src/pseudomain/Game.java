package pseudomain;

import java.awt.Canvas;
import java.awt.Dimension;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

import Input.KeyboardInput;
import audio.AudioManager;
import gui.game.GameManager;
import renderer.data.DynamicObjectManager;
import renderer.data.entity.Entity;
import renderer.engine.EntityRenderEngine;
import renderer.engine.RenderEngine;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    public final static int WIDTH = 300;
    public final static int HEIGHT = WIDTH / 16 * 9;
    public static int scale = 3;

    private JFrame frame;
    private Thread thread;
    private boolean running = false;

    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

    private KeyboardInput keyInput;

    public Game() {
        Dimension size = new Dimension(WIDTH * scale, HEIGHT * scale);
        keyInput = new KeyboardInput();
        this.addKeyListener(keyInput);
        setPreferredSize(size);
        //frame = new JFrame();
        this.setFocusTraversalKeysEnabled(false);
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
        AudioManager.getInstance().playBackGroundMusic();
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
        stop();
    }

    public void update() {


        List<Entity> allEntity = DynamicObjectManager.getInstance().getAllDynamicObjects();
        for(Entity e : allEntity){
            e.update();
        }
        GameManager.getInstance().update();
        
    }

    /*public static void main(String[] args) throws InterruptedException {
        // before game initial
        RealTcpClient realTcpClient = RealTcpClient.getInstance();
        boolean isConnect =
                realTcpClient.connectServer("127.0.0.1");
        UDP_Server.initUDPServer();
        realTcpClient.joinRoom("aaa");
        realTcpClient.loadGame();
        DynamicObjectManager dom = DynamicObjectManager.getInstance();
        while (dom.getCharacterList().size() == 0) ;
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setTitle("Rain");
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }*/

}
