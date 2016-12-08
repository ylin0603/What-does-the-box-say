package CDC;

public class ClientItemFeature {

	private int itemIndex;
	private int itemOwner;
	private int locationX, locationY;
	private boolean isShared, isOwned;
	private String type = "";

	public ClientItemFeature(String name, int index, boolean shared, int x, int y)
	{
		this.type = name;
		this.itemIndex = index;
		this.locationX = x;
		this.locationY = y;
		this.isShared = shared;
	}

	public int getItemIndex() {
		return itemIndex;
	}

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	public boolean isShared() {
		return isShared;
	}

	public void setShared(boolean shared) {
		isShared = shared;
	}

	public boolean isOwned() {
		return isOwned;
	}

	public void setOwned(boolean owned) {
		isOwned = owned;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getItemOwner() {
		return itemOwner;
	}

	public void setItemOwner(int itemOwner) {
		this.itemOwner = itemOwner;
	}
}
