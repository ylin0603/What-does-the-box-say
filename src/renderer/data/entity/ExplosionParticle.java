package renderer.data.entity;

import renderer.engine.EntityRenderEngine;
import renderer.graphics.Sprite;

/**
 * Created by TsunglinYang on 2016/12/21.
 */
public class ExplosionParticle extends Entity {
    final static int FAKEBOX_SIZE = Sprite.PLAYER.SIZE;

    public ExplosionParticle() {
        this.sprite = Sprite.EMPTY;
    }

    @Override
    public void render(int[] pixels) {
        EntityRenderEngine.renderEntity(x - FAKEBOX_SIZE, y - FAKEBOX_SIZE, pixels, sprite);
        EntityRenderEngine.renderEntity(x, y - FAKEBOX_SIZE, pixels, sprite);
        EntityRenderEngine.renderEntity(x + FAKEBOX_SIZE, y - FAKEBOX_SIZE, pixels, sprite);
        EntityRenderEngine.renderEntity(x - FAKEBOX_SIZE, y, pixels, sprite);
        EntityRenderEngine.renderEntity(x + FAKEBOX_SIZE, y, pixels, sprite);
        EntityRenderEngine.renderEntity(x - FAKEBOX_SIZE, y + FAKEBOX_SIZE, pixels, sprite);
        EntityRenderEngine.renderEntity(x, y + FAKEBOX_SIZE, pixels, sprite);
        EntityRenderEngine.renderEntity(x + FAKEBOX_SIZE, y + FAKEBOX_SIZE, pixels, sprite);
    }
}
