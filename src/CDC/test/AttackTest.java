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
        clientPlayerFeature.add(new ClientPlayerFeature(0, "bbb", 100, 100));
        clientPlayerFeature.add(new ClientPlayerFeature(1, "bbb", 5000, 5000));
        int oriBlood = clientPlayerFeature.get(1).getHP();
        // clientItemFeature.add(new ClientItemFeature(1,1, 210, 210));// id =1 ,假箱子
        ClientPlayerFeature player = clientPlayerFeature.get(0);
        player.setWeaponType(0); // 0 是近戰

        // case1 距離太遠打不到
        player.setFaceAngle(0);
        Attack test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        assertEquals(oriBlood, clientPlayerFeature.get(1).getHP());
        // (16+16*1.2=35.2 是攻擊距離) 故 但測出來 100-33 = 67 是 極限
        // case2 打的到 在 玩家0 北方 被打到
        clientPlayerFeature.get(1).setLocX(100);
        clientPlayerFeature.get(1).setLocY(67);
        oriBlood = clientPlayerFeature.get(1).getHP();
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        System.out.println(clientPlayerFeature.get(1).getHP());
        assertEquals(oriBlood, clientPlayerFeature.get(1).getHP());


    }

}
