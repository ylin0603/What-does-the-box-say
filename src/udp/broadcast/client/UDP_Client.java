package udp.broadcast.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import com.google.gson.Gson;

public class UDP_Client {

	public static void main(String[] args) {
		new UDP_Client().startUDPBroadCast();
	}

	//TEST///////////////////////////////////
	public ArrayList<String> getClientIPTables() {
		ArrayList<String> IPTables = new ArrayList<String>();
		IPTables.add("127.0.0.1");
		
		return IPTables;
	}
	public ArrayList<String> getUpdateInfo() {	
		ArrayList<String> updateInfo = new ArrayList<String>();
		updateInfo.add("A/EAST");
		updateInfo.add("B/WEST");
		
		return updateInfo;
	}
	/////////////////////////////////////////
	
	// The method starts the UDP Broadcast thread.
	public void startUDPBroadCast() {
		ArrayList<String> allIPAddress = getClientIPTables();
		ArrayList<String> updateInfo = getUpdateInfo();
		
		ArrayList<EncodedData> encodedData = new ArrayList<EncodedData>();
		for(String eachInfo : updateInfo)
			encodedData.add(new EncodedData("ADD", eachInfo)); //after toString()
			
		broadcast(allIPAddress, encodedData); //First Time.
		
		//以下是update用
		Timer timerBroadcast = new Timer();
		TimerTask startBroadcast = new TimerTask() {
			@Override
			public void run() {
				ArrayList<String> updateInfo = getUpdateInfo();
				encodedData.clear();
	
				for(String eachInfo : updateInfo)
					encodedData.add(new EncodedData("UPDATE", eachInfo));
				
				broadcast(allIPAddress, encodedData);
			}
		};
		timerBroadcast.schedule(startBroadcast, 0, 200); // 5 times/per second
	}
	
	public void broadcast(ArrayList<String> allIPAddress, ArrayList<EncodedData> encodedData) {		
		Gson gson = new Gson();
		String jsonEncodedData = gson.toJson(encodedData);

		byte[] sendData = new byte[1024];
		sendData = jsonEncodedData.getBytes();

		DatagramSocket clientSocket = null;
		InetAddress IPAddress;
		try {
			for(String ip : allIPAddress){
				clientSocket = new DatagramSocket();
				IPAddress = InetAddress.getByName(ip);
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 3334);
				clientSocket.send(sendPacket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			clientSocket.close();
		}
	}
}
