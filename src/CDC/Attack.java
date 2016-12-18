package CDC;

import java.util.ArrayList;

public class Attack {
    final static int BULLETVEL = 4;
    final static int MAPSIZE = 1985;
    final static int WINDOWSIZEX = 150;
    final static int WINDOWSIZEY = 72;

    public void attack(int ClientNO,
            ArrayList<ClientPlayerFeature> clientPlayerFeature,
            ArrayList<ClientItemFeature> clientItemFeature) {
        ClientPlayerFeature player = clientPlayerFeature.get(ClientNO);
        /*
        if (player.getCD())
            return;
        else
            player.setCD();
        */
        switch (player.getWeaponType()) {
            case 0:
                attackShortRange(ClientNO, clientPlayerFeature,
                        clientItemFeature);
                break;
            case 1:
                attackLongRange(ClientNO, clientPlayerFeature,
                        clientItemFeature);
                break;
            default:
                new Exception("error Weapon Type");
        }
    }

    public void attackShortRange(int ClientNO,
            ArrayList<ClientPlayerFeature> clientPlayerFeature,
            ArrayList<ClientItemFeature> clientItemFeature) {
        ClientPlayerFeature player = clientPlayerFeature.get(ClientNO);
        player.setAttackFlag(true);

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
        ClientItemFeature attackArea1 = new ClientItemFeature(0, 4,
                (int) Math.round(fakeX), (int) Math.round(fakeY));
        attackI2P(ClientNO, attackArea1, clientPlayerFeature);
        attackI2B(attackArea1, clientItemFeature);

        // 先往前走一步，轉另一方向的90度
        fakeX = player.getLocX() + sin * Cdc.BOX_SIZE
                - cos * Cdc.BOX_SIZE * 0.5;
        fakeY = player.getLocY() - cos * Cdc.BOX_SIZE
                - sin * Cdc.BOX_SIZE * 0.5;
        // attack area 2
        ClientItemFeature attackArea2 = new ClientItemFeature(0, 4,
                (int) Math.round(fakeX), (int) Math.round(fakeY));
        attackI2P(ClientNO, attackArea2, clientPlayerFeature);
        attackI2B(attackArea2, clientItemFeature);
    }

    private boolean attackI2P(int ClientNO, ClientItemFeature item1,
            ArrayList<ClientPlayerFeature> clientPlayerFeature) {
        boolean isThisAttack = false;
        ClientPlayerFeature player1 = clientPlayerFeature.get(ClientNO);
        Collision collision = new Collision();
        for (ClientPlayerFeature player2 : clientPlayerFeature) {
            if (player2.getClientNo() == ClientNO) {
                continue;// for prevent synchronize problem
            } else {
                if (collision.isItemPlayerCollison(item1, player2)) {
                    player1.setAttackFlag(true);
                    player2.setAttackedFlag(true);
                    isThisAttack = true;
                    if (subBlood(player2, item1.getItemType() - 3)) {
                        player1.setKillCount(player1.getKillCount() + 1);

                        player2.setDeadCount(player2.getDeadCount() + 1);
                        player2.setDead(true);
                    }
                }
            }
        }
        return isThisAttack;
    }

    private boolean subBlood(ClientPlayerFeature player2, int attackType) {
        int HP = player2.getHP();
        int minusBlood = (attackType) * +10 + 40;// 0:子彈:40, 1:刀:50
        HP -= minusBlood;
        if (HP <= 0) {
            return true;
        }
        return false;
    }

    private boolean attackI2B(ClientItemFeature item1,
            ArrayList<ClientItemFeature> clientItemFeature) {
        boolean isAttack = false;
        Collision collision = new Collision();
        for (ClientItemFeature item2 : clientItemFeature) {
            if (item2.getItemType() != 0)
                continue;
            if (collision.isItemItemCollison(item1, item2)) {
                item2.setDead(true);
                isAttack = true;
            }
        }
        return isAttack;
    }

    private void attackLongRange(int ClientNO,
            ArrayList<ClientPlayerFeature> clientPlayerFeature,
            ArrayList<ClientItemFeature> clientItemFeature) {
        ClientPlayerFeature player = clientPlayerFeature.get(ClientNO);
        if (player.getBulletCount() > 0) {
            player.setBulletCount(player.getBulletCount() - 1);
            player.setAttackFlag(true);
            int LocX = player.getLocX();
            int LocY = player.getLocY();

            ClientItemFeature bullet = new ClientItemFeature(0, 3, LocX, LocY);
            bullet.setFaceAngle(player.getFaceAngle());
            bullet.setItemOwner(ClientNO);
            clientItemFeature.add(bullet);
        }
    }

    public void attackLongRangeUpdate(
            ArrayList<ClientPlayerFeature> clientPlayerFeature,
            ArrayList<ClientItemFeature> clientItemFeature) {
        for (ClientItemFeature bullet : clientItemFeature) {
            if (bullet.getItemType() != 3)
                continue;
            double faceAngle = bullet.getFaceAngle();
            double radianAngle = Math.toRadians(faceAngle);
            int locX, loxY, oriLocX, oriLocY;

            locX = (int) Math.round(
                    bullet.getLocX() + Math.sin(radianAngle) * BULLETVEL);
            oriLocX = bullet.getOriLocX();
            if (locX > MAPSIZE || locX < 0 || locX > oriLocX + WINDOWSIZEX
                    || locX < oriLocX - WINDOWSIZEX) {
                clientPlayerFeature.remove(bullet);
                return;
            }

            loxY = (int) Math.round(
                    bullet.getLocY() - Math.cos(radianAngle) * BULLETVEL);
            oriLocY = bullet.getOriLocY();
            if (loxY > MAPSIZE || loxY < 0 || loxY > oriLocY + WINDOWSIZEY
                    || loxY < oriLocY - WINDOWSIZEY) {
                clientPlayerFeature.remove(bullet);
            }

            bullet.setLocX(locX);
            bullet.setLocY(loxY);
            if (attackI2P(bullet.getItemOwner(), bullet, clientPlayerFeature)
                    || attackI2B(bullet, clientItemFeature))
                clientItemFeature.remove(bullet);
        }
    }
}
