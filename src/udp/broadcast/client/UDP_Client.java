package udp.broadcast.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import CDC.ClientItemFeature;
import CDC.ClientPlayerFeature;
import CDC.Cdc;
import tcp.tcpServer.RealTcpServer;

import com.google.gson.Gson;

public class UDP_Client {

	private Gson gson = new Gson();

	// The method starts the UDP Broadcast thread.
	public void startUDPBroadCast() {
		ArrayList<String> allIPAddress =
				RealTcpServer.getInstance().getClientIPTable();

		Timer timerBroadcast = new Timer();
		TimerTask startBroadcast = new TimerTask() {
			@Override
			public void run() {
				ArrayList<EncodedData> encodedData = encapsulateData();
				broadcast(allIPAddress, encodedData);
			}
		};
		timerBroadcast.schedule(startBroadcast, 0, 200); // 5 times/per second
	}

	private int clientNo = -1, itemId = -1;

	private ArrayList<EncodedData> encapsulateData() {
		ArrayList<ClientPlayerFeature> updatePlayers =
				Cdc.getInstance().getPlayersUpdateInfo();
		ArrayList<ClientItemFeature> updateItems =
				Cdc.getInstance().getItemsUpdateInfo();
		ArrayList<EncodedData> encodedData = new ArrayList<EncodedData>();

		String type;
		for (ClientPlayerFeature player : updatePlayers) {
			if (clientNo < player.getPlayerId()) {
				type = "AddP";
				clientNo = player.getPlayerId();
			} else {
				type = "UpdateP";
			}
			encodedData.add(new EncodedData(type, gson.toJson(player)));
		}
		for (ClientItemFeature item : updateItems) {
			if (itemId < item.getItemIndex()) {
				type = "AddI";
				itemId = item.getItemIndex();
			} else {
				type = "UpdateI";
			}
			encodedData.add(new EncodedData(type, gson.toJson(item)));
		}

		return encodedData;
	}

	private void broadcast(ArrayList<String> allIP,
			ArrayList<EncodedData> encodedData) {
		String jsonEncodedData = gson.toJson(encodedData);
		byte[] sendData = jsonEncodedData.getBytes();

		DatagramSocket clientSocket = null;
		InetAddress IPAddress;
		try {
			for (String ip : allIP) {
				clientSocket = new DatagramSocket();
				IPAddress = InetAddress.getByName(ip);

				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, 3335);
				clientSocket.send(sendPacket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			clientSocket.close();
		}
	}
}
