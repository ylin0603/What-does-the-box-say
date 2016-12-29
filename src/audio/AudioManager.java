package audio;


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

    private AudioManager() {
    }

    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    public void playBackGroundMusic() {
        try {
            backGroundClip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    AudioManager.class.getResource("backGround.wav"));
            backGroundClip.open(inputStream);
            backGroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            FloatControl gainControl =
                    (FloatControl) backGroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-7.0f);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        backGroundClip.start();
    }

    public void playSoundEffect(String name, Entity entity) {
        //resetAllSoundEffect();
        AudioInputStream inputStream;
        Clip clip = null;
        try {
            switch (name) {
                case "attacked":
                    break;
                case "meleeAttack":
                    clip = AudioSystem.getClip();
                    inputStream = AudioSystem.getAudioInputStream(
                            AudioManager.class.getResource("melee.wav"));
                    clip.open(inputStream);
                    break;
                case "rangeAttack":
                    clip = AudioSystem.getClip();
                    inputStream = AudioSystem.getAudioInputStream(
                            AudioManager.class.getResource("range.wav"));
                    clip.open(inputStream);
                    System.out.println("rangeAttack Sound Effect");
                    break;
                case "explosion":
                    clip = AudioSystem.getClip();
                    inputStream = AudioSystem.getAudioInputStream(
                            AudioManager.class.getResource("explosion.wav"));
                    clip.open(inputStream);
                    System.out.println("explosion sound Effect");
                    break;
                case "die":
                    clip = AudioSystem.getClip();
                    inputStream = AudioSystem.getAudioInputStream(
                            AudioManager.class.getResource("die.wav"));
                    clip.open(inputStream);
                    break;
            }
            setVolume(entity, clip);
            assert clip != null;
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }


    private static final int IN_VIEW_DISTANCE = 170;
    private static final float TO_SOUND_CONSTANT = 0.0002f;
    private static final float DIECLIP_MULTIPLIER = 1.5f;
    private static final float MINIMUM_VOLUME = -80.0f;

    private void setVolume(Entity entity, Clip clip) {
        double x1 = entity.x;
        double y1 = entity.y;
        double x0 = DynamicObjectManager.getInstance().getVirtualCharacterXY().x;
        double y0 = DynamicObjectManager.getInstance().getVirtualCharacterXY().y;
        double xDif = Math.pow(x1 - x0, 2);
        double yDif = Math.pow(y1 - y0, 2);
        double distance = xDif + yDif;
        if(Math.sqrt(distance) < IN_VIEW_DISTANCE)
            return;
        float volume = -(float) distance * TO_SOUND_CONSTANT;
        if (volume * DIECLIP_MULTIPLIER < MINIMUM_VOLUME * DIECLIP_MULTIPLIER) {
            volume = -79;
        }
        ((FloatControl) clip.
                getControl(FloatControl.Type.MASTER_GAIN)).setValue(volume);

    }

}

