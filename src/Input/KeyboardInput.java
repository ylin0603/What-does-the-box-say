package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gui.game.GameManager;
import tcp.tcpClient.RealTcpClient;

/**
 * Created by Tsunglin on 2016/12/15.
 */
public class KeyboardInput implements KeyListener {

    private boolean[] keys = new boolean[6];
    private static int W = 0;
    private static int S = 1;
    private static int A = 2;
    private static int D = 3;
    private static int ATTACK = 4;
    private static int J = 5;
    private static String HELP = "help";
    private static String TAB = "tab";

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
            case 'W':
                if (!keys[W]) {
                    keys[W] = true;
                    send();
                }
                break;
            case 's':
            case 'S':
                if (!keys[S]) {
                    keys[S] = true;
                    send();
                }
                break;
            case 'a':
            case 'A':
                if (!keys[A]) {
                    keys[A] = true;
                    send();
                }
                break;
            case 'd':
            case 'D':
                if (!keys[D]) {
                    keys[D] = true;
                    send();
                }
                break;
            case ' ':
                if (!keys[ATTACK]) {
                    keys[ATTACK] = true;
                    send();
                }
                break;
            case 'j':
            case 'J':
                if (!keys[J]) {
                    keys[J] = true;
                    send();
                }
                break;
            case 'h':
            case 'H':
                GameManager.getInstance().openInfo(HELP);
                break;
            case KeyEvent.VK_TAB:
                GameManager.getInstance().openInfo(TAB);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
            case 'W':
                if (keys[W]) {
                    keys[W] = false;
                    send();
                }
                break;
            case 's':
            case 'S':
                if (keys[S]) {
                    keys[S] = false;
                    send();
                }
                break;
            case 'a':
            case 'A':
                if (keys[A]) {
                    keys[A] = false;
                    send();
                }
                break;
            case 'd':
            case 'D':
                if (keys[D]) {
                    keys[D] = false;
                    send();
                }
                break;
            case ' ':
                if (keys[ATTACK]) {
                    keys[ATTACK] = false;
                    send();
                }
                break;
            case 'j':
            case 'J':
                if (keys[J]) {
                    keys[J] = false;
                    send();
                }
                break;
            case 'h':
            case 'H':
                GameManager.getInstance().closeInfo(HELP);
                break;
            case KeyEvent.VK_TAB:
                GameManager.getInstance().closeInfo(TAB);
                break;
        }
    }

    private boolean[] getKeys() {
        return keys;
    }

    private void send() {
        RealTcpClient realTcpClient = RealTcpClient.getInstance();
        try {
            realTcpClient.inputMoves(getKeys());
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.out.println("TCP connetction error");
        }
    }
}
