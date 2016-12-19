package udp.update.server;


public class ClientPlayerFeature {
    private int clientNo;
    private int weaponType = 0; // 0 for knife, 1 for gun
    private String nickname;
    private int locX, locY;
    private long lastMoveTime;
    private long attackCD = 0;
	private long changeWeaponCD = 0;
    private long resurrectionTime = 0;
    private final int resurrectionCD = 4000;

    private boolean[] direction = new boolean[6];// "wsad j"
    private double faceAngle = 0; // (degree) => use Math.toRadium();
    private int HP = 100;
    private int killCount = 0, deadCount = 0;
    private int bulletCount = 2;
    private int maxBulletCount = 3;
    private boolean attackFlag = false; // TCP設成true，傳送一次後設回false
    private boolean attackedFlag = false; // CDC設成true，UDP傳回一次後再設回false
    private boolean collisionFlag = false; // 同上
    private boolean isDead = false; // Bear will do it.

    public ClientPlayerFeature(int clientNo, String nickName, int locX,
            int locY) {
        this.clientNo = clientNo;
        this.nickname = nickName;
        this.locX = locX;
        this.locY = locY;
        this.HP = 100;
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

    public boolean[] getDirection() {
        return direction;
    }

    public void setDirection(boolean[] direction) {
        this.direction = direction;
    }

    public int getLocX() {
        return locX;
    }

    public void setLocX(int locX) {
        if (locX > 1985)
            locX = 1984;
        else if (locX < 0)
            locX = 0;
        this.locX = locX;
        lastMoveTime = System.currentTimeMillis();
    }

    public int getLocY() {
        return locY;
    }

    public void setLocY(int locY) {
        if (locY > 1985)
            locY = 1985;
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
        attackCD = System.currentTimeMillis() + 1000;
    }

    public void setDeadCount(int deadCount) {
        this.deadCount = deadCount;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    public void addBullet(int bullet) {
        this.bulletCount += bullet;
        if (this.bulletCount > 3) {
            this.bulletCount = 3;
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

    public void checkRecover() { // 檢查是否停在原地
        long stopSecond = System.currentTimeMillis() - lastMoveTime;
        if (stopSecond >= 5000) {
            HP += 5;
            lastMoveTime += 1000;
        }
    }

    public boolean checkResurrection() { // 檢查復活
        if (System.currentTimeMillis() >= resurrectionTime)
            return true;
        else
            return false;
    }
}
