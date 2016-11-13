package whatdoestheboxsay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;

public class ServerThread extends Thread {

	int myID;

	ServerThread(Socket sc, int ClientID, TotalPlayer totalplayer) {
		this.myID = ClientID;
		PrintWriter output;
		BufferedReader input;
		try {
			output = new PrintWriter(sc.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(sc.getInputStream()));

			totalplayer.player[myID].nickName = recv(input);
			send(output, String.valueOf(ClientID));
			if (ClientID == 0) {
				recv(input);
				totalplayer.startGame = true;
				totalplayer.gameStart();
			}
			while (!totalplayer.startGame) {

			}
			while (true) {
				// 收自己
				totalplayer.player[this.myID] = recvPlayer(input);
				// 運算
				process(myID, totalplayer);
				// 送全部
				sendPlayer(output, totalplayer.player);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 處理收到的訊息
	static void process(int myID, TotalPlayer totalplayer) {
		Player myPlayer = totalplayer.player[myID];
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

	private static boolean isBonus() {
		return false;

	}

	private static boolean isCollision() {
		return false;

	}

	private static void regeneration() {

	}

	private static void attack() {
		// TODO Auto-generated method stub

	}

	static Player recvPlayer(BufferedReader input) throws IOException {
		String inputLine;
		Player player;
		Gson gson = new Gson();
		inputLine = recv(input);
		player = gson.fromJson(inputLine, Player.class);
		return player;
	}

	static String recv(BufferedReader input) throws IOException {
		String inputLine;
		while ((inputLine = input.readLine()) != null) {
			break;
		}
		return inputLine;
	}

	static void sendPlayer(PrintWriter output, Player[] players) {
		Gson gson = new Gson();
		String playersString = gson.toJson(players);
		send(output, playersString);
	}

	static void send(PrintWriter output, String outputString) {
		output.println(outputString);
	}
}
