package renderer.engine;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import renderer.data.entity.Character;
import renderer.data.entity.Entity;
import renderer.data.entity.Item;
import renderer.graphics.Sprite;

import java.util.List;

/**
 * Created by tsunglin on 2016/12/2.
 */
public class EntityRenderEngine {
    private int[] pixels;
    private DynamicObjectManager dom = null;

    public EntityRenderEngine(){
        dom = DynamicObjectManager.getInstance();
    }

    public void render(int pixels[]) {
        this.pixels = pixels;
        List<Entity> allCharacter = dom.getAllDynamicObjects();
        for (Entity e : allCharacter) {
            e.render(pixels);
        }
    }
    public static void renderCharacter(Character character, int pixels[]){
        int offsetX = DynamicObjectManager.getInstance().getVirtualCharacterXY().x - Game.WIDTH / 2;
        int offsetY = DynamicObjectManager.getInstance().getVirtualCharacterXY().y - Game.HEIGHT / 2;
        int xp = character.x;
        int yp = character.y;
        xp -= offsetX;
        yp -= offsetY;
        Sprite s = character.getSprite();
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

    public static void renderitem(Item item, int pixels[]){
        int offsetX = DynamicObjectManager.getInstance().getVirtualCharacterXY().x - Game.WIDTH / 2;
        int offsetY = DynamicObjectManager.getInstance().getVirtualCharacterXY().y - Game.HEIGHT / 2;
        int xp = item.x;
        int yp = item.y;
        xp -= offsetX;
        yp -= offsetY;
        Sprite s = item.getSprite();
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
}
