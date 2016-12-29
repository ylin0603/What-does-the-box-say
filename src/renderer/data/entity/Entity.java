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
    protected boolean isDead;
    protected Sprite sprite;
    private Sprite deadSprite = Sprite.EMPTY;
    private Sprite currentSprite;

    public Entity(Sprite s){
        sprite = s;
        currentSprite = s;
    }
    public Entity(){
        this.sprite=Sprite.EMPTY;
        this.currentSprite=Sprite.EMPTY;
    }

    public void update() {
        if (isDead) currentSprite = deadSprite;
        else currentSprite = sprite;
    }

    public void render(int pixels[]) {
        EntityRenderEngine.renderEntity(this, pixels);
    }


    protected void setDeadSprite(Sprite deadSprite) {
        this.deadSprite = deadSprite;
    }

    public void remove() {
        isDead = true;
    }

    public double getDirection() {
        return dir;
    }

    public Sprite getSprite() {
        assert currentSprite != null;
        return currentSprite;
    }

    public boolean isDead() {
        return isDead;
    }

}