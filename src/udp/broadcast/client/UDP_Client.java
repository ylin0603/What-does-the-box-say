package udp.broadcast.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import com.google.gson.Gson;

public class UDP_Client {

	public static void main(String[] args) {
		new UDP_Client().startUDPBroadCast();
	}

	//TEST///////////////////////////////////
	public Vector<String> getClientIPTables() {
		Vector<String> IPTables = new Vector<String>();
		IPTables.addElement("127.0.0.1");
		
		return IPTables;
	}
	public Vector<String> getUpdateInfo() {	
		Vector<String> updateInfo = new Vector<String>();
		updateInfo.addElement("A/EAST");
		updateInfo.addElement("B/WEST");
		
		return updateInfo;
	}
	/////////////////////////////////////////
	
	// The method starts the UDP Broadcast thread.
	public void startUDPBroadCast() {
		Vector<String> allIPAddress = getClientIPTables();
		Vector<String> updateInfo = getUpdateInfo();
		
		Vector<EncodedData> encodedData = new Vector<EncodedData>();
		for(String eachInfo : updateInfo)
			encodedData.addElement(new EncodedData("ADD", eachInfo)); //after toString()
			
		broadcast(allIPAddress, encodedData); //First Time.
		
		//以下是update用
		Timer timerBroadcast = new Timer();
		TimerTask startBroadcast = new TimerTask() {
			@Override
			public void run() {
				Vector<String> updateInfo = getUpdateInfo();
				encodedData.clear();
	
				for(String eachInfo : updateInfo)
					encodedData.addElement(new EncodedData("UPDATE", eachInfo));
				
				broadcast(allIPAddress, encodedData);
			}
		};
		timerBroadcast.schedule(startBroadcast, 0, 200); // 5 times/per second
	}
	
	public void broadcast(Vector<String> allIPAddress, Vector<EncodedData> encodedData) {		
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
