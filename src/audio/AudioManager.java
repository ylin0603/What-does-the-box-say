package audio;


import com.sun.java.browser.plugin2.DOM;
import com.sun.jmx.mbeanserver.DynamicMBean2;
import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector;
import renderer.data.DynamicObjectManager;
import renderer.data.entity.Entity;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * Created by TsunglinYang on 2016/12/27.
 */
public class AudioManager {

    private static AudioManager instance;

    private Clip backGroundClip;
    private Clip rangeAttackCLip;
    private Clip meleeAttackClip;
    private Clip attackedClip;
    private Clip explosionClip;
    private Clip dieClip;

    private AudioManager() {
        try {
            backGroundClip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    AudioManager.class.getResource("backGround.wav"));
            backGroundClip.open(inputStream);
            backGroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            FloatControl gainControl =
                    (FloatControl) backGroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-7.0f);


            meleeAttackClip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(
                    AudioManager.class.getResource("melee.wav"));
            meleeAttackClip.open(inputStream);

            rangeAttackCLip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(
                    AudioManager.class.getResource("range.wav"));
            rangeAttackCLip.open(inputStream);


            explosionClip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(
                    AudioManager.class.getResource("explosion.wav"));
            explosionClip.open(inputStream);

            attackedClip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(
                    AudioManager.class.getResource("pain.wav"));
            attackedClip.open(inputStream);

            dieClip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(
                    AudioManager.class.getResource("die.wav"));
            dieClip.open(inputStream);

        } catch (LineUnavailableException e) {
            System.err.println("LineUnavailableException");
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("UnsupportedAudioFileException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
        }
    }

    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    public void playBackGroundMusic() {
        backGroundClip.start();
    }

    public void playSoundEffect(String name, Entity entity) {
        resetAllSoundEffect();
        setVolume(entity);
        switch (name) {
            case "attacked":
                attackedClip.start();
                break;
            case "meleeAttack":
                meleeAttackClip.start();
                break;
            case "rangeAttack":
                rangeAttackCLip.start();
                System.out.println("rangeAttack Sound Effect");
                break;
            case "explosion":
                explosionClip.start();
                System.out.println("explosion sound Effect");
                break;
            case "die":
                dieClip.start();
                break;
        }
    }

    private void resetAllSoundEffect() {
        attackedClip.setMicrosecondPosition(0);
        meleeAttackClip.setMicrosecondPosition(0);
        rangeAttackCLip.setMicrosecondPosition(0);
        explosionClip.setMicrosecondPosition(0);
        dieClip.setMicrosecondPosition(0);

    }

    private static final int IN_VIEW_DISTANCE = 170;
    private static final float TO_SOUND_CONSTANT = 0.01f;
    private static final float DIECLIP_MULTIPLIER = 1.5f;
    private static final float MINIMUM_VOLUME = -80.0f;

    public void setVolume(Entity entity) {
        double x1 = entity.x * 0.1;
        double y1 = entity.y * 0.1;
        double x0 = DynamicObjectManager.getInstance().getVirtualCharacterXY().x * 0.1;
        double y0 = DynamicObjectManager.getInstance().getVirtualCharacterXY().y * 0.1;
        double xDif = Math.pow(x1 - x0, 2);
        double yDif = Math.pow(y1 - y0, 2);
        double distance = xDif + yDif;

        if (distance < IN_VIEW_DISTANCE){
            ((FloatControl) rangeAttackCLip.
                    getControl(FloatControl.Type.MASTER_GAIN)).setValue(0);
            ((FloatControl) explosionClip.
                    getControl(FloatControl.Type.MASTER_GAIN)).setValue(0);
            ((FloatControl) meleeAttackClip.
                    getControl(FloatControl.Type.MASTER_GAIN)).setValue(0);
            ((FloatControl) dieClip.
                    getControl(FloatControl.Type.MASTER_GAIN)).setValue(0);
            return;
        }
        else {
            float volume = -(float) distance * TO_SOUND_CONSTANT;
            float dieVolume = 0;
            if (volume * DIECLIP_MULTIPLIER < MINIMUM_VOLUME * DIECLIP_MULTIPLIER) {
                volume = -79;
                dieVolume = -79;
            }

            ((FloatControl) rangeAttackCLip.
                    getControl(FloatControl.Type.MASTER_GAIN)).setValue(volume);
            ((FloatControl) explosionClip.
                    getControl(FloatControl.Type.MASTER_GAIN)).setValue(volume);
            ((FloatControl) meleeAttackClip.
                    getControl(FloatControl.Type.MASTER_GAIN)).setValue(volume);
            ((FloatControl) dieClip.
                    getControl(FloatControl.Type.MASTER_GAIN)).setValue(dieVolume);

        }

    }
}
