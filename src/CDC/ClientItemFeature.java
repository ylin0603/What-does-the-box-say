package CDC;

import java.util.ArrayList;

public class ClientItemFeature {
	
	String type = "";
	int itemIndex;
	int locationX;
	int locationY;
	boolean isShared;
	boolean isOwned;
	
	public ClientItemFeature(String name, int index, boolean shared, int x, int y)
	{
		type = name;
		itemIndex = index;
		locationX = x;
		locationY = y;
		isShared = shared;
	}
	
	/*public String toString(){
		String passString;
		passString = "kind" + type + "index" + itemIndex + "x:" + locationX + "y:" + locationY + "shared:" + isShared;
		return passString;
	}*/
	
}
