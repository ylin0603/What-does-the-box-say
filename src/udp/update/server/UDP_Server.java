package udp.update.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import renderer.data.DynamicObjectManager;

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
            serverSocket = new DatagramSocket(3335);

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                // get EncodedData in ArrayList and parse.
                String receiveString = new String(receivePacket.getData()).trim();
                parseData(receiveString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }
    private Gson gson = new Gson();
    public void parseData(String receiveString) {
        ArrayList<EncodedData> encodedData
                = gson.fromJson(receiveString, new TypeToken<ArrayList<EncodedData>>(){}.getType());

        for (EncodedData eachEnData : encodedData) {
            ClientPlayerFeature player = null;
            ClientItemFeature item = null;
            switch (eachEnData.getType()) {
                case "UpdateP":
                    player = gson.fromJson(eachEnData.getData(), ClientPlayerFeature.class);
                    DynamicObjectManager.getInstance()
                            .updateVirtualCharacter(player.getPlayerId(), player.getDirection(),
                                    player.getVelocity(), player.getLocationX(), player.getLocationY());

                    System.out.println("Update Player");
                    break;
                case "AddP":
                    player = gson.fromJson(eachEnData.getData(), ClientPlayerFeature.class);
                    DynamicObjectManager.getInstance().addVirtualCharacter(player.getPlayerId());

                    System.out.println("Add Player");
                    break;
                case "UpdateI":
                    item = gson.fromJson(eachEnData.getData(), ClientItemFeature.class);
                    DynamicObjectManager.getInstance()
                            .updateItem(item.getItemIndex(), item.isShared(), item.getItemOwner());

                    System.out.println("Update Item");
                    break;
                case "AddI":
                    item = gson.fromJson(eachEnData.getData(), ClientItemFeature.class);
                    DynamicObjectManager.getInstance().addItem(item.getType(), item.getItemIndex(),
                            item.isShared(), item.getLocationX(), item.getLocationY());

                    System.out.println("Add Item");
                    break;
                default:
                    System.out.println("ERROR");
                    throw new Error("ERROR");
            }
        }
    }
}
