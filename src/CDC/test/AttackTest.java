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
        int oriBlood;
        ClientPlayerFeature player = clientPlayerFeature.get(0);
        ClientPlayerFeature attackedPlayer = clientPlayerFeature.get(1);
        player.setWeaponType(0); // 0 是近戰

        // case1 距離太遠打不到
        oriBlood = attackedPlayer.getHP();
        Attack test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        assertEquals(oriBlood, attackedPlayer.getHP());
        // (16+16*1.2=35.2 是攻擊距離) 故 但測出來 100-33 = 67 是 極限

        // case2 打的到 在 玩家0 正北方 被打到
        player.setFaceAngle(0);
        attackedPlayer.setLocX(100);
        attackedPlayer.setLocY(67);
        oriBlood = attackedPlayer.getHP();
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        // System.out.println(attackedPlayer.getHP());
        assertEquals(oriBlood - 80, attackedPlayer.getHP());

        // case2.1 打不到 玩家0 在正北方
        attackedPlayer.setHP(100);
        attackedPlayer.setLocX(100);
        attackedPlayer.setLocY(66);
        oriBlood = attackedPlayer.getHP();
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        assertEquals(oriBlood, attackedPlayer.getHP());

        // case3 打的到 在 玩家0 正東方 被打到
        player.setFaceAngle(90);
        attackedPlayer.setHP(100);
        attackedPlayer.setLocX(133);
        attackedPlayer.setLocY(100);
        oriBlood = attackedPlayer.getHP();
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        // System.out.println(attackedPlayer.getHP());
        assertEquals(oriBlood - 80, attackedPlayer.getHP());

        // case4 打的到 在 玩家0 正東方 被打到
        player.setFaceAngle(90);
        attackedPlayer.setHP(100);
        attackedPlayer.setLocX(133);
        attackedPlayer.setLocY(100);
        oriBlood = attackedPlayer.getHP();
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        // System.out.println(attackedPlayer.getHP());
        assertEquals(oriBlood - 80, attackedPlayer.getHP());

        // case5 打的到 在 玩家0 正南方 被打到
        player.setFaceAngle(180);
        attackedPlayer.setHP(100);
        attackedPlayer.setLocX(100);
        attackedPlayer.setLocY(133);
        oriBlood = attackedPlayer.getHP();
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        // System.out.println(attackedPlayer.getHP());
        assertEquals(oriBlood - 80, attackedPlayer.getHP());

        // case6 打的到 在 玩家0 正西方 被打到
        player.setFaceAngle(270);
        attackedPlayer.setHP(100);
        attackedPlayer.setLocX(67);
        attackedPlayer.setLocY(100);
        oriBlood = attackedPlayer.getHP();
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        // System.out.println(attackedPlayer.getHP());
        assertEquals(oriBlood - 80, attackedPlayer.getHP());

        // case7 假箱子 假設他在玩家正北方 打到 扣 60
        clientItemFeature.add(new ClientItemFeature(0, 0, 100, 67));// id =1 ,假箱子
        player.setFaceAngle(0);
        oriBlood = player.getHP();
        assertEquals(false, clientItemFeature.get(0).isDead());
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        assertEquals(oriBlood - 60, player.getHP());
        assertEquals(true, clientItemFeature.get(0).isDead());

    }

}
