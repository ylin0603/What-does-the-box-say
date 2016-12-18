package renderer.data.entity;

import renderer.graphics.Sprite;

public class Item extends Entity {
    
    private int itemType;
    private int index;
    private int owner;

    public Item(int type, int index, boolean isDead, int x, int y) {
        this.itemType = type;
        this.index = index;
        this.isDead = isDead;
        this.x = x;
        this.y = y;
        this.sprite = Sprite.ITEM;
    }


    public void update(boolean isDead, int owner) {
        this.isDead = isDead;
        this.owner = owner;
    }

    public void render(int[] pixels) {
        super.render(pixels);
    }

    public int getType() {
        return itemType;
    }

    public int getIndex() {
        return index;
    }

    public int getOwner() {
        return owner;
    }
}
