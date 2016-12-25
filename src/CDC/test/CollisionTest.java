package CDC.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import CDC.ClientItemFeature;
import CDC.ClientPlayerFeature;
import CDC.Collision;

public class CollisionTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testIsCollisonClientPlayerFeatureClientPlayerFeature() {
        ClientPlayerFeature p1 = new ClientPlayerFeature(0, 0, 0);
        ClientPlayerFeature p2;

        p2 = new ClientPlayerFeature(1, 0, 0);
        assert Collision.isCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, 0, 8);
        assert Collision.isCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, 0, 16);
        assert Collision.isCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, 0, 19);
        assert Collision.isCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, 0, 20);
        assert !Collision.isCollison(p1, p2);
    }

    @Test
    public void testIsCollisonClientItemFeatureClientPlayerFeature() {
        ClientItemFeature i1 = new ClientItemFeature(0, 0, 0, 0);

        ClientPlayerFeature p2;
        p2 = new ClientPlayerFeature(1, 0, 0);
        assert Collision.isCollison(i1, p2);
        p2 = new ClientPlayerFeature(1, 0, 8);
        assert Collision.isCollison(i1, p2);
        p2 = new ClientPlayerFeature(1, 0, 16);
        assert Collision.isCollison(i1, p2);
        p2 = new ClientPlayerFeature(1, 0, 19);
        assert Collision.isCollison(i1, p2);
        p2 = new ClientPlayerFeature(1, 0, 20);
        assert !Collision.isCollison(i1, p2);
    }

    @Test
    public void testIsCollisonIntIntClientPlayerFeature() {
        ClientPlayerFeature p2;
        p2 = new ClientPlayerFeature(1, 0, 0);
        assert Collision.isCollison(0, 0, p2);
        p2 = new ClientPlayerFeature(1, 0, 8);
        assert Collision.isCollison(0, 0, p2);
        p2 = new ClientPlayerFeature(1, 0, 16);
        assert Collision.isCollison(0, 0, p2);
        p2 = new ClientPlayerFeature(1, 0, 19);
        assert Collision.isCollison(0, 0, p2);
        p2 = new ClientPlayerFeature(1, 0, 20);
        assert !Collision.isCollison(0, 0, p2);
    }

    @Test
    public void testIsCollisonClientItemFeatureClientItemFeature() {
        ClientItemFeature i1 = new ClientItemFeature(0, 0, 0, 0);
        ClientItemFeature i2;

        i2 = new ClientItemFeature(1, 0, 0, 0);
        assert Collision.isCollison(i1, i2);
        i2 = new ClientItemFeature(1, 0, 0, 8);
        assert Collision.isCollison(i1, i2);
        i2 = new ClientItemFeature(1, 0, 0, 16);
        assert Collision.isCollison(i1, i2);
        i2 = new ClientItemFeature(1, 0, 0, 19);
        assert Collision.isCollison(i1, i2);
        i2 = new ClientItemFeature(1, 0, 0, 20);
        assert !Collision.isCollison(i1, i2);
    }

    @Test
    public void testIsCollisonIntIntClientItemFeature() {
        ClientItemFeature i2;
        i2 = new ClientItemFeature(1, 0, 0, 0);
        assert Collision.isCollison(0, 0, i2);
        i2 = new ClientItemFeature(1, 0, 0, 8);
        assert Collision.isCollison(0, 0, i2);
        i2 = new ClientItemFeature(1, 0, 0, 16);
        assert Collision.isCollison(0, 0, i2);
        i2 = new ClientItemFeature(1, 0, 0, 19);
        assert Collision.isCollison(0, 0, i2);
        i2 = new ClientItemFeature(1, 0, 0, 20);
        assert !Collision.isCollison(0, 0, i2);
    }

    @Test
    public void testIsCollisonIntIntIntInt() {
        assert Collision.isCollison(0, 0, 0, 0);
        assert Collision.isCollison(0, 0, 0, 8);
        assert Collision.isCollison(0, 0, 0, 16);
        assert Collision.isCollison(0, 0, 0, 19);
        assert !Collision.isCollison(0, 0, 0, 20);
        assert Collision.isCollison(0, 0, 1, 1);
        assert Collision.isCollison(0, 0, 13, 13);
        assert !Collision.isCollison(0, 0, 14, 14);
        assert !Collision.isCollison(0, 0, 15, 15);
        assert !Collision.isCollison(0, 0, 16, 16);
        assert Collision.isCollison(1, 1, 0, 0);
        assert Collision.isCollison(13, 13, 0, 0);
        assert !Collision.isCollison(14, 14, 0, 0);
        assert !Collision.isCollison(15, 15, 0, 0);
        assert !Collision.isCollison(16, 16, 0, 0);
    }

    @Test
    public void testIsCollisonIntIntIntClientPlayerFeature() {
        ClientPlayerFeature p1 = new ClientPlayerFeature(0, 0, 0);

        assert Collision.isCollison(0, 0, 8, p1);
        assert Collision.isCollison(0, 8, 8, p1);
        assert Collision.isCollison(0, 16, 8, p1);
        assert Collision.isCollison(0, 19, 8, p1);
        assert !Collision.isCollison(0, 20, 8, p1);

        assert Collision.isCollison(0, 0, 24, p1);
        assert Collision.isCollison(0, 8, 24, p1);
        assert Collision.isCollison(0, 16, 24, p1);
        assert Collision.isCollison(0, 24, 24, p1);
        assert Collision.isCollison(0, 32, 24, p1);
        assert Collision.isCollison(0, 36, 24, p1);
        assert Collision.isCollison(0, 38, 24, p1);
        assert !Collision.isCollison(0, 39, 24, p1);
        assert !Collision.isCollison(0, 40, 24, p1);


        assert Collision.isCollison(36, 0, 24, p1);
        assert Collision.isCollison(38, 0, 24, p1);
        assert !Collision.isCollison(39, 0, 24, p1);
        assert !Collision.isCollison(40, 0, 24, p1);
    }

    @Test
    public void testDistance1d() {
        assertEquals(Collision.distance1d(0, 0), 0);
        assertEquals(Collision.distance1d(20, 30), 10);
        assertNotEquals(Collision.distance1d(20, 30), 40);
    }

    @Test
    public void testDistance2d() {
        assertEquals(Double.compare(Collision.distance2d(0, 0, 6, 8), 100), 0);
        assertEquals(Double.compare(Collision.distance2d(0, 0, 5, 12), 169), 0);
    }

}
