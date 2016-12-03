package renderer.data.entity;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import renderer.graphics.Sprite;

public class Character extends Entity {

    private int dir;
    private int speed;
    private int clientno;

    public Character(int clientno) {
        this.clientno = clientno;
        Initialize();
    }

    public void Initialize() {
        this.x = 0;
        this.y = 0;
        this.dir = 0;
        this.speed = 0;
    }

    public void update(int dir, int speed, int x, int y) {
        this.dir = dir;
        this.speed = speed;
        this.x = x;
        this.y = y;
        sprite = Sprite.PLAYER;
    }


}
