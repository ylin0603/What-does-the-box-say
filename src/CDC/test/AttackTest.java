package CDC.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import CDC.Attack;
import CDC.Cdc;
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
        clientPlayerFeature.add(new ClientPlayerFeature(0, 100, 100));
        clientPlayerFeature.add(new ClientPlayerFeature(1, 5000, 5000));
        player = clientPlayerFeature.get(0);
        attackedPlayer = clientPlayerFeature.get(1);
    }

    @After
    public void tearDown() throws Exception {
        clientItemFeature = null;
        clientBulletFeature = null;
        clientPlayerFeature = null;
    }

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
        assertEquals(oriBlood - 50, attackedPlayer.getHP());
        assertEquals(false, attackedPlayer.isDead());

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
        assertEquals(oriBlood - 50, attackedPlayer.getHP());
    }

    @Test
    public void testAttackShortRangeTooFar() {
        int oriBlood;
        player.setWeaponType(0); // 0 是近戰

        oriBlood = attackedPlayer.getHP();
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        assertEquals(oriBlood, attackedPlayer.getHP());
        assertEquals(false, attackedPlayer.isDead());
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

        assertEquals(oriBlood - 50, attackedPlayer.getHP());
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

        assertEquals(oriBlood - 50, attackedPlayer.getHP());
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

        assertEquals(oriBlood - 50, attackedPlayer.getHP());
    }

    @Test
    public void testAttackShortRangeFakeBox() {
        int oriBlood;
        player.setWeaponType(0); // 0 是近戰
        // case7 假箱子 假設他在玩家正北方 打到 扣 60
        clientItemFeature.add(new ClientItemFeature(0, 0, 100, 67));// id =1 ,假箱子
        player.setFaceAngle(0);
        oriBlood = player.getHP();
        assertEquals(false, player.isDead());
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        assertEquals(oriBlood - 60, player.getHP());
        assertEquals(false, player.isDead());

    }

    @Test
    public void testAttackBulletRangeY() {// 測試攻擊距離Y
        player.setWeaponType(1);
        player.setFaceAngle(180);
        attackedPlayer.setLocX(100);
        attackedPlayer.setLocY(259);
        attackedPlayer.setHP(100);
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        for (int i = 0; i < 30; i++) {
            new Attack().attackLongRangeUpdate(clientPlayerFeature,
                    clientItemFeature, clientBulletFeature);
        }
        assertEquals(60, attackedPlayer.getHP());
        assertEquals(0, clientBulletFeature.size());
        // 這是在極限距離外
        attackedPlayer.setLocX(100);
        attackedPlayer.setLocY(260);
        attackedPlayer.setHP(100);
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        for (int i = 0; i < 31; i++) {
            new Attack().attackLongRangeUpdate(clientPlayerFeature,
                    clientItemFeature, clientBulletFeature);
        }
        assertEquals(100, attackedPlayer.getHP());
        assertEquals(0, clientBulletFeature.size());
    }

    @Test
    public void testAttackBulletRangeX() {// 測試攻擊距離X
        player.setWeaponType(1); // 1 是遠攻
        player.setFaceAngle(90);
        // 在幾乎極限距離被打到 飛了
        attackedPlayer.setLocX(419);
        attackedPlayer.setLocY(100);
        attackedPlayer.setHP(100);
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        for (int i = 0; i < 30; i++) {
            new Attack().attackLongRangeUpdate(clientPlayerFeature,
                    clientItemFeature, clientBulletFeature);
        }
        assertEquals(60, attackedPlayer.getHP());
        assertEquals(0, clientBulletFeature.size());
        // 這是在極限距離外
        attackedPlayer.setLocX(420);
        attackedPlayer.setLocY(100);
        attackedPlayer.setHP(100);
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        for (int i = 0; i < 31; i++) {
            new Attack().attackLongRangeUpdate(clientPlayerFeature,
                    clientItemFeature, clientBulletFeature);
        }
        assertEquals(100, attackedPlayer.getHP());
        assertEquals(0, clientBulletFeature.size());
    }

    @Test
    public void testAttackBulletRangeAtBorderY() {

        player.setWeaponType(1); // 1 是遠攻
        player.setFaceAngle(180);
        player.setLocX(0);
        player.setLocY(0);
        // 在幾乎極限距離被打到 飛了 120 的距離
        attackedPlayer.setLocX(0);
        attackedPlayer.setLocY(50);
        attackedPlayer.setHP(100);

        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        for (int i = 0; i < 31; i++) {
            new Attack().attackLongRangeUpdate(clientPlayerFeature,
                    clientItemFeature, clientBulletFeature);
        }
        assertEquals(60, attackedPlayer.getHP());

    }

    @Test
    public void testAttackBulletRangeAtBorderX() {
        player.setWeaponType(1); // 1 是遠攻
        player.setFaceAngle(90);
        player.setLocX(0);
        player.setLocY(0);
        // 在幾乎極限距離被打到 飛了 120 的距離
        attackedPlayer.setLocX(20);
        attackedPlayer.setLocY(0);
        attackedPlayer.setHP(100);

        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        for (int i = 0; i < 31; i++) {
            new Attack().attackLongRangeUpdate(clientPlayerFeature,
                    clientItemFeature, clientBulletFeature);
        }
        assertEquals(60, attackedPlayer.getHP());
    }



    @Test
    public void testAttackBulletBorder() { // 射到邊界 子彈是否消失
        player.setWeaponType(1); // 1 是遠攻
        // 人在左下角靠近邊界
        player.setLocX(Cdc.MAP_SIZE_X - 50);
        player.setLocY(Cdc.MAP_SIZE_Y - 50);
        player.setFaceAngle(90);
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        for (int i = 0; i < 5; i++) {
            new Attack().attackLongRangeUpdate(clientPlayerFeature,
                    clientItemFeature, clientBulletFeature);
        }
        assertEquals(0, clientBulletFeature.size());
    }

    @Test
    public void testAttackBulletAngle() {
        // 被攻擊腳色在南邊
        player.setWeaponType(1); // 1 是遠攻
        attackedPlayer.setLocX(100);
        attackedPlayer.setLocY(220);

        player.setFaceAngle(170);
        attackedPlayer.setHP(100);
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        for (int i = 0; i < 31; i++) {
            new Attack().attackLongRangeUpdate(clientPlayerFeature,
                    clientItemFeature, clientBulletFeature);
        }
        assertEquals(100, attackedPlayer.getHP());

        player.setFaceAngle(190);
        attackedPlayer.setHP(100);
        test = new Attack(player, clientPlayerFeature, clientItemFeature,
                clientBulletFeature);
        for (int i = 0; i < 31; i++) {
            new Attack().attackLongRangeUpdate(clientPlayerFeature,
                    clientItemFeature, clientBulletFeature);
        }
        assertEquals(100, attackedPlayer.getHP());
    }
}
