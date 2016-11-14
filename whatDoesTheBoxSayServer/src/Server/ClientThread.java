package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;

public class ClientThread extends Thread {

	int myID;

	ClientThread(Socket sc, int ClientID, PlayerManager playerManager) {
		this.myID = ClientID;
		PrintWriter output;
		BufferedReader input;
		try {
			output = new PrintWriter(sc.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(sc.getInputStream()));

			String nickName = recv(input);
			send(output, String.valueOf(ClientID));
			if (ClientID == 0) {
				while (recv(input) == "Start")
					;
				playerManager.startGame = true;
				playerManager.gameStart();
			}
			while (!playerManager.startGame) {
				send(output, "Start");
			}
			while (!playerManager.gameLoaded) {
				// TODO send client count down message
			}
			// after here generate the players[]
			playerManager.players[this.myID].nickName = nickName;

			while (true) {
				// 收自己
				playerManager.players[this.myID] = recvPlayer(input);
				// 運算
				process(playerManager);
				// 送全部
				sendPlayer(output, playerManager.players);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("client" + this.myID + " disconnect");
			e.printStackTrace();
		}
	}

	// 處理收到的訊息
	void process(PlayerManager playerManager) {
		Player myPlayer = playerManager.players[this.myID];
		if (myPlayer.getMoveFlag()) {
			myPlayer.setCollisionFlag(isCollision(playerManager));
			myPlayer.setBonusFlag(isBonus(playerManager));
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
		// TODO 一補包位置判斷，先假設沒吃到
		return false;
	}

	// 是否碰撞
	private boolean isCollision(PlayerManager playerManager) {
		// TODO 一箱子位置判斷，先假設沒碰撞
		return false;

	}

	// 回血
	private void regeneration(PlayerManager playerManager) {
		// TODO 依時間補血，先假設不補血
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
