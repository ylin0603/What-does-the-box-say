package cdc;

import java.util.Vector;

public class CentralizedDataCenter {
	
	private static Vector<VirtualCharacter> characters 
			= new Vector<VirtualCharacter>();
	private static Vector<Item> items = new Vector<Item>();

	public static void addVirtualCharacter(int clientno) {
		characters.addElement(new VirtualCharacter(clientno));
	}
	
	public static void addItem(String name, int index, boolean shared,int x, int y) {
		items.addElement(new Item(name, index, shared, x, y));
	}
	
	public static void updateDirection(int clientno, int MoveCode) {
		characters.get(clientno).changeDirection(MoveCode);
	}
	
	public static void getItem(int clientno) {
		VirtualCharacter character = characters.get(clientno);
		int nextX = character.getX();
		int nextY = character.getY();
		
		switch(character.getDirection())
		{
		case 0: // NORTH
			nextY -= 2;
		case 1: // SOUTH
			nextY += 2;
		case 2: // WEST
			nextX -= 2;
		case 3: // EAST
			nextX += 2;
		default:
		}
		
		for(Item item: items) {
			if(nextX==item.getX() && nextY==item.getY()) {
				if(item.shared && !item.owned)
					item.owned = true;
			}
		}
	}
	
	public static Vector<Object> getUpdateInfo() {
		Vector<Object> all = new Vector<Object>();
		all.addAll(characters);
		all.addAll(items);
		return all;
	}
	
	public static void startUpdatingThread() {
		Thread updatingThread = new Thread(new updating());
		updatingThread.start();
	}
	
	public static class updating implements Runnable {
		
		@Override
		public void run() {
			try {
				while(true) {
					Thread.sleep(500);
					for(VirtualCharacter character: characters) {
						int x = character.getX();
						int y = character.getY();
						switch(character.getDirection())
						{
						case 0: // NORTH
							y -= character.getSpeed();
						case 1: // SOUTH
							y += character.getSpeed();
						case 2: // WEST
							x -= character.getSpeed();
						case 3: // EAST
							x += character.getSpeed();
						default:
						}
						
						character.changePosition(x, y);
					}
				}
			} catch (Exception ex) {
				System.out.println("Something wrong~");
			}
		}
	}
}
