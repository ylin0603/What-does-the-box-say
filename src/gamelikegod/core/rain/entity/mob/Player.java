package gamelikegod.core.rain.entity.mob;

import gamelikegod.core.graphics.Screen;
import gamelikegod.core.graphics.Sprite;
import gamelikegod.core.input.Keyboard;
import gamelikegod.core.rain.level.TileCoordinate;

public class Player extends Mob {
	private Sprite curSprite;
	private Keyboard input;
	private int anim = 0;
	boolean walking = false;

	public Player(Keyboard input) {
		this.input = input;
		sprite = sprite.PLAYER_BACKWARD[0];
	}

	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
	}

	private void spriteDet() {
		Sprite[] sprites = null;
		switch (dir) {
		case NORTH:
			sprites = Sprite.PLAYER_FORWARD;
			break;
		case SOUTH:
			sprites = Sprite.PLAYER_BACKWARD;
			break;
		case EAST:
			sprites = Sprite.PLAYER_RIGHT;
			break;
		case WEST:
			sprites = Sprite.PLAYER_LEFT;
			break;
		default:
			System.err.println("Error Direction in Player.class");
			break;
		}
		if (walking) {
			if (anim % 20 > 10) {
				sprite = sprites[1];
			} else {
				sprite = sprites[2];
			}
		} else{
			sprite = sprites[0];
		}
	}

	public void update() {
		int xa = 0, ya = 0;
		if (anim < 75000)
			anim++;
		else
			anim = 0;
		if (input.up) ya--;
		if (input.down) ya++;
		if (input.left) xa--;
		if (input.right) xa++;

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}

	}

	public void render(Screen screen) {
		spriteDet();
		screen.renderPlayer(x, y, sprite);
	}

}
