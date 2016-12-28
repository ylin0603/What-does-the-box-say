package renderer.data.entity;

import audio.AudioManager;
import renderer.graphics.Sprite;

/**
 * Created by TsunglinYang on 2016/12/21.
 */
public class FakeBox extends Item {
    final static int EP_NUMBER = 8;
    private ExplosionParticle ep = new ExplosionParticle();
    private int counter = 0, timer = 0;
    private boolean animationFinished = true;
    private int deadPosX = 0;
    private int deadPosY = 0;

    public FakeBox(int type, int index, boolean isDead, int x, int y) {
        super(type, index, isDead, x, y, Sprite.PLAYER);
        //this.sprite = Sprite.PLAYER;
    }

    private boolean audioPlayed;

    @Override
    public void set(boolean isDead, int owner, int x, int y) {
        super.set(isDead, owner, x, y);
        if(isDead && !audioPlayed) {
            AudioManager.getInstance().playSoundEffect("explosion");
            audioPlayed = true;
        }
        else if(!isDead)
            audioPlayed = false;

        if (isDead) {
            animationFinished = false;
            deadPosX = x;
            deadPosY = y;
        }
    }

    @Override
    public void update() {
        super.update();
        timer++;
        if (timer % 6 == 0) {
            counter++;
            if (!animationFinished) {
                ep.x = deadPosX;
                ep.y = deadPosY;
                ep.sprite = Sprite.BOX_EXPLOSION[counter % 4];
                animationFinished = (counter % 4 == 0);
            } else {
                timer = 0;
                counter = 0;
                ep.sprite = Sprite.EMPTY;
            }
        }
    }

    @Override
    public void render(int[] pixels) {
        super.render(pixels);
        ep.render(pixels);
    }

}
