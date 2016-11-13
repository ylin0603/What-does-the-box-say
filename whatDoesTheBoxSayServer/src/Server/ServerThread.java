package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;

public class ServerThread extends Thread {

	int myID;

	ServerThread(Socket sc, int ClientID, PlayerManager playerManager) {
		this.myID = ClientID;
		PrintWriter output;
		BufferedReader input;
		try {
			output = new PrintWriter(sc.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(sc.getInputStream()));

			playerManager.players[myID].nickName = recv(input);
			send(output, String.valueOf(ClientID));
			if (ClientID == 0) {
				recv(input);
				playerManager.startGame = true;
				playerManager.gameStart();
			}
			while (!playerManager.startGame) {

			}
			while (true) {
				// 收自己
				playerManager.players[this.myID] = recvPlayer(input);
				// 運算
				process(myID, playerManager);
				// 送全部
				sendPlayer(output, playerManager.players);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 處理收到的訊息
	void process(int myID, PlayerManager playerManager) {
		Player myPlayer = playerManager.players[myID];
		if (myPlayer.getMoveFlag()) {
			myPlayer.bonusFlag = isBonus(playerManager);
			myPlayer.setBonusFlag(isBonus(playerManager));
			myPlayer.setCollisionFlag(isCollision(playerManager));
		} else {
			// 原地補血
			regeneration(playerManager);
		}
		if (myPlayer.getAttackFlag()) {
			// 做攻擊
			attack();
		}
	}

	// 是否吃到寶物
	private boolean isBonus(PlayerManager playerManager) {
		return false;

	}

	// 是否碰撞
	private boolean isCollision(PlayerManager playerManager) {
		return false;

	}

	// 回血
	private void regeneration(PlayerManager playerManager) {

	}

	// 攻擊
	private void attack() {
		// TODO Auto-generated method stub

	}

	// 收Player物件
	Player recvPlayer(BufferedReader input) throws IOException {
		String inputLine;
		Player player;
		Gson gson = new Gson();
		inputLine = recv(input);
		player = gson.fromJson(inputLine, Player.class);
		return player;
	}

	// 收字串
	String recv(BufferedReader input) throws IOException {
		String inputLine;
		while ((inputLine = input.readLine()) != null) {
			break;
		}
		return inputLine;
	}

	// 送Player物件
	void sendPlayer(PrintWriter output, Player[] players) {
		Gson gson = new Gson();
		String playersString = gson.toJson(players);
		send(output, playersString);
	}

	// 送字串
	void send(PrintWriter output, String outputString) {
		output.println(outputString);
	}
}
