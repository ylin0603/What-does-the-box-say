package CDC;

import udp.broadcast.client.UDP_Client;

import java.util.ArrayList;

public class Cdc implements Runnable {
	private ArrayList<ClientPlayerFeature> allPlayers = new ArrayList<>();
	private ArrayList<ClientItemFeature> allItems = new ArrayList<>();
	
	private final static int East = 0;
	private final static int South = 1;
	private final static int North = 2;
	private final static int West = 3;

	private static Cdc instance = null;

	public static void main(String[] args) {
		//test and temp
		getInstance().startUpdatingThread();
		getInstance().addVirtualCharacter(0);
		getInstance().addVirtualCharacter(1);
		new UDP_Client().startUDPBroadCast();
	}

	public void Cdc(){}

	public static Cdc getInstance() {
		if (instance == null) instance = new Cdc();
		return instance;
	}

	public void addVirtualCharacter(int clientNo){
        assert clientNo > -1;

		allPlayers.add(new ClientPlayerFeature(clientNo));
	}
	
	public void addItem(String name, int index, Boolean shared, int x, int y){
		assert name != null;
		assert index > -1;
		assert x > 0 && y > 0;

		allItems.add(new ClientItemFeature(name, index, shared, x, y));
	}

	public void updateDirection(int clientNo, int moveCode){
		assert clientNo > -1;
		assert moveCode > -1 && moveCode < 4;

		allPlayers.get(clientNo).setDirection(moveCode);
	}
	
	public void getItem(int clientNo){
		assert clientNo > -1;
		ClientPlayerFeature player = allPlayers.get(clientNo);
		int playerX = player.getLocationX();
		int playerY = player.getLocationY();
		int itemsSize = allItems.size();
		for(int i = 0; i < itemsSize; i++){
            ClientItemFeature item = allItems.get(i);
			if(Math.abs(playerX - item.getLocationX()) <= 1
                    && Math.abs(playerY - item.getLocationY()) <= 1){
                if(item.isShared() && !item.isOwned()) {
					item.setOwned(true);
					item.setItemOwner(clientNo);
				}
            }
		}
	}
	
	public ArrayList<ClientPlayerFeature> getPlayersUpdateInfo(){
		return allPlayers;
	}
	public ArrayList<ClientItemFeature> getItemsUpdateInfo(){
		return allItems;
	}
	
	public void startUpdatingThread(){
	    Thread game = new Thread(instance);
		game.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				Thread.sleep(500);
			} catch (InterruptedException e){
				e.printStackTrace();
            }

            int playerSize = allPlayers.size();
            for(int i = 0; i < playerSize; i++) {
                ClientPlayerFeature player = allPlayers.get(i);
                int currPlayerX = player.getLocationX();
                int currPlayerY = player.getLocationY();
                int currPlayerVel = player.getVelocity();

                switch(player.getDirection()) {
                    case West:
                        player.setLocationX(currPlayerX - currPlayerVel/2);
                        break;
                    case East:
                        player.setLocationX(currPlayerX + currPlayerVel/2);
                        break;
                    case South:
                        player.setLocationY(currPlayerY + currPlayerVel/2);
                        break;
                    case North:
                        player.setLocationY(currPlayerY - currPlayerVel/2);
                        break;
                    default:
                        throw new Error("Out of direction!");
                }
            }
		}
	}
}
