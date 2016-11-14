package Server;

import java.util.Random;

public class PlayerManager {
	final int mapSizeX = 1000;
	final int mapSizeY = 1000;

	final int playerSizeX = 30;
	final int playerSizeY = 30;

	int totalClient = 0;
	boolean startGame = false;
	boolean gameLoaded = false;

	Player[] players;
	Box[] box;
	Bonus[] bonus;

	PlayerManager() {

	}

	void gameStart() {
		// TODO startgame,產生players陣列,new出個別player，產生box陣列
		Random random = new Random();
		players = new Player[totalClient];
		for (int i = 0; i < totalClient; i++) {
			int tempLocX;
			int tempLocY;
			int tempFaceAngle;
			do {
				tempLocX = random.nextInt();
				tempLocY = random.nextInt();
				tempFaceAngle = random.nextInt();
			} while (collision(tempLocX, tempLocY, tempFaceAngle));
			players[i] = new Player(tempLocX, tempLocY, tempFaceAngle);
		}

		gameLoaded = true;// after finished load,set true
	}

	boolean collision(int locX, int locY, int faceAngle) {
		for (int i = 0; i < totalClient; i++) {
			// players[i]

		}
		return false;
	}
}
