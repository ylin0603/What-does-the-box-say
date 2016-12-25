package renderer.data.entity;

import renderer.graphics.Sprite;

/**
 * Created by TsunglinYang on 2016/12/21.
 */
public class BloodPackage extends Item {
    public BloodPackage(int type, int index, boolean isDead, int x, int y) {
        super(type, index, isDead, x, y, Sprite.BLOOD_PACKAGE );
    }
}
