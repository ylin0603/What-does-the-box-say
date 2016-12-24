package renderer.data.entity;

import renderer.engine.EntityRenderEngine;

/**
 * Created by TsunglinYang on 2016/12/20.
 */
public class Sword extends Entity {
    @Override
    public void update() {

    }

    @Override
    public void render(int[] pixels) {
        EntityRenderEngine.renderEntity(x,y,pixels,sprite);
    }
}
