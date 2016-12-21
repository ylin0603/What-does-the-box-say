package renderer.data.entity;

import renderer.graphics.Sprite;

/**
 * Created by TsunglinYang on 2016/12/21.
 */
public class FakeBox extends Item {
    final static int EP_NUMBER = 8;
    private ExplosionParticle ep = new ExplosionParticle();
    private int counter = 0, timer = 0;
    private boolean animationFinished = true;

    public FakeBox(int type, int index, boolean isDead, int x, int y) {
        super(type, index, isDead, x, y);
        this.sprite = Sprite.PLAYER;
    }

    @Override
    public void update(boolean isDead, int owner, int x, int y) {
        super.update(isDead, owner, x, y);
        if (isDead)
            animationFinished = false;

    }

    @Override
    public void update() {
        timer++;
        if (timer % 6 == 0) {
            counter++;
            if (!animationFinished) {
                ep.sprite = Sprite.BOX_EXPLOSION[counter%4];
                animationFinished = (counter%4==0);
            } else{
                timer = 0;
                counter = 0;
            }
        }
    }

    @Override
    public void render(int[] pixels) {
        super.render(pixels);
        ep.render(pixels);
    }
}
