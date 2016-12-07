package CDC;

public class ClientPlayerFeature {

	private int playerId;
	private int locationX, locationY;
	private int direction = 0;
	private int velocity = 2;

	public ClientPlayerFeature(int clientNo){
		this.playerId = clientNo;
	}

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getPlayerId() {
        return playerId;
    }
    public int getLocationX() {
		return locationX;

	}
	public int getLocationY() {
		return locationY;
	}
	public int getDirection() {
		return direction;
	}
	public int getVelocity() {
		return velocity;
	}
}
