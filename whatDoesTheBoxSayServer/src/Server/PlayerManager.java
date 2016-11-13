package Server;

import java.util.Random;

public class PlayerManager {
	final int mapSizeX = 1000;
	final int mapSizeY = 1000;

	final int playerSizeX = 30;
	final int playerSizeY = 30;

	int totalClient = 0;
	boolean startGame = false;

	Player[] players;
	Box[] box;
	Bonus[] bonus;

	PlayerManager() {

	}

	void gameStart() {
		Random random = new Random();
		players = new Player[totalClient];
		// TODO startgame
	}
}
