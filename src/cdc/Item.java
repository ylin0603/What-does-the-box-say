package cdc;

public class Item {

	private int x,y,index;
	private String name;
	public boolean shared, owned = false;
	
	public Item(String name, int index, boolean shared,int x, int y) {
		this.name = name;
		this.index = index;
		this.shared = shared;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return "name: "+name+" index: "+index+" x: "+x+" y: "+y
				+" shared: "+shared+" owned: "+owned;
	}
}
