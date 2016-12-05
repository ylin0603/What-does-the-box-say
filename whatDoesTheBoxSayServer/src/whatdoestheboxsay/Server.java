package whatdoestheboxsay;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

import com.google.gson.Gson;

public class Server {
	// const variable

	public static int port = 8888;
	static int totalClient = 0;

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("server");

		TotalPlayer totalplayer = new TotalPlayer();

		ServerSocket ss;
		try {
			ss = new ServerSocket(port);
			System.out.println("server" + ss.getLocalSocketAddress().toString());

			while (true) { // every server thread
				new ServerThread(ss.accept(), totalClient, totalplayer).start();
				totalplayer.totalClient++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
