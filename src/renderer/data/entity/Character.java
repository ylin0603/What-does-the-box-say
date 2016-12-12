package renderer.data.entity;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import renderer.engine.EntityRenderEngine;
import renderer.graphics.Sprite;

public class Character extends Entity {

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
        sprite = Sprite.PLAYER;
    }

    public void update(double dir, int speed, int x, int y) {
        this.dir = dir;
        this.speed = speed;
        this.x = x;
        this.y = y;
        //this.sprite = Sprite.rotate(Sprite.PLAYER,Math.toRadians(dir));
    }

    public void render(int[] pixels){
        //TODO: If attack flag == true, draw addition animation
        //TODO: bullet should have additional class and draw additionally.
        super.render(pixels);
    }


}
