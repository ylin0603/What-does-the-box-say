package CDC;


public class ClientPlayerFeature {
    private int clientNo;
    private int weaponType = 0; // 0 for knife, 1 for gun
    private String nickname;
    private int locX, locY;
    private long lastMoveTime = System.currentTimeMillis();
    private long attackCD = 0;
    private long changeWeaponCD = 0;
    private long resurrectionTime = 0;
    private final int resurrectionCD = 4000;

    private int[] direction = new int[4];// "move,spin,attack,change weapon"
    private double faceAngle = 0; // (degree) => use Math.toRadium();
    private int HP = 100;
    private int killCount = 0, deadCount = 0;
    private int bulletCount = 2;
    private int maxBulletCount = 3;
    private boolean attackFlag = false; // attack.java在成功攻擊時設成true，
    // CD中 false 放開 false 人死 false
    private boolean attackedFlag = false; // CDC設成true，UDP傳回一次後再設回false
    private boolean collisionFlag = false; // 同上
    private boolean isDead = false; // Bear will do it.

    public ClientPlayerFeature(int clientNo, String nickName, int locX,
            int locY) {
        this.clientNo = clientNo;
        this.nickname = nickName;
        init();
        reborn(locX, locY);
    }

    public void init() {
        this.attackCD = 0;
        this.changeWeaponCD = 0;
        faceAngle = 0.0;
        resetPerRound();
    }

    public void resetPerRound() {
        this.attackFlag = false;
        this.attackedFlag = false;
        this.collisionFlag = false;
    }

    public void reborn(int locX, int locY) {
        this.locX = locX;
        this.locY = locY;
        this.HP = 100;
        this.bulletCount = 2;
        isDead = false;
        setLastMoveTime();
    }

    public int getClientNo() {
        return clientNo;
    }

    public void setClientNo(int clientNo) {
        this.clientNo = clientNo;
    }

    public int getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(int weaponType) {
        this.weaponType = weaponType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int[] getDirection() {
        return direction;
    }

    public void setDirection(int[] direction) {
        this.direction = direction;
    }

    public int getLocX() {
        return locX;
    }

    public void setLocX(int locX) {
        if (locX > Cdc.MAP_SIZE_X - Cdc.BOX_SIZE)
            locX = Cdc.MAP_SIZE_X - Cdc.BOX_SIZE;
        else if (locX < 0)
            locX = 0;
        this.locX = locX;
        lastMoveTime = System.currentTimeMillis();
    }

    public int getLocY() {
        return locY;
    }

    public void setLocY(int locY) {
        if (locY > Cdc.MAP_SIZE_Y - Cdc.BOX_SIZE)
            locY = Cdc.MAP_SIZE_Y - Cdc.BOX_SIZE;
        else if (locY < 0)
            locY = 0;
        this.locY = locY;
        lastMoveTime = System.currentTimeMillis();
    }

    public double getFaceAngle() {
        return faceAngle;
    }

    public void setFaceAngle(double faceAngle) {
        if (faceAngle < 0)
            faceAngle += 360;
        else if (faceAngle > 360)
            faceAngle -= 360;
        this.faceAngle = faceAngle;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void addHP(int HP) {
        this.HP += HP;
        if (this.HP > 100) {
            this.HP = 100;
        }
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public int getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(int deadCount) {
        this.deadCount = deadCount;
    }

    public boolean isAttackCD() {
        if (attackCD <= System.currentTimeMillis())
            return true;
        else
            return false;
    }

    public void setAttackCD() {
        attackCD = System.currentTimeMillis() + 1000;
    }

    public boolean isChangeWeaponCD() {
        if (changeWeaponCD <= System.currentTimeMillis())
            return true;
        else
            return false;
    }

    public void setChangeWeaponCD() {
        changeWeaponCD = System.currentTimeMillis() + 1000;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    public void addBullet(int bullet) {
        this.bulletCount += bullet;
        if (this.bulletCount > maxBulletCount) {
            this.bulletCount = maxBulletCount;
        }
    }

    public boolean isAttackFlag() {
        return attackFlag;
    }

    public void setAttackFlag(boolean attackFlag) {
        this.attackFlag = attackFlag;
    }

    public boolean isAttackedFlag() {
        return attackedFlag;
    }

    public void setAttackedFlag(boolean attackedFlag) {
        this.attackedFlag = attackedFlag;
    }

    public boolean isCollisionFlag() {
        return collisionFlag;
    }

    public void setCollisionFlag(boolean collisionFlag) {
        this.collisionFlag = collisionFlag;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
        if (dead) {
            resurrectionTime = System.currentTimeMillis() + resurrectionCD;
        }
    }

    public boolean checkResurrection() { // 檢查復活
        if (System.currentTimeMillis() >= resurrectionTime)
            return true;
        else
            return false;
    }

    public long getLastMoveTime() {
        return lastMoveTime;
    }

    public void setLastMoveTime(long lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    public void setLastMoveTime() {
        lastMoveTime = System.currentTimeMillis();
    }
}
