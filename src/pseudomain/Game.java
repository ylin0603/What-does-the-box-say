package pseudomain;

import java.awt.*;
<<<<<<< HEAD
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import Input.FetchAction;
import Input.InfoAction;
=======

import javax.swing.*;

import Input.AttackAction;
import Input.RotateAction;
>>>>>>> origin/renderer
import Input.MoveAction;
import Input.StopAction;

<<<<<<< HEAD
import Input.RotateAction;
import Input.WeaponAction;
import gui.game.GameManager;
=======
>>>>>>> origin/renderer
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

    private final static int FORWARD = 0;
    private final static int BACKWARD = 1;
    private final static int ROTATE_CLOCKWISE = 2;
    private final static int ROTATE_COUNTER_CLOCKWISE = 3;
    private final static int ATTACK = 4;
    private final static int RELEASE = -1;
    private final static String GET = "get";
    private final static String HELP = "help";
    private final static String TAB = "tab";
    private final static String WEAPON = "weapon";

    public Game() {
        Dimension size = new Dimension(WIDTH * scale, HEIGHT * scale);
        setPreferredSize(size);
        frame = new JFrame();
        initialKeyBinding();
    }

    private void initialKeyBinding() {
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke('w'), FORWARD);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke('w',true), RELEASE);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke('s'), BACKWARD);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke('s',true), RELEASE);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke('a'),ROTATE_CLOCKWISE);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke('a',true),RELEASE);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke('d'),ROTATE_COUNTER_CLOCKWISE);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke('d',true),RELEASE);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke(' '),ATTACK);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke("H"), HELP);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke("TAB"), TAB);
        frame.getRootPane().getInputMap(IFW).put(KeyStroke.getKeyStroke("J"), WEAPON);


        frame.getRootPane().getActionMap().put(FORWARD, new MoveAction(FORWARD));
        frame.getRootPane().getActionMap().put(BACKWARD, new MoveAction(BACKWARD));
        frame.getRootPane().getActionMap().put(ROTATE_CLOCKWISE, new RotateAction(ROTATE_CLOCKWISE));
        frame.getRootPane().getActionMap().put(ROTATE_COUNTER_CLOCKWISE, new RotateAction(ROTATE_COUNTER_CLOCKWISE));
        frame.getRootPane().getActionMap().put(ATTACK,new AttackAction(ATTACK));
        frame.getRootPane().getActionMap().put(RELEASE, new StopAction(RELEASE));
        
        // Remove the original key event on TAB.
        Set<KeyStroke> forwardKeys = new HashSet<KeyStroke>(1);
        forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_MASK));
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
        
        frame.getRootPane().getActionMap().put(HELP, new InfoAction(HELP));
        frame.getRootPane().getActionMap().put(TAB, new InfoAction(TAB));
        frame.getRootPane().getActionMap().put(WEAPON, new WeaponAction());
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
        stop();
    }

    public void update() {
    	GameManager.getInsatance().update();
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
