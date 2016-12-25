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
    ArrayList<ClientItemFeature> clientItemFeature;
    ArrayList<ClientBulletFeature> clientBulletFeature;
    ArrayList<ClientPlayerFeature> clientPlayerFeature;
    ClientPlayerFeature player;
    ClientPlayerFeature attackedPlayer;
    Attack test;

    // (16+16*1.2=35.2 是攻擊距離) 故 但測出來 100-33 = 67 是 極限

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {}

    @Before
    public void setUp() throws Exception {
        clientItemFeature = new ArrayList<ClientItemFeature>();
        clientBulletFeature = new ArrayList<ClientBulletFeature>();
        clientPlayerFeature = new ArrayList<ClientPlayerFeature>();
        clientPlayerFeature.add(new ClientPlayerFeature(0, "bbb", 100, 100));
        clientPlayerFeature.add(new ClientPlayerFeature(1, "bbb", 5000, 5000));
        player = clientPlayerFeature.get(0);
        attackedPlayer = clientPlayerFeature.get(1);
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testAttackShortRangeNorth() {
        int oriBlood;
        player.setWeaponType(0); // 0 是近戰

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
        attackedPlayer.setLocY(66);///
        oriBlood = attackedPlayer.getHP();
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        assertEquals(oriBlood, attackedPlayer.getHP());

        // case2.3 打不到 玩家0 在正北方 右邊 斜斜的
        attackedPlayer.setHP(100);
        attackedPlayer.setLocX(111);
        attackedPlayer.setLocY(83);
        oriBlood = attackedPlayer.getHP();
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        assertEquals(oriBlood - 80, attackedPlayer.getHP());
    }

    @Test
    public void testAttackShortRangeTooFar() {
        int oriBlood;
        player.setWeaponType(0); // 0 是近戰

        oriBlood = attackedPlayer.getHP();
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        assertEquals(oriBlood, attackedPlayer.getHP());
    }

    @Test
    public void testAttackShortRangeEast() {
        int oriBlood;
        player.setWeaponType(0); // 0 是近戰

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
    }

    @Test
    public void testAttackShortRangeSouth() {
        int oriBlood;
        player.setWeaponType(0); // 0 是近戰

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
    }

    @Test
    public void testAttackShortRangeWest() {
        int oriBlood;
        player.setWeaponType(0); // 0 是近戰

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
    }

    @Test
    public void testAttackShortRangeFake() {
        int oriBlood;
        player.setWeaponType(0); // 0 是近戰
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
