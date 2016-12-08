package tcp.tcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RealTcpServer implements Runnable {
	final int port = 8888;
	ServerSocket ss;
	static int totalClient = 0;
	volatile ArrayList<String> IPtable = new ArrayList<String>();
	private static RealTcpServer realTcpServer;

	private RealTcpServer() {

	}

	public static RealTcpServer getInstance() {
		if (realTcpServer == null) {
			realTcpServer = new RealTcpServer();
		}
		return realTcpServer;
	}

	public void initTCPServer() {
		try {
			ss = new ServerSocket(port);
			System.out
					.println("server" + ss.getLocalSocketAddress().toString());
			Thread t = new Thread(this);
			t.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getClientIPTable() {
		return IPtable;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			// every server thread
			Socket so;
			try {
				so = ss.accept();
				String s1 = so.getInetAddress().toString().replace("/", "");
				IPtable.add(s1);
				TcpServerThread tcpServerThread =
						new TcpServerThread(so, totalClient);
				if (totalClient == 0) {

				}
				Thread t = new Thread(tcpServerThread);
				t.start();
				totalClient++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
