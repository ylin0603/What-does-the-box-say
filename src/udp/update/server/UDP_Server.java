package udp.update.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Vector;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UDP_Server implements Runnable {
	
	public static void main(String[] args) {
		new UDP_Server().initUDPServer();
	}
	
	public void initUDPServer() {
		Thread serverThread = new Thread(new UDP_Server()); // 產生Thread物件
	 	serverThread.start(); 
	}
	
	public void run() {
		DatagramSocket serverSocket = null;
		try {
			byte[] receiveData = new byte[1024];
			serverSocket = new DatagramSocket(3334);
	    	
			while(true) {
				Gson gson = new Gson();
	    		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    		serverSocket.receive(receivePacket);
	  
	    		// parsing and getting EncodedData in Vector
	    		String receiveString = new String(receivePacket.getData()).trim();
	    		Vector<EncodedData> encodedData = gson.fromJson(receiveString, new TypeToken<Vector<EncodedData>>(){}.getType()); 		
	    		
    			for(EncodedData eachEnData : encodedData){
    				switch(eachEnData.getType()){
    				case "ADD":
    					System.out.println("ADD");
    					System.in.read();
    					//call addVirutalCharacter or addItem to DOM
    					break;
    				case "UPDATE":
    					System.out.println("UPDATE");
    					System.in.read();
    					//call updateVirtualCharacter or updateItem to DOM
    					break;
    				default:
        				System.in.read();
        				System.out.println("ERROR");	
    				}
    			}
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			serverSocket.close();	
		}
	}
}
