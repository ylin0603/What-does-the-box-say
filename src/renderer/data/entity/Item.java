package renderer.data.entity;

import renderer.graphics.Sprite;

public class Item extends Entity {
    private String name;
    private int index;
    private boolean isDead;
    private int owner;

    public Item(int type, int index, boolean isDead, int x, int y) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public Boolean getIsDead() {
        return isDead;
    }

    public int getOwner() {
        return owner;
    }
}
