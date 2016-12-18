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

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testIsPlayersCollison() {
        ClientPlayerFeature p1 = new ClientPlayerFeature(0, "aaa", 0, 0);

        ClientPlayerFeature p2 = new ClientPlayerFeature(1, "bbb", 0, 0);
        Collision collision = new Collision();
        assert collision.isPlayersCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, "bbb", 0, 8);
        assert collision.isPlayersCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, "bbb", 0, 16);
        assert collision.isPlayersCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, "bbb", 0, 19);
        assert collision.isPlayersCollison(p1, p2);
        p2 = new ClientPlayerFeature(1, "bbb", 0, 20);
        assert !collision.isPlayersCollison(p1, p2);
    }

    @Test
    public void testIsCirclePlayerCollison() {
        ClientPlayerFeature p1 = new ClientPlayerFeature(0, "aaa", 0, 0);

        Collision collision = new Collision();
        assert collision.isCirclePlayerCollison(0, 0, 8, p1);
        assert collision.isCirclePlayerCollison(0, 8, 8, p1);
        assert collision.isCirclePlayerCollison(0, 16, 8, p1);
        assert collision.isCirclePlayerCollison(0, 19, 8, p1);
        assert !collision.isCirclePlayerCollison(0, 20, 8, p1);

        assert collision.isCirclePlayerCollison(0, 0, 8, p1);
        assert collision.isCirclePlayerCollison(0, 8, 8, p1);
        assert collision.isCirclePlayerCollison(0, 16, 8, p1);
        assert collision.isCirclePlayerCollison(0, 19, 8, p1);
        assert !collision.isCirclePlayerCollison(0, 20, 8, p1);
    }

    @Test
    public void testDistance1d() {

    }

    @Test
    public void testDistance2d() {
        // fail("Not yet implemented");
    }

}
