package gamelikegod.core.rain.entity.mob;

import gamelikegod.core.graphics.Sprite;
import gamelikegod.core.rain.entity.Entity;

public abstract class Mob extends Entity {
	public enum Direction {
		EAST, WEST, SOUTH, NORTH
	}

	protected Sprite sprite;
	protected Direction dir = Direction.NORTH;
	protected boolean moving = false;

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}

		if (xa > 0) dir = Direction.EAST;
		if (xa < 0) dir = Direction.WEST;
		if (ya > 0) dir = Direction.SOUTH;
		if (ya < 0) dir = Direction.NORTH;

		if (!collision(xa, ya)) {
			x += xa;
			y += ya;
		}
	}

	public void update() {

	}

	private boolean collision(int xa, int ya) {
		boolean colide = false;
		for (int c = 0; c < 4; c++) {
			// tune up the four corner of entity
			// (0,0) (1,0) (0,1) (1,1) respectively
			int xt = ((x + xa) + c % 2 * 7 + 4) / sprite.SIZE;
			int yt = ((y + ya) + c / 2 * 9 + 5) / sprite.SIZE;
			if (level.getTile(xt, yt).solid()) colide = true;
		}
		return colide;
	}

	public void render() {

	}

}
