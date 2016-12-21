package renderer.graphics;

public class Sprite {
    public final int SIZE;
    private int sheetX, sheetY;
    public int[] pixels;
    private SpriteSheet sheet;

    public static Sprite EMPTY = new Sprite(32, 0xffffffff);

    public static Sprite GRASS = new Sprite(16, 0, 1, SpriteSheet.tiles);
    public static Sprite STONE = new Sprite(16, 0, 2, SpriteSheet.tiles);
    public static Sprite TREE = new Sprite(16, 0, 3, SpriteSheet.tiles);
    public static Sprite VOID_SPRITE = new Sprite(16, 0x1B87E0);

    //for demo
    public static Sprite BLOOD_PACKAGE = new Sprite(16, 4, 0, SpriteSheet.character);
    public static Sprite AMMO_PACKAGE = new Sprite(16, 3, 0, SpriteSheet.character);
    public static Sprite PLAYER = new Sprite(16, 1, 0, SpriteSheet.character);

    //Spawn Level Sprites here:
    public static Sprite SPWAN_GRASS = new Sprite(16, 0, 0, SpriteSheet.SPWAN_LEVEL);
    public static Sprite SPWAN_GRASS_2 = new Sprite(16, 1, 0, SpriteSheet.SPWAN_LEVEL);
    public static Sprite SPWAN_HEDGE = new Sprite(16, 0, 1, SpriteSheet.SPWAN_LEVEL);
    public static Sprite SPWAN_WATER = new Sprite(16, 2, 2, SpriteSheet.SPWAN_LEVEL);
    public static Sprite SPWAN_WALL = new Sprite(16, 1, 1, SpriteSheet.SPWAN_LEVEL);
    public static Sprite SPWAN_FLOOR = new Sprite(16, 2, 1, SpriteSheet.SPWAN_LEVEL);

    // below is for animation, dear StanleyLin.

    public static Sprite[] SWORD_UPWARD_ATTACK = {
            new Sprite(32, 4, 5, SpriteSheet.character),
            new Sprite(32, 5, 5, SpriteSheet.character),
            new Sprite(32, 6, 5, SpriteSheet.character),
            new Sprite(32, 7, 5, SpriteSheet.character),
            new Sprite(32, 0, 4, SpriteSheet.character),
    };

    public static Sprite SWORD_RIGHT_ATTACK[] = {
            new Sprite(32, 0, 4, SpriteSheet.character),
            new Sprite(32, 1, 4, SpriteSheet.character),
            new Sprite(32, 2, 4, SpriteSheet.character),
            new Sprite(32, 3, 4, SpriteSheet.character),
            new Sprite(32, 4, 4, SpriteSheet.character),
    };
    public static Sprite SWORD_DOWNWARD_ATTCK[] = {
            new Sprite(32, 4, 4, SpriteSheet.character),
            new Sprite(32, 5, 4, SpriteSheet.character),
            new Sprite(32, 6, 4, SpriteSheet.character),
            new Sprite(32, 7, 4, SpriteSheet.character),
            new Sprite(32, 0, 5, SpriteSheet.character),
    };
    public static Sprite SWORD_LEFT_ATTACK[] = {
            new Sprite(32, 0, 5, SpriteSheet.character),
            new Sprite(32, 1, 5, SpriteSheet.character),
            new Sprite(32, 2, 5, SpriteSheet.character),
            new Sprite(32, 3, 5, SpriteSheet.character),
            new Sprite(32, 4, 5, SpriteSheet.character),

    };


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

    public Sprite(int[] pixels, int size) {
        this.SIZE = size;
        this.pixels = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            this.pixels[i] = pixels[i];
        }
    }

    public static Sprite rotate(Sprite sprite, double angle) {
        return new Sprite(rotate(sprite.pixels, angle, sprite.SIZE, sprite.SIZE), sprite.SIZE);
    }

    // below are ratation class
    private static int[] rotate(int[] opixels, double angle, int width, int height) {
        int[] result = new int[width * height];
        double nx_x = rotateX(-angle, 1.0, 0.0);
        double nx_y = rotateY(-angle, 1.0, 0.0);
        double ny_x = rotateX(-angle, 0.0, 1.0);
        double ny_y = rotateY(-angle, 0.0, 1.0);

        double x0 = rotateX(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
        double y0 = rotateY(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

        for (int y = 0; y < height; y++) {
            double x1 = x0;
            double y1 = y0;
            for (int x = 0; x < width; x++) {
                int xx = (int) x1;
                int yy = (int) y1;
                int col;
                if (xx < 0 || xx >= width || yy < 0 || yy >= height) {
                    col = 0xffffffff;
                } else {
                    col = opixels[xx + yy * width];
                }
                result[x + y * width] = col;
                x1 += nx_x;
                y1 += nx_y;
            }
            x0 += ny_x;
            y0 += ny_y;
        }
        return result;
    }

    private static double rotateX(double angle, double x, double y) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        return x * cos + y * -sin;
    }

    private static double rotateY(double angle, double x, double y) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        return x * sin + y * cos;
    }

    // --------------------------------
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
