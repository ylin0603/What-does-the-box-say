package CDC;

public class ClientBulletFeature {
    private int itemType;
    // 0: Moving Bullet, 1: Moving Sword, 2: Revenge Box
    private int locX, locY;
    private int oriLocX, oriLocY;
    private double faceAngle = 0; // (degree) => use Math.toRadium();
    private boolean isDead = false;
    private int itemOwner;
    boolean[] isAttacked = new boolean[4];

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

    public boolean[] getIsAttacked() {
        return isAttacked;
    }

    public void setIsAttacked(boolean[] isAttacked) {
        this.isAttacked = isAttacked;
    }
}
