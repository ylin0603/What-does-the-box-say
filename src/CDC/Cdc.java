package CDC;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import tcp.tcpServer.RealTcpServer;

public class Cdc implements Runnable {
	Random random = new Random();
	private long startTime;
	private long tenSeconds;
	private ArrayList<ClientPlayerFeature> allPlayers = new ArrayList<>();
	private ArrayList<ClientItemFeature> allItems = new ArrayList<>();
	

	//TODO: reset these
	private final static int STOP = -1;
	private final static int FORWARD = 0;
	private final static int BACKWARD = 1;
	private final static int TURNRIGHT = 2;
	private final static int TURNLEFT = 3;

	private final static int VEL = 2;
	private final static double ANGLEVEL = 7;//degree

	private static Cdc instance = null;

	public final static int BOX_SIZE = 16;// this is two are read for all server
	public final static int MAP_SIZE = 1024;

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
		int[] loc = giveRandomLocation(); //initial position
		allPlayers.add(new ClientPlayerFeature(clientNo, nickName, loc[0], loc[1]));
	}

	public int[] giveRandomLocation() {
		int []location = new int[2];
		int playerSize = allPlayers.size();
		int itemSize = allItems.size();
		int xRange, yRange; //the distance between two objects.
		boolean isOverlapped = true;
		while(isOverlapped) {
		    isOverlapped = false;
			
			location[0] = random.nextInt(MAP_SIZE - BOX_SIZE + 1);
			location[1] = random.nextInt(MAP_SIZE - BOX_SIZE + 1);
			if(allPlayers.size()>0){
			for(int curPlayer = 0; curPlayer < playerSize; curPlayer ++){
				int curPlayerLocx = allPlayers.get(curPlayer).getLocX();
				int curPlayerLocy = allPlayers.get(curPlayer).getLocY();
				xRange = Math.abs(location[0] - curPlayerLocx);
				yRange = Math.abs(location[1] - curPlayerLocy);
				if((xRange <= BOX_SIZE) && (yRange <= BOX_SIZE)) // overlapped
				{
				    isOverlapped = true;
					break;}
			}}
			if(isOverlapped) continue;
			for(int curItem = 0; curItem < itemSize; curItem ++){
				int curItemLocx = allItems.get(curItem).getLocX();
				int curItemLocy = allItems.get(curItem).getLocY();
				xRange = Math.abs(location[0] - curItemLocx);
				yRange = Math.abs(location[1] - curItemLocy);
				if((xRange <= BOX_SIZE) && (yRange <= BOX_SIZE)){
				    isOverlapped = true;
				    break;}
			}
			if(isOverlapped) continue;

		}
		return location;
		
	}

	public void rebornPlayer(ClientPlayerFeature player){
		player.setWeaponType(0);
		int[] loc = giveRandomLocation();
		player.setLocX(loc[0]);
		player.setLocY(loc[1]);
		player.setFaceAngle(0.0);
		player.setHP(100);
		player.setBulletCount(2);
		player.setAttackFlag(false);
		player.setAttackedFlag(false);
		player.setCollisionFlag(false);
		player.setDead(false);
		//player.setLastMoveTime();
	}

	public void initFakeBox(){
		for(int fakeBoxNum = 0; fakeBoxNum < 30; fakeBoxNum ++){
			int[] loc = giveRandomLocation();
			allItems.add(new ClientItemFeature(fakeBoxNum, 0, loc[0], loc[1]));
		}
	}

	public void rebornFakeBox(ClientItemFeature fakeBox){
		int[] loc = giveRandomLocation();
		fakeBox.setLocX(loc[0]);
		fakeBox.setLocY(loc[1]);
		fakeBox.setDead(false);
		fakeBox.setCollision(false);
	}

	public void initBloodPackge(){
		int[] loc = giveRandomLocation();
		allItems.add(new ClientItemFeature(30, 1, loc[0], loc[1]));
	}

	public void initBulletPackge(){
		int[] loc = giveRandomLocation();
		allItems.add(new ClientItemFeature(31, 2, loc[0], loc[1]));

	}

	//to reborn bullet or blood packages
	public void rebornFunctionalPack(ClientItemFeature item){
		item.setFaceAngle(0.0);
		item.setDead(false);
		item.setCollision(false);
		int[] loc = giveRandomLocation();
		item.setLocX(loc[0]);
		item.setLocY(loc[1]);
	}

	public void gameItemsInital(){
		initFakeBox();
		initBloodPackge();
		initBulletPackge();
	}
	//不知道還需不需要，一開始都initial好了
	/*public void addItem(int itemID, int itemType, int x, int y) {
		assert itemID > -1 && itemType > -1;
		assert x > 0 && y > 0;

		//itemID要從30開始
		allItems.add(new ClientItemFeature(itemID, itemType, x, y));
	}*/

    public void updateDirection(int clientNo, boolean[] moveCode) {
		assert clientNo > -1;
        assert moveCode.length == 5;
        // 目前是將所有按住的按鍵記下來

		allPlayers.get(clientNo).setDirection(moveCode);
	}

	//TODO: 碰到物體則等於吃到，感覺要每秒去確認，但感覺會很慢？
	private void checkGetItem(ClientPlayerFeature player) {
		int itemSize = 32;
		int itemType;
		boolean isImpacted = false;
		for(int currItem = 30; currItem < itemSize; currItem++){
			isImpacted = Collision.isCollison(allItems.get(currItem), player);
			if(isImpacted){
				itemType = allItems.get(currItem).getItemType();
				switch(itemType){
					case 1:
						player.addHP(60);
						allItems.get(currItem).setDead(true);
						break;

					case 2:
						player.addBullet(1);
						allItems.get(currItem).setDead(true);
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
			if(allItems.get(30).isDead()){
				rebornFunctionalPack(allItems.get(30));
			}
			if(allItems.get(31).isDead()){
				rebornFunctionalPack(allItems.get(31));
			}
			
			/*int[] loc = giveRandomLocation();
			allItems.get(30).setDead(false);
			allItems.get(30).setLocX(loc[0]);
			allItems.get(30).setLocY(loc[1]);
			loc = giveRandomLocation();
			allItems.get(31).setDead(false);
			allItems.get(31).setLocX(loc[0]);
			allItems.get(31).setLocY(loc[1]);*/
			//rebornFunctionalPack(allItems.get(30));
			//rebornFunctionalPack(allItems.get(31));
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
	
	private boolean moveCollision(int x, int y, ClientPlayerFeature player){
		boolean isImpacted = false;
		boolean colliHappened = false;
		for (ClientPlayerFeature collisionPlayer : allPlayers) {
            if (collisionPlayer.getClientNo() == player.getClientNo())
                continue;
            isImpacted = Collision.isCollison(x,y, collisionPlayer);
        	if(isImpacted){
        		colliHappened = true;
        		break;
        	}
        }
		for (ClientItemFeature collisionItem : allItems) {// only 30 fake boxes
															// will collision
			if (collisionItem.getItemType() != 0)
				continue;
        		isImpacted = Collision.isCollison(x, y, collisionItem);
        	if(isImpacted){
        		colliHappened = true;
        		break;
        	}
        }
        return colliHappened;
	}

	private boolean finishGame (int gameTime){
		long now = System.currentTimeMillis();
		if(now - startTime <= gameTime*1000)
			return false;
		else
			return true;
	}

    private void forward(ClientPlayerFeature player, double radianAngle) {

    	// 攻擊範圍判斷依照此邏輯複製，如有修改，請一併確認 attackShortRange()
        double diffX = player.getLocX() + Math.sin(radianAngle) * VEL;
        double diffY = player.getLocY() - Math.cos(radianAngle) * VEL;

		if(!moveCollision((int)Math.round(diffX),(int)Math.round(diffY),player)){
			player.setLocX((int)Math.round(diffX));
			player.setLocY((int)Math.round(diffY));
		}

		checkGetItem(player); //只考慮前進後退才會吃到，旋轉不會碰到補給
    }

    private void backward(ClientPlayerFeature player, double radianAngle) {

        // 攻擊範圍判斷依照此邏輯複製，如有修改，請一併確認 attackShortRange()
		double diffX = player.getLocX() - Math.sin(radianAngle) * VEL;
		double diffY = player.getLocY() + Math.cos(radianAngle) * VEL;

		if(!moveCollision((int)Math.round(diffX),(int)Math.round(diffY),player)){
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

    public void attack(int clientNo) {
        new Attack(clientNo, allPlayers, allItems);
    }

    public void changeWeapon(int clientNo) {
        allPlayers.get(clientNo)
                .setWeaponType(1 - allPlayers.get(clientNo).getWeaponType());
        // 1 to 0
        // 0 to 1
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
