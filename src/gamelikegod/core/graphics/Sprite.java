package gamelikegod.core.graphics;

import java.awt.Color;

public class Sprite {
	public final int SIZE;
	private int sheetX, sheetY;
	public int[] pixels;
	private SpriteSheet sheet;

	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16,0x1B87E0);

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.sheetX = x * size;
		this.sheetY = y * size;
		this.sheet = sheet;
		pixels = new int[SIZE * SIZE];
		load();
		
	}

	public Sprite(int size, int colour) {
		this.SIZE = size;
		pixels = new int[SIZE*SIZE];
		setColour(colour);
	}
	
	private void setColour(int colour) {
		for(int i=0; i<SIZE*SIZE; i++){
			pixels[i] = colour;
		}
	}

	private void load() {
		int[] sheetPixels = sheet.getPixels();

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheetPixels[(x + this.sheetX) + (y + this.sheetY) * sheet.SIZE];
			}
		}
	}
}
