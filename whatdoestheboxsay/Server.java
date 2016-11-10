package whatdoestheboxsay;

public class Server {
	static Player[] player;
	int me;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// 每個client 擁有一個自己的server thread，持續等待所屬的client送訊息
	void serverThread() {
		while (true) {
			// 收自己
			player[me] = recv();
			// 運算
			process();
			// 送全部
			send(player);
		}
	}

	// 處理收到的訊息
	static void process() {
		Player myPlayer = player[me];
		if (myPlayer.getMoveFlag()) {
			myPlayer.setBonusFlag(isBonus());
			myPlayer.setCollisionFlag(isCollision());
		} else {
			// 原地補血
			regeneration();
		}
		if (myPlayer.getAttackFlag()) {
			// 做攻擊
			attack();
		}
	}
}
