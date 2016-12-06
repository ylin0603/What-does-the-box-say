package CDC;

import java.util.ArrayList;


public class Cdc implements Runnable {
	
	public ArrayList<ClientPlayerFeature> allPlayers = new ArrayList<>();
	public ArrayList<ClientItemFeature> allItems = new ArrayList<>();
	int West = 1;
	int East = 2;
	int South = 3;
	int North = 4;
	Thread game = new Thread(this);
	public void Cdc(){
		
	}

	public void addVirtualCharacter(int clientno){
			assert clientno > 0;
			allPlayers.add(new ClientPlayerFeature(clientno));
	}
	
	public void addItem(String name, int index, Boolean shared, int x, int y){
		assert name != null;
		assert index>0;
		assert x>0 && y>0;
		allItems.add(new ClientItemFeature(name,index,shared,x,y));
	}
	
	// called by TCPSM 
	// when TCPSM receives a MoveCode which is “TURN” from TCPCM, 
	// it call this function to change the moving direction of virtual character of clientno
	public void updateDirection(int clientno, int MoveCode){
		assert clientno > 0;
		assert MoveCode > 0;
		assert MoveCode < 5;
		allPlayers.get(clientno).direction = MoveCode;
	}
	
	// called by TCPSM
	// when TCPSM receives a MoveCode which is a “GET”, TCPSM calls this method.
	// This method should check if there is an item ahead of the virtual character 
	// clientno’s direction and if the item is within reaching range = 0. 
	// If the item is within reaching range, check if the item is a shared object.
	// If it is a shared object, check if it is already owned by any virtual character.
	// Finally, change the states of the item accordingly.
	public void getItem(int clientno){
		assert clientno>0;
		int playerLocationX = allPlayers.get(clientno).locationX;
		int playerLocationY = allPlayers.get(clientno).locationY;
		int itemsNum = allItems.size();
		for(int currentItems = 0; currentItems < itemsNum; currentItems++){ 
			//check direction and range (0?)
			int currentItemLocationX = allItems.get(currentItems).locationX;
			int currentItemLocationY = allItems.get(currentItems).locationY;
			boolean currentItemIsShared = allItems.get(currentItems).isShared;
			boolean currentItemIsNotOwned = !allItems.get(currentItems).isOwned;
			if(Math.abs(playerLocationX - currentItemLocationX) <= 1 && 
					Math.abs(playerLocationY - currentItemLocationY) <= 1)
			{	//check item is shered or not
				if(currentItemIsShared && currentItemIsNotOwned){
					allItems.get(currentItems).isOwned = true;
				}
			}
			
		}
	}
	
	// called by UDPBC 
	// The method will return a vector, which contains all the references
	// to the dynamic objects (virtual character and item) which has just been updated recently
	public ArrayList<ClientPlayerFeature> getPlayersUpdateInfo(){
		return allPlayers;
	}
	public ArrayList<ClientItemFeature> getItemsUpdateInfo(){
		return allItems;
	}
	
	// called by TCPSM, after all the connections are established and the game is started
	// this method start the following thread to update each virtual character’s x,y
	// every 0.5 second
	public void startUpdatingThread(){
		game.run();
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
				
			for(int currentUpdate = 0; currentUpdate < allPlayers.size(); currentUpdate ++){
				int currentPlayerDir = allPlayers.get(currentUpdate).direction;
				int currentPlayerLocationX = allPlayers.get(currentUpdate).locationX;
				int currentPlayerLocationY = allPlayers.get(currentUpdate).locationY;
				int currentPlayerVel = allPlayers.get(currentUpdate).velocity;
				if (currentPlayerDir == West){ 
					allPlayers.get(currentUpdate).locationX = 
							currentPlayerLocationX - currentPlayerVel/2;
				}
				else if (currentPlayerDir == East){ 
					allPlayers.get(currentUpdate).locationX = 
							currentPlayerLocationX + currentPlayerVel/2;
				}
				else if (currentPlayerDir == South){ 
					allPlayers.get(currentUpdate).locationY = 
							currentPlayerLocationY + currentPlayerVel/2;
				}
				else if (currentPlayerDir == North){ 
					allPlayers.get(currentUpdate).locationY = 
							currentPlayerLocationY - currentPlayerVel/2;
				}
			}
				
		}
	}
	
	
}
