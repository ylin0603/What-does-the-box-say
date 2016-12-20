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

	private int clientNo = -1, itemId = -1;
	private Gson gson = new Gson();
	private Timer timerBroadcast = new Timer();

	public void startUDPBroadCast() {
		ArrayList<String> allIPAddress =
				RealTcpServer.getInstance().getClientIPTable();

		TimerTask startBroadcast = new TimerTask() {
			@Override
			public void run() {
				ArrayList<EncodedData> encodedData = encapsulateData();
				broadcast(allIPAddress, encodedData);
			}
		};
		timerBroadcast.schedule(startBroadcast, 0, 17); // 20 times/per second
	}

	public void stopBroadCast() {
		timerBroadcast.cancel();

		ArrayList<String> allIPAddress =
				RealTcpServer.getInstance().getClientIPTable();
		ArrayList<EncodedData> stopFlag = new ArrayList<>();
		stopFlag.add(new EncodedData("STOP", "stop client"));

		broadcast(allIPAddress, stopFlag);
	}

	private ArrayList<EncodedData> encapsulateData() {
		String type;
		Cdc instance = Cdc.getInstance();
		ArrayList<ClientPlayerFeature> updatePlayers = instance.getPlayersUpdateInfo();
		ArrayList<ClientItemFeature> updateItems = instance.getItemsUpdateInfo();
		ArrayList<EncodedData> encodedData = new ArrayList<>();

		for (ClientPlayerFeature player : updatePlayers) {
			if (clientNo >= player.getClientNo()) {
				type = "UpdateP";
			} else {
				type = "AddP";
				clientNo = player.getClientNo();
			}

			encodedData.add(new EncodedData(type, gson.toJson(player)));
		}

		for (ClientItemFeature item : updateItems) {
			if (itemId >= item.getItemID()) {
				type = "UpdateI";
			} else {
				type = "AddI";
				itemId = item.getItemID();
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
