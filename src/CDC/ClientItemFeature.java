package CDC;

public class ClientItemFeature {
    private int itemID; // unique ID
    private int itemType;
    // 0: Fake Box, 1: Add HP, 2: Add Bullet
    private int locX, locY;
    private double faceAngle = 0; // (degree) => use Math.toRadium();
    private boolean isCollision = false;
    private boolean isDead = false;
    private boolean isReborn = false;
    private long rebornTime;

    public ClientItemFeature(int itemID, int itemType, int x, int y) {
        this.itemID = itemID;
        this.itemType = itemType;
        init(x, y);
    }

    public void init(int locX, int locY) {
        setLocX(locX);
        setLocY(locY);
        setDead(false);
        setReborn(false);
        setCollision(false);
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
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

    public double getFaceAngle() {
        return faceAngle;
    }

    public void setFaceAngle(double faceAngle) {
        this.faceAngle = faceAngle;
    }

    public boolean isCollision() {
        return isCollision;
    }

    public void setCollision(boolean collision) {
        isCollision = collision;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isReborn() {
        return isReborn;
    }

    public void setReborn(boolean isRebron) {
        this.isReborn = isRebron;
    }

    public long getRebornTime() {
        return rebornTime;
    }

    public void setRebornTime(long rebornTime) {
        this.rebornTime = rebornTime;
    }
}
