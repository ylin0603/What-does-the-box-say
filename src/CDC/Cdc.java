package CDC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.lang.Math;
import java.util.Timer;
import java.util.TimerTask;

import tcp.tcpServer.RealTcpServer;
import udp.broadcast.client.UDP_Client;

public class Cdc {
    public final static int BOX_SIZE = 16;// this is two are read for all server
    public final static int MAP_SIZE_X = 640;
    public final static int MAP_SIZE_Y = 1200;
    // TODO: reset these
    private final static int MOVE = 0;
    private final static int SPIN = 1;
    private final static int ATTACK = 2;
    private final static int CHANGEWEAPON = 3;
    private final static int BLOODPACKGEINDEX = 30;
    private final static int BULLETPACKGEINDEX = 31;
    private final static int VEL = 4;
    private final static double ANGLEVEL = 6;// degree
    private static Cdc instance;
    private static RealTcpServer realTcpServer;
    private static UDP_Client UDPinstance;

    Random random = new Random();
    private long startTime;
    private List<ClientPlayerFeature> allPlayers =
            Collections.synchronizedList(new ArrayList<>());
    private List<ClientItemFeature> allItems =
            Collections.synchronizedList(new ArrayList<>());
    private List<ClientBulletFeature> allBullets =
            Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {
        // tcp
        realTcpServer = RealTcpServer.getInstance();
        realTcpServer.initTCPServer();
    }

    public static Cdc getInstance() {
        if (instance == null)
            instance = new Cdc();
        return instance;
    }

    public void startUpdatingTimer() {
        Timer gameTimer = new Timer();
        TimerTask startUpdating = new TimerTask() {
            @Override
            public void run() {
                startTime = System.currentTimeMillis();
                UDPinstance = UDP_Client.getInstance();

                if (finishGame(300)) {// 5分鐘就是300秒
                    // do something
                }
                movingPlayer();
                movingBullet();
                checkResurrection();// 檢查復活
                checkSupplement();// 每十秒補充補包彈藥包

                UDPinstance.broadcast(realTcpServer.getClientIPTable(),
                        UDPinstance.encapsulateData(getPlayersUpdateInfo(),
                                getItemsUpdateInfo(), getAllBullets()));// broadcast
            }
        };
        gameTimer.schedule(startUpdating, 0, 33);
    }

    private void Cdc() {}

    public List<ClientPlayerFeature> getPlayersUpdateInfo() {
        return allPlayers;
    }

    public List<ClientItemFeature> getItemsUpdateInfo() {
        return allItems;
    }

    public List<ClientBulletFeature> getAllBullets() { return allBullets; }

    public void addVirtualCharacter(int clientNo, String nickName) {
        assert clientNo > -1;
        assert !nickName.isEmpty();
        System.out.println(clientNo + " addVirtualCharacter");
        int[] loc = giveRandomLocation(); // initial position
        allPlayers.add(
                new ClientPlayerFeature(clientNo, nickName, loc[0], loc[1]));
    }

    public int[] giveRandomLocation() {
        int[] location = new int[2];
        int xRange, yRange; // the distance between two objects.
        boolean isOverlapped = true;
        while (isOverlapped) {
            isOverlapped = false;

            location[0] = random.nextInt(MAP_SIZE_X - BOX_SIZE + 1);
            location[1] = random.nextInt(MAP_SIZE_Y - BOX_SIZE + 1);
            if (allPlayers.size() > 0) {
                for (ClientPlayerFeature player : allPlayers) {
                    int curPlayerLocx = player.getLocX();
                    int curPlayerLocy = player.getLocY();
                    xRange = Math.abs(location[0] - curPlayerLocx);
                    yRange = Math.abs(location[1] - curPlayerLocy);
                    if ((xRange <= BOX_SIZE) && (yRange <= BOX_SIZE)) // overlapped
                    {
                        isOverlapped = true;
                        break;
                    }
                }
            }
            if (isOverlapped)
                continue;
            for (ClientItemFeature item : allItems) {
                int curItemLocx = item.getLocX();
                int curItemLocy = item.getLocY();
                xRange = Math.abs(location[0] - curItemLocx);
                yRange = Math.abs(location[1] - curItemLocy);
                if ((xRange <= BOX_SIZE) && (yRange <= BOX_SIZE)) {
                    isOverlapped = true;
                    break;
                }
            }
            if (isOverlapped)
                continue;
        }
        return location;
    }

    public void initFakeBox() {
        for (int fakeBoxNum = 0; fakeBoxNum < BLOODPACKGEINDEX; fakeBoxNum++) {
            int[] loc = giveRandomLocation();
            allItems.add(new ClientItemFeature(fakeBoxNum, 0, loc[0], loc[1]));
        }
    }

    public void initBloodPackge() {
        int[] loc = giveRandomLocation();
        allItems.add(
                new ClientItemFeature(BLOODPACKGEINDEX, 1, loc[0], loc[1]));
    }

    public void initBulletPackge() {
        int[] loc = giveRandomLocation();
        allItems.add(
                new ClientItemFeature(BULLETPACKGEINDEX, 2, loc[0], loc[1]));
    }

    public void gameItemsInital() {
        initFakeBox();
        initBloodPackge();
        initBulletPackge();
    }

    public void updateKeys(int clientNo, int[] moveCode) {
        assert clientNo > -1;
        assert moveCode.length == 4;
        // "move,spin,attack,change weapon"

        allPlayers.get(clientNo).setDirection(moveCode);
    }

    public void movingPlayer() {
        for (ClientPlayerFeature player : allPlayers) {
            if (player.isDead())
                continue;
            player.resetPerRound();
            // TODO: key parsing should be out of this function
            double faceAngle = player.getFaceAngle();
            double radianAngle = Math.toRadians(faceAngle);
            int[] moveCode = player.getDirection();
            // "move,spin,attack,change weapon"
            switch (moveCode[MOVE]) {
                case 1:
                    forward(player, radianAngle);
                    break;
                case -1:
                    backward(player, radianAngle);
                    break;
                case 0:
                    if (player.checkRecover())
                        player.addHP(5);
                    break;
                default:
                    throw new Error("Out of Move direction!");
            }
            switch (moveCode[SPIN]) {
                case 1:
                    turnRight(player, faceAngle);
                    break;
                case -1:
                    turnLeft(player, faceAngle);
                    break;
                case 0:
                    // Don't Spin
                    break;
                default:
                    throw new Error("Out of Spin direction!");
            }
            if (moveCode[ATTACK] == 1)
                attack(player);
            else
                player.setAttackFlag(false);
            if (moveCode[CHANGEWEAPON] == 1) {
                // moveCode[CHANGEWEAPON] = 0;
                if (player.isChangeWeaponCD()) {
                    player.setChangeWeaponCD();
                    changeWeapon(player);
                }
            }
        }
    }

    private boolean moveCollision(int x, int y, ClientPlayerFeature player) {
        boolean isImpacted = false;
        boolean colliHappened = false;
        for (ClientPlayerFeature collisionPlayer : allPlayers) {
            if (collisionPlayer.equals(player) || collisionPlayer.isDead())
                continue;
            isImpacted = Collision.isCollison(x, y, collisionPlayer);
            if (isImpacted) {
                player.setCollisionFlag(true);
                collisionPlayer.setCollisionFlag(true);
                colliHappened = true;
                break;
            }
        }
        for (ClientItemFeature collisionItem : allItems) {// only 30 fake boxes
                                                          // will collision
            if (collisionItem.getItemType() != 0)
                continue;
            isImpacted = Collision.isCollison(x, y, collisionItem);
            if (isImpacted) {
                colliHappened = true;
                break;
            }
        }
        return colliHappened;
    }

    private boolean finishGame(int gameTime) {
        long now = System.currentTimeMillis();
        if (now - startTime <= gameTime * 1000)
            return false;
        else
            return true;
    }

    private void forward(ClientPlayerFeature player, double radianAngle) {
        // 攻擊範圍判斷依照此邏輯複製，如有修改，請一併確認 attackShortRange()
        double diffX = player.getLocX() + Math.sin(radianAngle) * VEL;
        double diffY = player.getLocY() - Math.cos(radianAngle) * VEL;

        if (!moveCollision((int) Math.round(diffX), (int) Math.round(diffY),
                player)) {
            player.setLocX((int) Math.round(diffX));
            player.setLocY((int) Math.round(diffY));
        }
        checkGetItem(player); // 只考慮前進後退才會吃到，旋轉不會碰到補給
    }

    private void backward(ClientPlayerFeature player, double radianAngle) {
        // 攻擊範圍判斷依照此邏輯複製，如有修改，請一併確認 attackShortRange()
        double diffX = player.getLocX() - Math.sin(radianAngle) * VEL;
        double diffY = player.getLocY() + Math.cos(radianAngle) * VEL;

        if (!moveCollision((int) Math.round(diffX), (int) Math.round(diffY),
                player)) {
            player.setLocX((int) Math.round(diffX));
            player.setLocY((int) Math.round(diffY));
        }
        checkGetItem(player); // 只考慮前進後退才會吃到，旋轉不會碰到補給
    }

    // TODO: 碰到物體則等於吃到，感覺要每秒去確認，但感覺會很慢？
    private void checkGetItem(ClientPlayerFeature player) {
        int itemSize = allItems.size();
        boolean isImpacted = false;
        for (int currItemIndex =
                BLOODPACKGEINDEX; currItemIndex < itemSize; currItemIndex++) {
            if (allItems.get(currItemIndex).isDead())
                continue;
            isImpacted =
                    Collision.isCollison(allItems.get(currItemIndex), player);
            if (isImpacted) {
                switch (currItemIndex) {
                    case BLOODPACKGEINDEX:
                        player.addHP(60);
                        allItems.get(currItemIndex).setDead(true);
                        allItems.get(currItemIndex).setRebornTime(
                                System.currentTimeMillis() + 10 * 1000);
                        break;
                    case BULLETPACKGEINDEX:
                        player.addBullet(1);
                        allItems.get(currItemIndex).setDead(true);
                        allItems.get(currItemIndex).setRebornTime(
                                System.currentTimeMillis() + 10 * 1000);
                        break;
                    default:
                        break;
                }
                break;
            }
        }
    }

    private void turnRight(ClientPlayerFeature player, double faceAngle) {
        // 攻擊範圍判斷依照此邏輯複製，如有修改，請一併確認 attackShortRange()
        player.setFaceAngle(faceAngle + ANGLEVEL);
    }

    private void turnLeft(ClientPlayerFeature player, double faceAngle) {
        // 攻擊範圍判斷依照此邏輯複製，如有修改，請一併確認 attackShortRange()
        player.setFaceAngle(faceAngle - ANGLEVEL);
    }

    public void attack(ClientPlayerFeature player) {
        if (!player.isAttackCD()) {
            player.setAttackFlag(false);
            return;
        } else {
            player.setAttackCD();
            new Attack(player, allPlayers, allItems, allBullets);
        }
    }

    public void changeWeapon(ClientPlayerFeature player) {
        player.setWeaponType(1 - player.getWeaponType());
        // 1 to 0
        // 0 to 1
    }

    private void movingBullet() {
        new Attack().attackLongRangeUpdate(allPlayers, allItems, allBullets);
    }

    private void checkResurrection() {// 檢查復活
        for (ClientPlayerFeature player : allPlayers) {
            if (player.isDead()) {
                if (player.checkResurrection()) {
                    int[] loc = Cdc.getInstance().giveRandomLocation();
                    player.reborn(loc[0], loc[1]);
                }
            }
        }
        for (ClientItemFeature item : allItems) {
            if (item.isReborn()) {// initial at next round
                int[] loc = giveRandomLocation();
                item.init(loc[0], loc[1]);
            }
            if (item.getItemType() == 0 && item.isDead())// don't initial at dead
                item.setReborn(true);
        }
    }

    private void checkSupplement() {
        long now = System.currentTimeMillis();
        if (allItems.get(BLOODPACKGEINDEX).isDead()
                && allItems.get(BLOODPACKGEINDEX).getRebornTime() < now) {
            rebornFunctionalPack(allItems.get(BLOODPACKGEINDEX));
        }
        if (allItems.get(BULLETPACKGEINDEX).isDead()
                && allItems.get(BULLETPACKGEINDEX).getRebornTime() < now) {
            rebornFunctionalPack(allItems.get(BULLETPACKGEINDEX));
        }
    }

    // to reborn bullet or blood packages
    public void rebornFunctionalPack(ClientItemFeature item) {
        int[] loc = giveRandomLocation();
        item.init(loc[0], loc[1]);
    }
}
