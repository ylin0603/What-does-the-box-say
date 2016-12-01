package renderer.graphics;

public class Sprite {
	public final int SIZE;
	private int sheetX, sheetY;
	public int[] pixels;
	private SpriteSheet sheet;
	

	public static Sprite GRASS = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite STONE = new Sprite(16, 0, 2, SpriteSheet.tiles);
	public static Sprite TREE = new Sprite(16, 0, 3, SpriteSheet.tiles);
	public static Sprite VOID_SPRITE = new Sprite(16, 0x1B87E0);
	
	//Spawn Level Sprites here:
	public static Sprite SPWAN_GRASS = new Sprite(16,0,0, SpriteSheet.SPWAN_LEVEL);
	public static Sprite SPWAN_GRASS_2 = new Sprite(16,1,0, SpriteSheet.SPWAN_LEVEL);
	public static Sprite SPWAN_HEDGE = new Sprite(16,0,1, SpriteSheet.SPWAN_LEVEL);
	public static Sprite SPWAN_WATER = new Sprite(16,2,2, SpriteSheet.SPWAN_LEVEL);
	public static Sprite SPWAN_WALL = new Sprite(16,1,1, SpriteSheet.SPWAN_LEVEL);
	public static Sprite SPWAN_FLOOR = new Sprite(16,2,1, SpriteSheet.SPWAN_LEVEL);
	
	// below is for animation, dear StanleyLin.
	/*public static Sprite[] PLAYER_FORWARD = {
			new Sprite(16, 1, 3, SpriteSheet.character),
			new Sprite(16, 0, 3, SpriteSheet.character),
			new Sprite(16, 2, 3, SpriteSheet.character),
	};
	
	public static Sprite PLAYER_BACKWARD[] = {
			new Sprite(16, 1, 0, SpriteSheet.character),
			new Sprite(16, 0, 0, SpriteSheet.character),
			new Sprite(16, 2, 0, SpriteSheet.character),
	};
	public static Sprite PLAYER_LEFT[] = {
			new Sprite(16, 1, 1, SpriteSheet.character),
			new Sprite(16, 0, 1, SpriteSheet.character),
			new Sprite(16, 2, 1, SpriteSheet.character),
	};
	public static Sprite PLAYER_RIGHT[] = {
			new Sprite(16, 1, 2, SpriteSheet.character),
			new Sprite(16, 0, 2, SpriteSheet.character),
			new Sprite(16, 2, 2, SpriteSheet.character),
			
	};*/

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
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}

	private void setColour(int colour) {
		for (int i = 0; i < SIZE * SIZE; i++) {
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
