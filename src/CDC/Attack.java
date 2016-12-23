package CDC;

import java.util.ArrayList;
import java.util.List;

public class Attack {
    final static int BULLETVEL = 4;
    final static int BOXSIZE = 16;
    final static int MAP_SIZE_X = Cdc.MAP_SIZE_X - BOXSIZE;
    final static int MAP_SIZE_Y = Cdc.MAP_SIZE_Y - BOXSIZE;
    final static int WINDOWSIZEX = 150;
    final static int WINDOWSIZEY = 72;
    final static double revengeSize =
            Math.pow((Math.pow(1.2 * BOXSIZE, 2) - Math.pow(BOXSIZE / 2, 2)),
                    (0.5)) + 8 * (2 - 1.2);

    // ((1.2 * 16) ^ 2 - (8) ^ 2) ^ (1 / 2) + 8 * (2 - 1.2)
    // 23.853939383417143058403447179797

    final static int SHORTRANGE = 0;
    final static int LONGRANGE = 1;

    final static int BULLET = 0;
    final static int SWORD = 1;

    public Attack(ClientPlayerFeature player,
            List<ClientPlayerFeature> clientPlayerFeature,
            List<ClientItemFeature> clientItemFeature,
            List<ClientBulletFeature> clientBulletFeature) {
        switch (player.getWeaponType()) {
            case SHORTRANGE:
                attackShortRange(player, clientPlayerFeature,
                        clientItemFeature);
                break;
            case LONGRANGE:
                attackLongRange(player, clientPlayerFeature, clientItemFeature,
                        clientBulletFeature);
                break;
            default:
                new Exception("error Weapon Type");
        }
    }

    Attack() {

    }

    public void attackShortRange(ClientPlayerFeature player,
            List<ClientPlayerFeature> clientPlayerFeature,
            List<ClientItemFeature> clientItemFeature) {
        double faceAngle = player.getFaceAngle();
        double radianAngle = Math.toRadians(faceAngle);
        double sin = Math.sin(radianAngle);
        double cos = Math.cos(radianAngle);
        double fakeX = 0;
        double fakeY = 0;

        // 先往前走一步，再轉90度走半步
        fakeX = player.getLocX() + sin * Cdc.BOX_SIZE
                + cos * Cdc.BOX_SIZE * 0.5;
        fakeY = player.getLocY() - cos * Cdc.BOX_SIZE
                + sin * Cdc.BOX_SIZE * 0.5;
        // attack area 1
        ClientBulletFeature attackArea1 =
                new ClientBulletFeature(SWORD, (int) Math.round(fakeX),
                        (int) Math.round(fakeY), 0, player.getClientNo());
        attackBulletToPlayer(attackArea1, clientPlayerFeature);
        attackBulletToBox(attackArea1, clientPlayerFeature, clientItemFeature);

        // 先往前走一步，轉另一方向的90度
        fakeX = player.getLocX() + sin * Cdc.BOX_SIZE
                - cos * Cdc.BOX_SIZE * 0.5;
        fakeY = player.getLocY() - cos * Cdc.BOX_SIZE
                - sin * Cdc.BOX_SIZE * 0.5;
        // attack area 2
        attackArea1.setLocX((int) Math.round(fakeX));
        attackArea1.setLocY((int) Math.round(fakeY));

        attackBulletToPlayer(attackArea1, clientPlayerFeature);
        attackBulletToBox(attackArea1, clientPlayerFeature, clientItemFeature);
    }

    public boolean attackBulletToPlayer(ClientBulletFeature bullet,
            List<ClientPlayerFeature> clientPlayerFeature) {
        boolean isThisAttack = false;
        boolean[] isAttacked = bullet.getIsAttacked();
        ClientPlayerFeature player1 =
                clientPlayerFeature.get(bullet.getItemOwner());
        for (ClientPlayerFeature player2 : clientPlayerFeature) {
            if (player2.getClientNo() == bullet.getItemOwner()
                    || isAttacked[clientPlayerFeature.indexOf(player2)]) {
                continue;// for prevent synchronize problem
            } else {
                if (Collision.isCollison(bullet, player2)) {
                    player2.setAttackedFlag(true);
                    isThisAttack = true;
                    isAttacked[clientPlayerFeature.indexOf(player2)] = true;
                    if (subBlood(player2, bullet.getItemType())) {
                        player1.setKillCount(player1.getKillCount() + 1);

                        kill(player2);
                    }
                    bullet.setIsAttacked(isAttacked);
                }
            }
        }
        return isThisAttack;
    }

    private boolean subBlood(ClientPlayerFeature player2, int attackType) {
        int HP = player2.getHP();
        boolean isDead = false;
        int minusBlood = (attackType) * +10 + 40;// 0:子彈:40, 1:刀:50 2:假箱爆:60
        HP -= minusBlood;
        if (HP <= 0) {
            HP = 0;
            isDead = true;
        } else {
            isDead = false;
        }
        player2.setHP(HP);
        return isDead;
    }

    private void kill(ClientPlayerFeature player) {
        player.setDeadCount(player.getDeadCount() + 1);
        player.setDead(true);
        player.init();
    }

    public boolean attackBulletToBox(ClientBulletFeature bullet,
            List<ClientPlayerFeature> clientPlayerFeature,
            List<ClientItemFeature> clientItemFeature) {
        boolean isAttack = false;
        for (ClientItemFeature item2 : clientItemFeature) {
            if (item2.getItemType() != 0 || item2.isDead())
                continue;
            if (Collision.isCollison(bullet, item2)) {
                item2.setDead(true);
                isAttack = true;
                boxRevenge(item2, clientPlayerFeature);
            }
        }
        return isAttack;
    }

    public void boxRevenge(ClientItemFeature item2,
            List<ClientPlayerFeature> clientPlayerFeature) {
        double fakeX = item2.getLocX();
        double fakeY = item2.getLocY();
        for (ClientPlayerFeature player2 : clientPlayerFeature) {
            if (Collision.isCollison((int) Math.round(fakeX),
                    (int) Math.round(fakeY), revengeSize, player2)) {
                player2.setAttackedFlag(true);
                if (subBlood(player2, 2)) {
                    kill(player2);
                }
            }
        }
    }

    private void attackLongRange(ClientPlayerFeature player,
            List<ClientPlayerFeature> clientPlayerFeature,
            List<ClientItemFeature> clientItemFeature,
            List<ClientBulletFeature> clientBulletFeature) {
        if (player.getBulletCount() > 0) {
            player.setBulletCount(player.getBulletCount() - 1);
            int LocX = player.getLocX();
            int LocY = player.getLocY();
            double faceAngle = player.getFaceAngle();
            ClientBulletFeature bullet = new ClientBulletFeature(BULLET, LocX,
                    LocY, faceAngle, player.getClientNo());
            clientBulletFeature.add(bullet);
        }
    }

    public void attackLongRangeUpdate(
            List<ClientPlayerFeature> clientPlayerFeature,
            List<ClientItemFeature> clientItemFeature,
            List<ClientBulletFeature> clientBulletFeature) {
        for (ClientBulletFeature bullet : clientBulletFeature) {
            if (bullet.getItemType() != 0 || bullet.isDead())
                continue;
            double faceAngle = bullet.getFaceAngle();
            double radianAngle = Math.toRadians(faceAngle);
            int locX, loxY, oriLocX, oriLocY;

            locX = (int) Math.round(
                    bullet.getLocX() + Math.sin(radianAngle) * BULLETVEL);
            oriLocX = bullet.getOriLocX();
            if (locX > MAP_SIZE_X || locX < 0 || locX > oriLocX + WINDOWSIZEX
                    || locX < oriLocX - WINDOWSIZEX) {
                clientBulletFeature.remove(bullet);
                return;
            }

            loxY = (int) Math.round(
                    bullet.getLocY() - Math.cos(radianAngle) * BULLETVEL);
            oriLocY = bullet.getOriLocY();
            if (loxY > MAP_SIZE_Y || loxY < 0 || loxY > oriLocY + WINDOWSIZEY
                    || loxY < oriLocY - WINDOWSIZEY) {
                clientBulletFeature.remove(bullet);
            }

            bullet.setLocX(locX);
            bullet.setLocY(loxY);
            if (attackBulletToPlayer(bullet, clientPlayerFeature)
                    || attackBulletToBox(bullet, clientPlayerFeature,
                            clientItemFeature))
                clientBulletFeature.remove(bullet);
        }
    }
}
