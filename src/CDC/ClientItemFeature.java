package CDC;

public class ClientItemFeature {
	private int itemID; // unique ID
    private int itemType;
    // 0: Fake Box, 1: Add HP, 2: Add Bullet, 3: Moving Bullet, 4: Moving Sword
	private int locX, locY;
    private int oriLocX, oriLocY;
    private double faceAngle = 0; // (degree) => use Math.toRadium();
	private boolean isCollision = false;
	private boolean isDead = false;
	private int itemOwner;

	public ClientItemFeature(int itemID, int itemType, int x, int y) {
		this.itemID = itemID;
		this.itemType = itemType;
		this.locX = x;
		this.locY = y;
        this.oriLocX = x;
        this.oriLocY = y;
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

	public int getItemOwner() {
		return itemOwner;
	}

	public void setItemOwner(int itemOwner) {
		this.itemOwner = itemOwner;
	}
}
