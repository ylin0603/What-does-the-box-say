package CDC;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import tcp.tcpServer.RealTcpServer;

public class Cdc implements Runnable {
	private int setX = 0, setY = 0;
	private long startTime;
	private long tenSeconds;
	private ArrayList<ClientPlayerFeature> allPlayers = new ArrayList<>();
	private ArrayList<ClientItemFeature> allItems = new ArrayList<>();
	private Collision dealCollision;

	//TODO: reset these
	private final static int STOP = -1;
	private final static int FORWARD = 0;
	private final static int BACKWARD = 1;
	private final static int TURNRIGHT = 2;
	private final static int TURNLEFT = 3;

	private final static int VEL = 2;
	private final static double ANGLEVEL = 2;//degree

	private static Cdc instance = null;

	public final static int BOX_SIZE = 16;// this is two are read for all server
	public final static int MAP_SIZE = 2000;

	public static void main(String[] args) throws InterruptedException {
		// tcp
		RealTcpServer realTcpServer = RealTcpServer.getInstance();
		realTcpServer.initTCPServer();
	}

	public void startUpdatingThread() {
		Thread game = new Thread(instance);
		game.start();
	}

    private void Cdc() {}

	public static Cdc getInstance() {
		if (instance == null)
			instance = new Cdc();
		return instance;
	}

	public ArrayList<ClientPlayerFeature> getPlayersUpdateInfo() {
		return allPlayers;
	}
	public ArrayList<ClientItemFeature> getItemsUpdateInfo() {
		return allItems;
	}

	public void addVirtualCharacter(int clientNo, String nickName) {
		assert clientNo > -1;
		assert !nickName.isEmpty();

		giveRandomLocation(); //initial position
		allPlayers.add(new ClientPlayerFeature(clientNo, nickName, setX, setY));
	}

	public void giveRandomLocation() {
		int playerSize = allPlayers.size();
		int itemSize = allItems.size();
		int xRange, yRange; //the distance between two objects.
		boolean isOverlapped = true;
		while(isOverlapped) {
			Random random = new Random();
			setX = random.nextInt(MAP_SIZE - BOX_SIZE + 1);
			setY = random.nextInt(MAP_SIZE - BOX_SIZE + 1);
			for(int curPlayer = 0; curPlayer < playerSize; curPlayer ++){
				int curPlayerLocx = allPlayers.get(curPlayer).getLocX();
				int curPlayerLocy = allPlayers.get(curPlayer).getLocY();
				xRange = Math.abs(setX - curPlayerLocx);
				yRange = Math.abs(setY - curPlayerLocy);
				if((xRange <= BOX_SIZE) && (yRange <= BOX_SIZE)) // overlapped
					break;
			}
			if(isOverlapped) continue;

			for(int curItem = 0; curItem < itemSize; curItem ++){
				int curItemLocx = allItems.get(curItem).getLocX();
				int curItemLocy = allItems.get(curItem).getLocY();
				xRange = Math.abs(setX - curItemLocx);
				yRange = Math.abs(setY - curItemLocy);
				if((xRange <= BOX_SIZE) && (yRange <= BOX_SIZE)) break;
			}
			if(isOverlapped) continue;

			isOverlapped = false;
		}
		
	}

	public void rebornPlayer(ClientPlayerFeature player){
		player.setWeaponType(0);
		giveRandomLocation();
		player.setLocX(setX);
		player.setLocY(setY);
		player.setFaceAngle(0.0);
		player.setHP(100);
		player.setBulletCount(2);
		player.setAttackFlag(false);
		player.setAttackedFlag(false);
		player.setCollisionFlag(false);
		player.setDead(false);

	}

	public void initFakeBox(){
		for(int fakeBoxNum = 0; fakeBoxNum < 30; fakeBoxNum ++){
			giveRandomLocation();
			allItems.add(new ClientItemFeature(fakeBoxNum, 0, setX, setY));
		}
	}

	public void rebornFakeBox(ClientItemFeature fakeBox){
		giveRandomLocation();
		fakeBox.setLocX(setX);
		fakeBox.setLocY(setY);
		fakeBox.setDead(false);
		fakeBox.setCollision(false);
	}

	public void addItem(int itemID, int itemType, int x, int y) {
		assert itemID > -1 && itemType > -1;
		assert x > 0 && y > 0;

		//itemID要從30開始
		allItems.add(new ClientItemFeature(itemID, itemType, x, y));
	}

    public void updateDirection(int clientNo, boolean[] moveCode) {
		assert clientNo > -1;
        assert moveCode.length == 5;
        // 目前是將所有按住的按鍵記下來

		allPlayers.get(clientNo).setDirection(moveCode);
	}

	//TODO: 碰到物體則等於吃到，感覺要每秒去確認，但感覺會很慢？
	private void checkGetItem(ClientPlayerFeature player) {
		int itemSize = allItems.size();
		int itemType;
		boolean isImpacted = false;
		for(int currItem = 30; currItem < itemSize; currItem++){
			isImpacted = dealCollision.isItemPlayerCollison(allItems.get(currItem), player);
			if(isImpacted){
				itemType = allItems.get(currItem).getItemType();
				switch(itemType){
					case 1:
						player.addHP(60);
						break;

					case 2:
						player.addBullet(1);
						break;

					default:
						break;
				}
				break;
			}
		}
	}

	public void movingPlayer() {
		int playerSize = allPlayers.size();
		for (int i = 0; i < playerSize; i++) {
			ClientPlayerFeature player = allPlayers.get(i);
			double faceAngle = player.getFaceAngle();
			double radianAngle = Math.toRadians(faceAngle);
            boolean[] keys = player.getDirection();
            // keys "wsad "
            int move = 0, spin = 0;
            if (keys[0]) {
                move++;
            }
            if (keys[1]) {
                move--;
            }
            if (keys[2]) {
                spin--;
            }
            if (keys[3]) {
                spin++;
            }
            if(keys[4]) {
                keys[4] = false;
                player.setDirection(keys);
                new Attack().attack(player.getClientNo(), allPlayers, allItems);
				//attack;
			}
            if (keys[5]) {
                keys[5] = false;
                player.setDirection(keys);
                player.setWeaponType(1 - player.getWeaponType());
                // 1 to 0
                // 0 to 1
            }
            switch (move) {
                case 1:
                    forward(player, radianAngle);
                    break;
                case -1:
                    backward(player, radianAngle);
                    break;
                case 0:
                    player.checkRecover();
                    break;
                default:
                    throw new Error("Out of Move direction!");
            }
            switch (spin) {
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
        }
    }
	
	private void checkSupplement(){
		if(System.currentTimeMillis() >= tenSeconds){ //現在時間超過10秒
			//補衝 補包 彈藥包
			tenSeconds += 10*1000;
		}
	}
	
	private void checkResurrection(){//檢查復活
		int playerSize = allPlayers.size();
		for (int i = 0; i < playerSize; i++) {
			ClientPlayerFeature player = allPlayers.get(i);
			if(player.isDead()){
				if(player.checkResurrection()){
					rebornPlayer(player);
				}
			}
        }
	}

	private boolean finishGame (int gameTime){
		long now = System.currentTimeMillis();
		if(now - startTime <= gameTime*1000)
			return false;
		else
			return true;
	}

    private void forward(ClientPlayerFeature player, double radianAngle) {
    	boolean isImpacted = false;

    	// 攻擊範圍判斷依照此邏輯複製，如有修改，請一併確認 attackShortRange()
        double diffX = player.getLocX() + Math.sin(radianAngle) * VEL;
        double diffY = player.getLocY() - Math.cos(radianAngle) * VEL;

        isImpacted = dealCollision.isCollison((int)Math.round(diffX), (int)Math.round(diffY));

		if(!isImpacted){
			player.setLocX((int)Math.round(diffX));
			player.setLocY((int)Math.round(diffY));
		}

		checkGetItem(player); //只考慮前進後退才會吃到，旋轉不會碰到補給
    }

    private void backward(ClientPlayerFeature player, double radianAngle) {
    	boolean isImpacted = false;

        // 攻擊範圍判斷依照此邏輯複製，如有修改，請一併確認 attackShortRange()
		double diffX = player.getLocX() - Math.sin(radianAngle) * VEL;
		double diffY = player.getLocY() + Math.cos(radianAngle) * VEL;

		isImpacted = dealCollision.isCollison((int)Math.round(diffX), (int)Math.round(diffY));

		if(!isImpacted){
			player.setLocX((int)Math.round(diffX));
			player.setLocY((int)Math.round(diffY));
		}

		checkGetItem(player);//只考慮前進後退才會吃到，旋轉不會碰到補給
    }

    private void turnRight(ClientPlayerFeature player, double faceAngle) {
        // 攻擊範圍判斷依照此邏輯複製，如有修改，請一併確認 attackShortRange()
					player.setFaceAngle(faceAngle + ANGLEVEL);
			}

    private void turnLeft(ClientPlayerFeature player, double faceAngle) {
        // 攻擊範圍判斷依照此邏輯複製，如有修改，請一併確認 attackShortRange()
        player.setFaceAngle(faceAngle - ANGLEVEL);
	}

    private void movingBullet() {

        for (int i = 0; i < allItems.size(); i++) {

        }
    }

	@Override
	public void run() {
		startTime = System.currentTimeMillis();
		tenSeconds = startTime + 10*1000;
		while (true) {
			if(finishGame(300)){// 5分鐘就是300秒
				//do something
			}
			movingPlayer();
            movingBullet();
			checkResurrection();//檢查復活
			checkSupplement();//每十秒補充補包彈藥包
			try {
				Thread.sleep(50); // while(true) + sleep = timer嗎?
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
