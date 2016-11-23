package gamelikegod.core.rain.entity;

import java.util.Random;

import gamelikegod.core.graphics.Screen;
import gamelikegod.core.rain.level.Level;

public abstract class Entity {
	public int x,y;
	private boolean removed = false;
	protected final Random random = new Random();
	
	public void update() {
		
	}
	public void render (Screen screen) {
		
	}
	
	public void remove() {
		//TODO:: remove from level
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
}
