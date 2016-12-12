package CDC;

import transfer.TransferPlayer;

public class ClientPlayerFeature {

    private TransferPlayer transferPlayer;
    private int velocity = 2;
    private int lastShootTime;
    private int lastMoveTime;

    public ClientPlayerFeature(int clientNo, String nickName) {
        transferPlayer = new TransferPlayer(clientNo, nickName);

    }

    public TransferPlayer getTransferPlayer() {
        return transferPlayer;
    }

    public void setTransferPlayer(TransferPlayer transferPlayer) {
        this.transferPlayer = transferPlayer;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getLastShootTime() {
        return lastShootTime;
    }

    public void setLastShootTime(int lastShootTime) {
        this.lastShootTime = lastShootTime;
    }

    public int getLastMoveTime() {
        return lastMoveTime;
    }

    public void setLastMoveTime(int lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    // transferPlayer
    public int getPlayerId() {
        return getPlayerId();
    }


    public int getWeaponType() {
        return transferPlayer.weaponType;
    }

    public void setWeaponType(int weaponType) {
        transferPlayer.weaponType = weaponType;
    }

    public String getNickName() {
        return getNickName();
    }

    public int getLocationX() {
        return transferPlayer.locX;
    }

    public void setLocationX(int locX) {
        transferPlayer.locX = locX;
    }

    public int getLocationY() {
        return transferPlayer.locY;
    }

    public void setLocationY(int locY) {
        transferPlayer.locY = locY;
    }

    public double getFaceAngle() {
        return transferPlayer.faceAngle;
    }

    public void setFaceAngle(double faceAngle) {
        transferPlayer.faceAngle = faceAngle;
    }

    public int getHP() {
        return transferPlayer.HP;
    }

    public void setHP(int hP) {
        transferPlayer.HP = hP;
    }

    public int getKillCount() {
        return transferPlayer.killCount;
    }

    public void setKillCount(int killCount) {
        transferPlayer.killCount = killCount;
    }

    public int getDeadCount() {
        return transferPlayer.deadCount;
    }

    public void setDeadCount(int deadCount) {
        transferPlayer.deadCount = deadCount;
    }

    public int getBulletCount() {
        return transferPlayer.bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        transferPlayer.bulletCount = bulletCount;
    }

    public boolean getAttackFlag() {
        return transferPlayer.attackFlag;
    }

    public void setAttackFlag(boolean attackFlag) {
        transferPlayer.attackFlag = attackFlag;
    }

    public boolean getAttackedFlag() {
        return transferPlayer.attackedFlag;
    }

    public void setAttackedFlag(boolean attackedFlag) {
        transferPlayer.attackedFlag = attackedFlag;
    }

    public boolean getCollisionFlag() {
        return transferPlayer.collisionFlag;
    }

    public void setCollisionFlag(boolean collisionFlag) {
        transferPlayer.collisionFlag = collisionFlag;
    }

    public boolean getIsDead() {
        return transferPlayer.isDead;
    }

    public void setIsDead(boolean isDead) {
        transferPlayer.isDead = isDead;
    }

}
