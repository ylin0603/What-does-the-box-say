package CDC;

import java.util.HashSet;
import java.util.Set;

public class ClientBulletFeature {
    private int itemType;
    // 0: Moving Sword, 1: Moving Bullet, 2: Revenge Box
    private int locX, locY;
    private int oriLocX, oriLocY;
    private double faceAngle = 0; // (degree) => use Math.toRadium();
    private boolean isDead = false;// useless
    private int itemOwner;
    private Set<Integer> isAttackedSet = new HashSet<Integer>();


    ClientBulletFeature(int itemType, int locX, int locY, double faceAngle,
            int itemOwner) {
        assert itemType == 0 || itemType == 1;
        this.locX = locX;
        this.locY = locY;
        this.oriLocX = locX;
        this.oriLocY = locY;
        this.faceAngle = faceAngle;
        this.itemOwner = itemOwner;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getLocX() {
        return locX;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public int getLocY() {
        return locY;
    }

    public void setLocY(int locY) {
        this.locY = locY;
    }

    public int getOriLocX() {
        return oriLocX;
    }

    public void setOriLocX(int oriLocX) {
        this.oriLocX = oriLocX;
    }

    public int getOriLocY() {
        return oriLocY;
    }

    public void setOriLocY(int oriLocY) {
        this.oriLocY = oriLocY;
    }

    public double getFaceAngle() {
        return faceAngle;
    }

    public void setFaceAngle(double faceAngle) {
        this.faceAngle = faceAngle;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public int getItemOwner() {
        return itemOwner;
    }

    public void setItemOwner(int itemOwner) {
        this.itemOwner = itemOwner;
    }

    public boolean getIsAttacked(int ClientNo) {
        return isAttackedSet.contains(ClientNo);
    }

    public void setIsAttacked(int ClientNo) {
        this.isAttackedSet.add(ClientNo);
    }
}
