package pseudomain;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

import Input.FetchAction;
import Input.MoveAction;

import Input.RotateAction;
import renderer.engine.RenderEngine;
import tcp.tcpClient.RealTcpClient;
import udp.update.server.UDP_Server;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    public final static int WIDTH = 300;
    public final static int HEIGHT = WIDTH / 16 * 9;
    public static int scale = 3;

    private JFrame frame;
    private Thread thread;
    private boolean running = false;

    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

    private final static int TURNEAST = 0;
    private final static int TURNSOUTH = 1;
    private final static int TURNNORTH = 2;
    private final static int TURNWEST = 3;
    private final static int ROTATE_CLOCKWISE = 0;
    private final static int ROTATE_COUNTER_CLOCKWISE = 1;
    private final static String GET = "get";


    public Game() {
        Dimension size = new Dimension(WIDTH * scale, HEIGHT * scale);
        setPreferredSize(size);
        frame = new JFrame();
        initialKeyBinding();
    }

    private void initialKeyBinding() {
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), TURNNORTH);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), TURNSOUTH);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), TURNWEST);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), TURNEAST);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke("GET"), GET);

        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke("A"),ROTATE_CLOCKWISE);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke("D"),ROTATE_COUNTER_CLOCKWISE);

        frame.getRootPane().getActionMap().put(TURNNORTH, new MoveAction(TURNNORTH));
        frame.getRootPane().getActionMap().put(TURNSOUTH, new MoveAction(TURNSOUTH));
        frame.getRootPane().getActionMap().put(TURNWEST, new MoveAction(TURNWEST));
        frame.getRootPane().getActionMap().put(TURNEAST, new MoveAction(TURNEAST));
        frame.getRootPane().getActionMap().put(GET, new FetchAction());

        frame.getRootPane().getActionMap().put(ROTATE_CLOCKWISE,new RotateAction(ROTATE_CLOCKWISE));
        frame.getRootPane().getActionMap().put(ROTATE_COUNTER_CLOCKWISE, new RotateAction(ROTATE_COUNTER_CLOCKWISE));
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

    public static void main(String[] args) throws InterruptedException {
        // before game initial
        RealTcpClient realTcpClient = RealTcpClient.getInstance();
        boolean isConnect =
                realTcpClient.connectServer("127.0.0.1");
        UDP_Server.initUDPServer();
        realTcpClient.joinRoom("aaa");
        realTcpClient.loadGame();
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
