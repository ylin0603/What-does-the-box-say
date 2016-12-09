package renderer.data.entity;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;
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
        int offsetX = DynamicObjectManager.getInstance().getVirtualCharacterXY().x - Game.WIDTH / 2;
        int offsetY = DynamicObjectManager.getInstance().getVirtualCharacterXY().y - Game.HEIGHT / 2;
        int xp = this.x;
        int yp = this.y;
        xp -= offsetX;
        yp -= offsetY;
        for (int y = 0; y < sprite.SIZE; y++) {
            int ya = y + yp;
            for (int x = 0; x < sprite.SIZE; x++) {
                int xa = x + xp;
                if (xa < -sprite.SIZE || xa >= Game.WIDTH || ya < 0 || ya >= Game.HEIGHT) break;
                if (xa < 0) xa = 0;
                int color = sprite.pixels[x + y * sprite.SIZE];
                if (color != 0xffffffff) pixels[xa + ya * Game.WIDTH] = color;
            }
        }
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