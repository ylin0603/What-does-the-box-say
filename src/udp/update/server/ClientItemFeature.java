package udp.update.server;

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

	public int getItemOwner() {
		return itemOwner;
	}

	public int getLocationX() {
		return locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public boolean isShared() {
		return isShared;
	}

	public boolean isOwned() {
		return isOwned;
	}

	public String getType() {
		return type;
	}
}
