package renderer.data.entity;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import renderer.engine.EntityRenderEngine;
import renderer.graphics.Sprite;

import java.util.Map;
import java.util.Random;

/**
 * Created by tsunglin on 2016/12/2.
 */
public abstract class Entity {
    public int x, y;
    protected double dir;
    private boolean removed = false;
    protected Sprite sprite;

    public void update() {

    }

    public void render(int pixels[]) {
        EntityRenderEngine.renderEntity(this,pixels);
    }


    public void remove() {
        //TODO:: remove from level
        removed = true;
    }

    public double getDirection() {
        return dir;
    }
    public Sprite getSprite() {return sprite;}
    public boolean isRemoved() {
        return removed;
    }
}