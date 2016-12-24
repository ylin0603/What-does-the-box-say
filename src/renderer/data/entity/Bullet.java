package renderer.data.entity;

/**
 * Created by tsunglin on 2016/12/24.
 */
public class Bullet extends Entity{
    private double angle;
    public Bullet(int x, int y, double faceAngle){
        super(Sprite.);
        this.x = x;
        this.y = y;
        this.angle = faceAngle;
    }

    @Override
    public void render(int[] pixels) {
        super.render(pixels);
    }
}
