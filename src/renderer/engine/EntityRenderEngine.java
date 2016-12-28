package renderer.engine;

import com.sun.java.browser.plugin2.DOM;
import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import renderer.data.entity.Bullet;
import renderer.data.entity.Character;
import renderer.data.entity.Entity;
import renderer.data.entity.Item;
import renderer.graphics.Sprite;
import renderer.level.tile.Tile;

import java.util.List;

/**
 * Created by tsunglin on 2016/12/2.
 */
public class EntityRenderEngine {
    private int[] pixels;
    private DynamicObjectManager dom = null;

    public EntityRenderEngine() {
        dom = DynamicObjectManager.getInstance();
    }

    public void render(int pixels[]) {
        this.pixels = pixels;

        List<Character> allCharacter = dom.getCharacterList();
        List<Item> allItem = dom.getItemList();
        List<Bullet> allBullets = dom.getBullets();
        for (Item i : allItem) {
            if(isRangeInView(i))
                i.render(pixels);
        }
        for (Character c : allCharacter) {
            if(isRangeInView(c))
                c.render(pixels);
        }
        dom.setBullet();
        for (Bullet b : allBullets) {
            if(isRangeInView(b))
                b.render(pixels);
        }
        //allCharacter.stream().filter(e -> !e.isDead()).forEach(e -> e.render(pixels));
    }

    public static void renderEntity(Entity entity, int pixels[]) {
        int offsetX = DynamicObjectManager.getInstance().getVirtualCharacterXY().x - Game.WIDTH / 2;
        int offsetY = DynamicObjectManager.getInstance().getVirtualCharacterXY().y - Game.HEIGHT / 2;
        int xp = entity.x;
        int yp = entity.y;
        xp -= offsetX;
        yp -= offsetY;
        Sprite s = entity.getSprite();
        for (int y = 0; y < s.SIZE; y++) {
            int ya = y + yp;
            for (int x = 0; x < s.SIZE; x++) {
                int xa = x + xp;
                if (xa < -s.SIZE || xa >= Game.WIDTH || ya < 0 || ya >= Game.HEIGHT) break;
                if (xa < 0) xa = 0;
                int color = s.pixels[x + y * s.SIZE];
                if (color != 0xffffffff) pixels[xa + ya * Game.WIDTH] = color;
            }
        }
    }

    public static void renderEntity(int xPos, int yPos, int pixels[], Sprite s) {
        int offsetX = DynamicObjectManager.getInstance().getVirtualCharacterXY().x - Game.WIDTH / 2;
        int offsetY = DynamicObjectManager.getInstance().getVirtualCharacterXY().y - Game.HEIGHT / 2;
        int xp = xPos;
        int yp = yPos;
        xp -= offsetX;
        yp -= offsetY;
        for (int y = 0; y < s.SIZE; y++) {
            int ya = y + yp;
            for (int x = 0; x < s.SIZE; x++) {
                int xa = x + xp;
                if (xa < -s.SIZE || xa >= Game.WIDTH || ya < 0 || ya >= Game.HEIGHT) break;
                if (xa < 0) xa = 0;
                int color = s.pixels[x + y * s.SIZE];
                if (color != 0xffffffff) pixels[xa + ya * Game.WIDTH] = color;
            }
        }
    }

    public static boolean isRangeInView(Entity entity) {
        int xp = entity.x;
        int yp = entity.y;
        int x0 = DynamicObjectManager.getInstance().getVirtualCharacterXY().x - Game.WIDTH / 2;
        int y0 = DynamicObjectManager.getInstance().getVirtualCharacterXY().y - Game.HEIGHT / 2;
        int x1 = x0 + Game.WIDTH + Tile.SIZE;
        int y1 = y0 + Game.HEIGHT + Tile.SIZE;
        if (xp>x1 || yp >y1 || xp<x0 || yp<y0)
            return false;
        return true;
    }

}
