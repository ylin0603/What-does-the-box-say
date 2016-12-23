package udp.broadcast.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import CDC.ClientItemFeature;
import CDC.ClientPlayerFeature;

import com.google.gson.Gson;

public class UDP_Client {

	private int clientNo = -1, itemId = -1;
	private Gson gson = new Gson();
	private static UDP_Client instance;

	public static UDP_Client getInstance() {
		if (instance == null)
			instance = new UDP_Client();
		return instance;
	}

	public void stopBroadCast(ArrayList<String> allIPAddress) {
		//allIPAddress = RealTcpServer.getInstance().getClientIPTable();
		ArrayList<EncodedData> stopFlag = new ArrayList<>();
		stopFlag.add(new EncodedData("STOP", "stop client"));

		broadcast(allIPAddress, stopFlag);
	}

    public ArrayList<EncodedData> encapsulateData(
            List<ClientPlayerFeature> updatePlayers,
            List<ClientItemFeature> updateItems) {
        String type;
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

	public void broadcast(ArrayList<String> allIP,
			ArrayList<EncodedData> encodedData) {
		String jsonEncodedData = gson.toJson(encodedData);
		byte[] sendData = jsonEncodedData.getBytes();

		DatagramSocket clientSocket = null;
		InetAddress IPAddress;
		try {
			for (String ip : allIP) {
				clientSocket = new DatagramSocket();
				IPAddress = InetAddress.getByName(ip);

				DatagramPacket sendPacket =
						new DatagramPacket(sendData, sendData.length, IPAddress,
								3335);
				clientSocket.send(sendPacket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			clientSocket.close();
		}
	}
}
