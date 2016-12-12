package CDC;

import java.util.ArrayList;
import java.util.Random;

import tcp.tcpServer.RealTcpServer;

public class Cdc implements Runnable {
	private ArrayList<ClientPlayerFeature> allPlayers = new ArrayList<>();
	private ArrayList<ClientItemFeature> allItems = new ArrayList<>();

	//TODO: reset these
	private final static int STOP = -1;
	private final static int FORWARD = 0;
	private final static int BACKWARD = 1;
	private final static int TURNRIGHT = 2;
	private final static int TURNLEFT = 3;

	private static Cdc instance = null;

	public static void main(String[] args) throws InterruptedException {
		// tcp
		RealTcpServer realTcpServer = RealTcpServer.getInstance();
		realTcpServer.initTCPServer();
	}

	public void startUpdatingThread() {
		Thread game = new Thread(instance);
		game.start();
	}

	public void Cdc() {}

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

		// initial location, but TODO:要解決位置重疊的情況
		Random random = new Random();
		int x = random.nextInt(1984) + 1;
		int y = random.nextInt(1984) + 1;

		allPlayers.add(new ClientPlayerFeature(clientNo, nickName, x, y));
	}

	public void addItem(int itemID, int itemType, int x, int y) {
		assert itemID > -1 && itemType > -1;
		assert x > 0 && y > 0;

		//TODO: 物品的初始位置？

		allItems.add(new ClientItemFeature(itemID, itemType, x, y));
	}

	public void updateDirection(int clientNo, int moveCode) {
		assert clientNo > -1;
		assert moveCode > -1 && moveCode < 4;

		//TODO: 確認TCP傳進來的moveCode狀態，0: 前進、1: 後退、2: 右轉、3: 左轉，我覺得-1可以當停止。
		//TODO: 如果是同時按前進跟旋轉呢？

		allPlayers.get(clientNo).setDirection(moveCode);
	}

	//TODO: 碰到物體則等於吃到，感覺要每秒去確認，但感覺會很慢？
	private void checkGetItem(ClientPlayerFeature player) {
		int itemSize = allItems.size();

		for (int j = 0; j < itemSize; j++) {
			ClientItemFeature item = allItems.get(j);

			if (item.getItemType() > 0 && item.getItemType() < 3) continue; //只考慮補給品

			if (Math.abs(item.getLocX() - player.getLocX()) < 16 &&
					Math.abs(item.getLocY() - player.getLocY()) < 16)
				item.setItemOwner(player.getClientNo());
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(50); // while(true) + sleep = timer嗎?
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			int playerSize = allPlayers.size();
			for (int i = 0; i < playerSize; i++) {
				ClientPlayerFeature player = allPlayers.get(i);

				int vel = player.getVel();
				double faceAngle = player.getFaceAngle();
				double angleVel = player.getAngleVel();

				switch (player.getDirection()) {
					//TODO: 用三角函數算前進向量，角度每次角速度
					case FORWARD:
					case BACKWARD:
						double diff = player.getLocX() + Math.sin(faceAngle);
						player.setLocX((int)Math.round(diff) * vel);

						diff = player.getLocY() + Math.cos(faceAngle);
						player.setLocY((int)Math.round(diff) * vel);

						checkGetItem(player);//只考慮前進後退才會吃到，旋轉不會碰到補給
						break;
					case TURNRIGHT:
						player.setFaceAngle(faceAngle + Math.toRadians(angleVel));
						break;
					case TURNLEFT:
						player.setFaceAngle(faceAngle - Math.toRadians(angleVel));
						break;
					case STOP:
						//Don't Move
					default:
						throw new Error("Out of direction!");
				}
			}
		}
	}
}
