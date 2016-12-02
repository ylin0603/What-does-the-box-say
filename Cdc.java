package CDC;

import java.util.ArrayList;
import java.util.Vector;


public class Cdc implements Runnable {
	
	public ArrayList<ClientPlayerFeature> allPlayers = new ArrayList<>();
	public ArrayList<ClientItemFeature> allItems = new ArrayList<>();
	Thread game = new Thread(this);
	public void Cdc(){
		
	}

	// A map is stored in the server, this map specifies the initial location of each virtual
	// character. We assume there are maximum four virtual characters in the maps 
	// So when the map is read, these initial locations are stored somewhere.
	// When a client connects to TCPSM, TCPSM should call this method to create a 
	// virtual character in the CDC. The initial location of this virtual character should use
	// the initial locations stored in the map.
	public void addVirtualCharacter(int clientno){
			assert clientno > 0;
			allPlayers.add(new ClientPlayerFeature(clientno));
	}
	
	// There is a map stored in the server. When the map is loaded, the map 
	// contains the information of all the items on the map. When these information is read
	// this method is called to create an shared item at position x,y
	// An item can be indexed by a name or an index.
	// If shared is true, the item can only be owned by a client at any time
	// If shared is false, the item can be obtained by any client as if it can reappear
	// after it is obtained by a virtual character (例如急救包)
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
	// clientno’s direction and if the item is within reaching range. 
	// If the item is within reaching range, check if the item is a shared object.
	// If it is a shared object, check if it is already owned by any virtual character.
	// Finally, change the states of the item accordingly.
	public void getItem(int clientno){
		assert clientno>0;
		for(int currentItems = 0; currentItems < allItems.size(); currentItems++){ //check every items in the map
			//check direction and range (0?)
			if(allPlayers.get(clientno).direction == 1 && allItems.get(currentItems).locationX == allPlayers.get(clientno).locationX)
			{	//check item is shered or not
				if(allItems.get(currentItems).isShared){
					//check item is owned or not
					if(allItems.get(currentItems).isOwned == false){
						allItems.get(currentItems).isOwned = true;
					}
				}
			}
			if(allPlayers.get(clientno).direction == 2 && allItems.get(currentItems).locationX == allPlayers.get(clientno).locationX)
			{
				if(allItems.get(currentItems).isShared){
					if(allItems.get(currentItems).isOwned == false){
						allItems.get(currentItems).isOwned = true;
					}
				}
			}
			if(allPlayers.get(clientno).direction == 3 && allItems.get(currentItems).locationY == allPlayers.get(clientno).locationY)
			{
				if(allItems.get(currentItems).isShared){
					if(allItems.get(currentItems).isOwned == false){
						allItems.get(currentItems).isOwned = true;
					}
				}
			}
			if(allPlayers.get(clientno).direction == 4 && allItems.get(currentItems).locationY == allPlayers.get(clientno).locationY)
			{
				if(allItems.get(currentItems).isShared){
					if(allItems.get(currentItems).isOwned == false){
						allItems.get(currentItems).isOwned = true;
					}
				}
			}
			
		}
	}
	
	// called by UDPBC 
	// The method will return a vector, which contains all the references
	// to the dynamic objects (virtual character and item) which has just been updated
	// recently
	// these object should contain a toString() method
	// when it is called, its attributes will be formatted into a string.
	public Vector getUpdateInfo(){
		Vector<ArrayList> infoVector = new Vector<ArrayList>();
		infoVector.add(allPlayers);
		infoVector.add(allItems);
		return infoVector;
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
				Thread.sleep(5000);
			} catch (InterruptedException e){
				e.printStackTrace();
				
			for(int currentUpdate = 0; currentUpdate < allPlayers.size(); currentUpdate ++){
				if (allPlayers.get(currentUpdate).direction == 1){ //direction 1 is left, x update to x-v/2
					allPlayers.get(currentUpdate).locationX = allPlayers.get(currentUpdate).locationX - allPlayers.get(currentUpdate).velocity/2;
				}
				if (allPlayers.get(currentUpdate).direction == 2){ //direction 2 is right, x update to x+v/2
					allPlayers.get(currentUpdate).locationX = allPlayers.get(currentUpdate).locationX + allPlayers.get(currentUpdate).velocity/2;
				}
				if (allPlayers.get(currentUpdate).direction == 3){ //direction 3 is down, y update to y-v/2
					allPlayers.get(currentUpdate).locationY = allPlayers.get(currentUpdate).locationY - allPlayers.get(currentUpdate).velocity/2;
				}
				if (allPlayers.get(currentUpdate).direction == 4){ //direction 4 is left, y update to y+v/2
					allPlayers.get(currentUpdate).locationY = allPlayers.get(currentUpdate).locationY + allPlayers.get(currentUpdate).velocity/2;
				}
			}
				
			}
		}
	}
	
}
