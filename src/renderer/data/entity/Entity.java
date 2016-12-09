package renderer.data.entity;

import pseudomain.Game;
import renderer.data.DynamicObjectManager;
import renderer.graphics.Sprite;

import java.util.Map;
import java.util.Random;

/**
 * Created by tsunglin on 2016/12/2.
 */
public abstract class Entity {
    public int x, y;
    private boolean removed = false;
    protected Sprite sprite;

    public void update() {

    }

    public void render(int pixels[]) {
        int offsetX = DynamicObjectManager.getInstance().getVirtualCharacterXY().x - Game.WIDTH / 2;
        int offsetY = DynamicObjectManager.getInstance().getVirtualCharacterXY().y - Game.HEIGHT / 2;
        int xp = this.x;
        int yp = this.y;
        xp -= offsetX;
        yp -= offsetY;
        for (int y = 0; y < sprite.SIZE; y++) {
            int ya = y + yp;
            for (int x = 0; x < sprite.SIZE; x++) {
                int xa = x + xp;
                if (xa < -sprite.SIZE || xa >= Game.WIDTH || ya < 0 || ya >= Game.HEIGHT) break;
                if (xa < 0) xa = 0;
                int color = sprite.pixels[x + y * sprite.SIZE];
                if (color != 0xffffffff) pixels[xa + ya * Game.WIDTH] = color;
            }
        }
    }

    public void render(int pixels[], double angle) {
        int offsetX = DynamicObjectManager.getInstance().getVirtualCharacterXY().x - Game.WIDTH / 2;
        int offsetY = DynamicObjectManager.getInstance().getVirtualCharacterXY().y - Game.HEIGHT / 2;
        int xp = this.x;
        int yp = this.y;
        xp -= offsetX;
        yp -= offsetY;
        for (int y = 0; y < sprite.SIZE; y++) {
            int ya = y + yp;
            for (int x = 0; x < sprite.SIZE; x++) {
                int xa = x + xp;
                if (xa < -sprite.SIZE || xa >= Game.WIDTH || ya < 0 || ya >= Game.HEIGHT) break;
                if (xa < 0) xa = 0;
                int[] rpixels = rotate(sprite.pixels, angle, sprite.SIZE, sprite.SIZE);
                int color = rpixels[x + y * sprite.SIZE];
                if (color != 0xffffffff) pixels[xa + ya * Game.WIDTH] = color;
            }
        }
    }

    private int[] rotate(int[] opixels, double angle, int width, int height) {
        int[] result = new int[width * height];
        double nx_x = rotateX(-angle,1.0,0.0);
        double nx_y = rotateY(-angle,1.0,0.0);
        double ny_x = rotateX(-angle,0.0,1.0);
        double ny_y = rotateY(-angle,0.0,1.0);

        double x0 = rotateX(-angle, -width/2, -height/2) + width/2;
        double y0 = rotateX(-angle, -width/2, -height/2) + height/2;

        for(int y=0; y<height; y++){
            double x1 = x0;
            double y1 = y0;
            for(int x=0; x<width; x++){
                int xx = (int) x1;
                int yy = (int) y1;
                int col;
                if(xx <0 || xx >= width || yy<0 || yy>=height){
                    col=0xffffffff;
                } else {
                    col = opixels[xx+yy*width];
                }

                x1 += nx_x;
                y1 += nx_y;
            }
            x0 += ny_x;
            y0 += ny_y;
        }
        return result;
    }

    private double rotateX(double angle, double x, double y) {
        double cos = Math.cos(angle - Math.PI / 2);
        double sin = Math.sin(angle - Math.PI / 2);

        return x * cos + y * -sin;
    }

    private double rotateY(double angle, double x, double y) {
        double cos = Math.cos(angle- Math.PI / 2);
        double sin = Math.sin(angle- Math.PI / 2);

        return x * sin + y * cos;
    }

    public void remove() {
        //TODO:: remove from level
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }
}