package CDC.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import CDC.Attack;
import CDC.ClientBulletFeature;
import CDC.ClientItemFeature;
import CDC.ClientPlayerFeature;

public class AttackTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testAttackShortRange() {
        ArrayList<ClientItemFeature> clientItemFeature =
                new ArrayList<ClientItemFeature>();
        ArrayList<ClientBulletFeature> clientBulletFeature =
                new ArrayList<ClientBulletFeature>();
        ArrayList<ClientPlayerFeature> clientPlayerFeature =
                new ArrayList<ClientPlayerFeature>();
        clientPlayerFeature.add(new ClientPlayerFeature(0, 0, 0));
        clientPlayerFeature.add(new ClientPlayerFeature(1, 10, 0));
        int oriBlood = clientPlayerFeature.get(1).getHP();
        // clientItemFeature.add(new ClientItemFeature(1,1, 210, 210));// id =1 ,假箱子
        ClientPlayerFeature player = clientPlayerFeature.get(0);
        player.setWeaponType(0); // 0 是近戰
        player.setFaceAngle(0);
        Attack test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        assertEquals(oriBlood, clientPlayerFeature.get(1).getHP());
    }

}
