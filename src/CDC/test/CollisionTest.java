package CDC.test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import CDC.ClientPlayerFeature;
import CDC.Collision;

public class CollisionTest {
    Collision collision;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {
        collision = new Collision();
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testIsPlayersCollison() {
        ClientPlayerFeature p1 = new ClientPlayerFeature(0, "aaa", 0, 0);

        ClientPlayerFeature p2 = new ClientPlayerFeature(1, "bbb", 0, 0);

        assert Collision.isCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, "bbb", 0, 8);
        assert Collision.isCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, "bbb", 0, 16);
        assert Collision.isCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, "bbb", 0, 19);
        assert Collision.isCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, "bbb", 0, 20);
        assert !Collision.isCollison(p1, p2);
    }

    @Test
    public void testIsCirclePlayerCollison() {
        ClientPlayerFeature p1 = new ClientPlayerFeature(0, "aaa", 0, 0);


       
    }

    @Test
    public void testDistance1d() {
        assertEquals(10, Collision.distance1d(20, 30));
    }

    @Test
    public void testDistance2d() {
        assertEquals(100, (int) Collision.distance2d(0, 0, 6, 8));
    }
}
