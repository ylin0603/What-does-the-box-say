package CDC;

public class ClientPlayerFeature {

	private int playerId;
	private int locationX, locationY ,lastLocationX , lastLocationY;
	private int direction = 0;
	private int velocity = 2;
	private String nickName = null;
	private long lastMoveTime;

	public ClientPlayerFeature(int clientNo, String nickName) {
		this.playerId = clientNo;
		this.nickName = nickName;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
		if(checkStayStill()){
			checkRecover();
		}else{
			lastMoveTime = System.currentTimeMillis();
			lastLocationX = locationX;
		}
	}
	
	public void setLocationY(int locationY) {
		this.locationY = locationY;
		if(checkStayStill()){
			checkRecover();
		}else{
			lastMoveTime = System.currentTimeMillis();
			lastLocationX = locationX;
		}
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
	
	private boolean checkStayStill(){ //檢查是否停在原地
		if(locationX == lastLocationX && locationY == lastLocationY){
			return true;
		}else{
			return false;
		}
	}
	
	private void checkRecover() {
		long stopSecond = System.currentTimeMillis()- lastMoveTime;
		if(stopSecond > 5000){
			//Recoverd
		}
	}
}
