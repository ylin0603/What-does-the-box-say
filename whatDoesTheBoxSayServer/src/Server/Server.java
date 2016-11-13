package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	public static int port = 8888;
	static int totalClient = 0;

	public static void main(String[] args) {
		System.out.println("server");

		PlayerManager playerManager = new PlayerManager();

		ServerSocket ss;
		try {
			ss = new ServerSocket(port);
			System.out.println("server" + ss.getLocalSocketAddress().toString());

			while (true) { // every server thread
				new ServerThread(ss.accept(), totalClient, playerManager).start();
				playerManager.totalClient++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
