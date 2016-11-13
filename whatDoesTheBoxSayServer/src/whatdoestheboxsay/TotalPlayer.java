package whatdoestheboxsay;

import java.util.Random;

public class TotalPlayer {
	final int mapSizeX = 1000;
	final int mapSizeY = 1000;

	final int playerSizeX = 30;
	final int playerSizeY = 30;
	static int totalClient = 0;
	static boolean startGame = false;
	Player[] player;
	Box[] box;
	Bonus[] bonus;

	TotalPlayer() {
		Random random = new Random();
		player = new Player[totalClient];
	}

	void gameStart() {

	}
}
