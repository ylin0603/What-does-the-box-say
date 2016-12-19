package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import gui.game.GameManager;

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
        if (e.getKeyChar() == 'w')
            keys[W] = true;
        if (e.getKeyChar() == 's')
            keys[S] = true;
        if (e.getKeyChar() == 'a')
            keys[A] = true;
        if (e.getKeyChar() == 'd')
            keys[D] = true;
        if (e.getKeyChar() == ' ')
            keys[ATTACK] = true;
        if (e.getKeyChar() == 'j') {
            keys[J] = true;
            // GameManager.getInsatance().setWeapon();
        }
        if (e.getKeyChar() == 'h')
            GameManager.getInsatance().openInfo(HELP);
        if (e.getKeyCode() == KeyEvent.VK_TAB)
            GameManager.getInsatance().openInfo(TAB);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'w')
            keys[W] = false;
        if (e.getKeyChar() == 's')
            keys[S] = false;
        if (e.getKeyChar() == 'a')
            keys[A] = false;
        if (e.getKeyChar() == 'd')
            keys[D] = false;
        if (e.getKeyChar() == ' ')
            keys[ATTACK] = false;
        if (e.getKeyChar() == 'j')
            keys[J] = false;
        if (e.getKeyChar() == 'h')
            GameManager.getInsatance().closeInfo(HELP);
        if (e.getKeyCode() == KeyEvent.VK_TAB)
            GameManager.getInsatance().closeInfo(TAB);
    }

    public boolean[] getKeys() {
        return keys;
    }

    public void reset() {
        Arrays.fill(keys, false);
    }

    public void resetOnceKey() {
        keys[ATTACK] = false;
        keys[J] = false;
    }
}
