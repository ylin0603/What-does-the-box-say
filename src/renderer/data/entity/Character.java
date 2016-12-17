package renderer.data.entity;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import renderer.engine.EntityRenderEngine;
import renderer.graphics.Sprite;

public class Character extends Entity {
    private int playerId;
    private int weaponType;
    private String nickName;
    private double faceAngle;
    private int HP;
    private int killCount, deadCount;
    private int bulletCount;
    private boolean attackFlag;
    private boolean attackedFlag;
    private boolean collisionFlag;
    private boolean isDead;

    private int clientno;

    public Character(int clientno, String nickName) {
        this.clientno = clientno;
        this.nickName = nickName;
        Initialize();
    }

    public void Initialize() {
        this.x = 0;
        this.y = 0;
        this.dir = 0;
        this.HP = 100;

        sprite = Sprite.PLAYER;
    }

    public void update(int weaponType, String nickName, int x, int y,
                       double angle, int HP, int killCount, int deadCount,
                       int bulletCount, boolean attackFlag, boolean attackedFlag,
                       boolean collisionFlag, boolean isDead) {
        this.weaponType = weaponType;
        this.nickName = nickName;
        this.x = x;
        this.y = y;
        this.faceAngle = angle;
        this. killCount = killCount;
        this.deadCount = deadCount;
        this.bulletCount = bulletCount;
        this.attackFlag = attackFlag;
        this.attackedFlag = attackedFlag;
        this.collisionFlag = collisionFlag;
        this.isDead = isDead;

        //this.sprite = Sprite.rotate(Sprite.PLAYER,angle);
    }

    public void render(int[] pixels) {
        //TODO: If attack flag == true, draw addition animation
        //TODO: bullet should have additional class and draw additionally.
        super.render(pixels);
    }
    
    public int getWeaponType() {
    	return weaponType;
    }

    public String getNickName() {
    	return nickName;
    }
    
    public double getFaceAngle() {
    	return faceAngle;
    }
    
    public int getHP() {
    	return HP;
    }
    
    public int getKillCount() {
    	return killCount;
    }
    
    public int getDeadCount() {
    	return deadCount;
    }
    
    public int getBulletCount() {
    	return bulletCount;
    }
}
