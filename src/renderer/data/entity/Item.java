package renderer.data.entity;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import renderer.graphics.Sprite;

public class Item extends Entity {
    private String name;
    private int index;
    private Boolean shared;
    private int owner;

    public Item(String name, int index, Boolean shared, int x, int y) {
        this.name = name;
        this.index = index;
        this.shared = shared;
        this.x = x;
        this.y = y;
        this.sprite = Sprite.ITEM;
    }


    public void update(boolean shared, int owner){
        this.shared = shared;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public Boolean getShared() {
        return shared;
    }

    public int getOwner() {
        return owner;
    }
}
