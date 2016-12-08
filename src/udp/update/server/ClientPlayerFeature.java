package udp.update.server;

public class ClientPlayerFeature {

	private int playerId;
	private int locationX, locationY;
	private int direction = 0;
	private int velocity = 0;

	public ClientPlayerFeature(int clientNo){
		this.playerId = clientNo;
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
