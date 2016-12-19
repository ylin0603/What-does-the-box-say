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
        if (timer % 5 == 0) {
            count++;
        }

        if (attackFlag || !animationFinished) {

            animationFinished = false;

            if (faceAngle >= 45 && faceAngle < 135) {
                this.sprite = Sprite.BOX_ATTACK_RIGHT[count % 4];
            } else if (faceAngle >= 135 && faceAngle < 225) {
                this.sprite = Sprite.BOX_ATTACK_DOWN[count % 4];
            } else if (faceAngle >= 225 && faceAngle < 315) {
                this.sprite = Sprite.BOX_ATTACK_LEFT[count % 4];
            } else if ((faceAngle >= 315 && faceAngle < 360) || (faceAngle >= 0 && faceAngle < 45)) {
                this.sprite = Sprite.BOX_ATTACK_UP[count % 4];
            }

            animationFinished = (count % 4 == 3);

        } else {
            this.sprite = Sprite.PLAYER;
        }

        if (count == 12000) {
            count = 0;
        }

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
