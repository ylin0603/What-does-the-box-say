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
    private int count = 0;
    private int timer = 0;
    private boolean animationFinished = true;
    private int clientno;
    private Sword sword = new Sword();

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
        sword.sprite = Sprite.EMPTY;
    }

    public void set(int weaponType, String nickName, int x, int y,
                    double angle, int HP, int killCount, int deadCount,
                    int bulletCount, boolean attackFlag, boolean attackedFlag,
                    boolean collisionFlag, boolean isDead) {
        this.weaponType = weaponType;
        this.nickName = nickName;
        this.x = x;
        this.y = y;
        this.faceAngle = angle;
        this.killCount = killCount;
        this.deadCount = deadCount;
        this.bulletCount = bulletCount;
        this.attackFlag = attackFlag;
        this.attackedFlag = attackedFlag;
        this.collisionFlag = collisionFlag;
        this.isDead = isDead;

        //this.sprite = Sprite.rotate(Sprite.PLAYER,angle);
    }

    public void update() {
        timer++;
        if (timer % 4 == 0) {
            count++;
        }

        if (attackFlag || !animationFinished) {
            animationFinished = false;
            if (faceAngle >= 45 && faceAngle < 135) {
                sword.sprite = Sprite.SWORD_RIGHT_ATTACK[count % 5];
                sword.x = x;
                sword.y = y - this.sprite.SIZE / 2;
            } else if (faceAngle >= 135 && faceAngle < 225) {
                sword.sprite = Sprite.SWORD_DOWNWARD_ATTCK[count % 5];
                sword.x = x - this.sprite.SIZE / 2;
                sword.y = y - this.sprite.SIZE /3 ;
            } else if (faceAngle >= 225 && faceAngle < 315) {
                sword.sprite = Sprite.SWORD_LEFT_ATTACK[count % 5];
                sword.x = x - this.sprite.SIZE ;
                sword.y = y - this.sprite.SIZE / 3;
            } else if ((faceAngle >= 315 && faceAngle < 360) || (faceAngle >= 0 && faceAngle < 45)) {
                sword.sprite = Sprite.SWORD_UPWARD_ATTACK[count % 5];
                sword.x = x - this.sprite.SIZE / 2;
                sword.y = y - this.sprite.SIZE;
            }

            animationFinished = (count % 4 == 3);

        } else {
            sword.sprite = Sprite.EMPTY;
        }

        if (count == 12000) {
            count = 0;
        }

    }

    public void render(int[] pixels) {
        //TODO: If attack flag == true, draw addition animation
        //TODO: bullet should have additional class and draw additionally.
        super.render(pixels);
        sword.render(pixels);
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
