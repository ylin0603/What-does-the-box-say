package audio;



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
            gainControl.setValue(-5.0f);


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
    public static AudioManager getInstance(){
        if(instance==null)
            instance = new AudioManager();
        return instance;
    }
    public void playBackGroundMusic () {
        backGroundClip.start();
    }
    public void playSoundEffect(String name) {
        resetAllSoundEffect();
        switch (name){
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
                break;
            case "die":
                dieClip.start();
                break;
        }
    }
    private void resetAllSoundEffect(){
        attackedClip.setMicrosecondPosition(0);
        meleeAttackClip.setMicrosecondPosition(0);
        rangeAttackCLip.setMicrosecondPosition(0);
        explosionClip.setMicrosecondPosition(0);
        dieClip.setMicrosecondPosition(0);

    }

}
