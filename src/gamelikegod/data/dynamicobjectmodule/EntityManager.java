package gamelikegod.data.dynamicobjectmodule;

import java.util.ArrayList;

import javax.management.InstanceAlreadyExistsException;

import gamelikegod.core.rain.entity.Entity;
import gamelikegod.core.rain.entity.mob.Player;

public class EntityManager {

	private static EntityManager instance = null;
	private ArrayList<Entity> allDynamicObject = new ArrayList<Entity>();
	private Player virtualCharacter;

	private EntityManager() {

	}

	public static EntityManager getInstance() {
		if (instance == null) {
			instance = new EntityManager();
		}
		return instance;
	}

	public void addVirtualCharacter(int clientno) {
		// virtualCharacter = new Player(x,y);
	}

	public void addItem(String name, int index, boolean shared) {

	}

	public void updateVirtualCharacter(int clientno, int dir, int speed, int x, int y) {
		virtualCharacter.x = x;
		virtualCharacter.y = y;
	}

	public void updateItem(int index, boolean shared, int owner) {

	}

	public ArrayList<Entity> getAllDynamicObjects() {
		return allDynamicObject;
	}

	int count = 0;
	int xp = 0, yp = 0;

	public int[] getVirtualCharacterXY() {
		count++;
		if (count % 100 == 1) {
			xp++;
			yp++;
		}

		int[] position = new int[2];
		position[0] = xp;
		position[1] = yp;

		return position;
	}

	public void getKeyPressed() {
		// TODO: check if the GET action is possible by comparing the virtual
		// character's position and any item nearby.
		// if possible, it should call inputMoves(GET) of TCPCM
	}

}
