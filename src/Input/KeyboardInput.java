package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Tsunglin on 2016/12/15.
 */
public class KeyboardInput implements KeyListener{

    private boolean[] keys = new boolean[4];
    private static int W = 0;
    private static int S = 1;
    private static int A = 2;
    private static int D = 3;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar()=='w')
            keys[W] = true;
        if(e.getKeyChar() == 's')
            keys[S] = true;
        if(e.getKeyChar() == 'a')
            keys[A] = true;
        if(e.getKeyChar() == 'd')
            keys[D] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar()=='w')
            keys[W] = false;
        if(e.getKeyChar() == 's')
            keys[S] = false;
        if(e.getKeyChar() == 'a')
            keys[A] = false;
        if(e.getKeyChar() == 'd')
            keys[D] = false;
    }

    public boolean[] getKeys(){
        return keys;
    }
}
