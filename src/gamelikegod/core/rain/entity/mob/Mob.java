package gamelikegod.core.rain.entity.mob;

import gamelikegod.core.graphics.Sprite;
import gamelikegod.core.rain.entity.Entity;

public abstract class Mob extends Entity {
	enum Direction {
		EAST,WEST,SOUTH,NORTH
	}
	protected Sprite sprite;
	protected Direction dir = Direction.NORTH;
	protected boolean moving = false;

	public void move(int xa, int ya) {
		if (xa > 0)	dir = Direction.EAST;
		if (xa < 0) dir = Direction.WEST;
		if (ya > 0)	dir = Direction.SOUTH;
		if (ya < 0) dir = Direction.NORTH;
		
		if (!collision()) {
			x += xa;
			y += ya;
		}
	}

	public void update() {

	}

	private boolean collision() {
		return false;
	}

	public void render() {

	}

}
