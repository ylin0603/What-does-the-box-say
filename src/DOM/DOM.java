package DOM;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

public class DOM {
	public ArrayList<Character> character = new ArrayList<>();
	public Vector<Item> item = new Vector<>();

	public void addVirtualCharacter(int clientno) {

		this.character.add(new Character(clientno));
	}

	public void addItem(String name, int index, Boolean shared) {

		this.item.add(new Item(name, index, shared));
	}

	public void updateVirtualCharacter(int clientno, int dir, int speed, int x, int y) {

		Character character = this.character.get(clientno);
		character.x = x;
		character.y = y;
		character.dir = dir;
		character.speed = speed;
	}

	public void updateItem(int index, Boolean shared, int owner) {

		Item item = this.item.get(index);
		item.shared = shared;
		item.owner = owner;
	}

	public Vector getAllDynamicObjects() {

		return item;
	}

	public Point getVirtualCharacterXY(int clientno) {

		Character character = this.character.get(clientno);
		return new Point(character.x, character.y);
	}

	public void keyGETPressed() {

	}

}
