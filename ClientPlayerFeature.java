package CDC;

public class ClientPlayerFeature {
	
	int playerId;
	int locationX;
	int locationY;
	int direction = 0;
	int velocity = 0;
	public ClientPlayerFeature(int clientno){
		playerId=clientno;
	}
	
	public String toString(){
		//turn features to string
		String passString;
		passString = "id:" + playerId + "direction:" + direction + "velocity" + velocity + "x:" + locationX + "y:" + locationY;
		return passString;
	}
	

}
