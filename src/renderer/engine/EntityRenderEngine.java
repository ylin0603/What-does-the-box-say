package renderer.engine;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import renderer.data.entity.Entity;
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

}
