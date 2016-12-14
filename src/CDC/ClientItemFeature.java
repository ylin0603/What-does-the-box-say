package CDC;

public class ClientItemFeature {
	private int itemID; // unique ID
	private int itemType; // 0: Fake Box, 1: Add HP, 2: Add Bullet, 3: Moving Bullet
	private int locX, locY;
	private boolean isCollision = false;
	private boolean isDead = false;
	private int itemOwner;

	public ClientItemFeature(int itemID, int itemType, int x, int y) {
		this.itemID = itemID;
		this.itemType = itemType;
		this.locX = x;
		this.locY = y;
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
