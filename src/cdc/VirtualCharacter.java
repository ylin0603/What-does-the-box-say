package cdc;

import java.util.Random;

public class VirtualCharacter {

	private int clientno;
	private int x,y; //current position
	private int dir = 0; //direction the virtual character is heading
	private int velocity = 0; //the moving speed
	
	public VirtualCharacter(int clientno) {
		this.clientno = clientno;
		initialize();
	}
	
	private void initialize() {
		Random ran = new Random();
		this.x = ran.nextInt(600);
		this.y = ran.nextInt(600);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDirection() {
		return dir;
	}
	
	public int getSpeed() {
		return velocity;
	}
	
	public void changePosition(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	public void changeDirection(int newDirection) {
		dir = newDirection;
	}
	
	public void changeSpeed(int newVelocity) {
		velocity = newVelocity;
	}
	
	public String toString() {
		return "client: "+clientno+" x: "+x+" y: "+y+" dir: "+dir
				+" velocity: "+velocity;
	}
}
