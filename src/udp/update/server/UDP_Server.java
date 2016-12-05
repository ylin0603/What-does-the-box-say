package udp.update.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import renderer.data.DynamicObjectManager;

public class UDP_Server implements Runnable {

	/*public static void main(String[] args) {
		new UDP_Server().initUDPServer();
	}*/

    public void initUDPServer() {
        Thread serverThread = new Thread(new UDP_Server()); // 產生Thread物件
        serverThread.start();
    }

    public void run() {
        DatagramSocket serverSocket = null;
        try {
            byte[] receiveData = new byte[1024];
            serverSocket = new DatagramSocket(3334);

            while (true) {
                Gson gson = new Gson();
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                // parsing and getting EncodedData in Vector
                String receiveString = new String(receivePacket.getData()).trim();
                ArrayList<EncodedData> encodedData = gson.fromJson(receiveString, new TypeToken<ArrayList<EncodedData>>() {
                }.getType());

                for (EncodedData eachEnData : encodedData) {
                    switch (eachEnData.getType()) {
                        case "ADD":
                            System.out.println("ADD");
                            System.in.read();
                            DynamicObjectManager.getInstance().addVirtualCharacter(0);
                            break;
                        case "ADDI":
                            String name = null;
                            int index = 0;
                            Boolean shared = true;
                            int x = 10, y = 10;
                            DynamicObjectManager.getInstance().addItem(name, index, shared, x, y);
                        case "UPDATE":
                            System.out.println("UPDATE");
                            System.in.read();
                            //DynamicObjectManager.getInstance().updateVirtualCharacter();
                            break;
                        case "UPDATEI":
                            //DynamicObjectManager.getInstance().updateItem();
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
