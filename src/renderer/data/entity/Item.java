package renderer.data.entity;

import renderer.graphics.Sprite;

public class Item extends Entity {
    
    private int itemType;
    private int index;
    private int owner;

    public Item(int type, int index, boolean isDead, int x, int y, Sprite s) {
        super(s);
        this.itemType = type;
        this.index = index;
        this.isDead = isDead;
        this.x = x;
        this.y = y;
    }


    public void set(boolean isDead, int owner, int x, int y) {
        this.isDead = isDead;
        this.owner = owner;
        this.x = x;
        this.y = y;
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
