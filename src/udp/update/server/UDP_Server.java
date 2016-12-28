package udp.update.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import gui.game.GameManager;
import renderer.data.DynamicObjectManager;
import tcp.tcpServer.RealTcpServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class UDP_Server implements Runnable {
    static DatagramSocket serverSocket = null;
    private Gson gson = new Gson();
    static private String savedMagicWord;

    public static int initUDPServer(String magicWord) {
        savedMagicWord = magicWord;
        Thread serverThread = new Thread(new UDP_Server()); // 產生Thread物件
        try {
            serverSocket = new DatagramSocket();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        serverThread.start();
        return serverSocket.getLocalPort();
    }

    public void run() {
        try {
            byte[] receiveData;
            while (true) {
                receiveData = new byte[10240];
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                // get EncodedData in ArrayList and parse.
                String receiveString =
                        new String(receivePacket.getData()).trim();
                if (receiveString.startsWith(savedMagicWord)) {
                    receiveString =
                            receiveString.replaceFirst(savedMagicWord, "");
                    parseData(receiveString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

	private void parseData(String receiveString) {
		ArrayList<EncodedData> encodedData = gson.fromJson(receiveString,
				new TypeToken<ArrayList<EncodedData>>() {}.getType());

		ClientPlayerFeature player = null;
		ClientItemFeature item = null;
		List<ClientBulletFeature> allBullet;
		DynamicObjectManager instance = DynamicObjectManager.getInstance();
		for (EncodedData eachEnData : encodedData) {
			switch (eachEnData.getType()) {
				case "UpdateP":
					player = gson.fromJson(eachEnData.getData(),
							ClientPlayerFeature.class);
					instance.updateVirtualCharacter(player.getClientNo(),
							player.getWeaponType(), player.getNickname(),
							player.getLocX(), player.getLocY(),
							player.getFaceAngle(), player.getHP(),
							player.getKillCount(), player.getDeadCount(),
							player.getBulletCount(), player.isAttackFlag(),
							player.isAttackedFlag(), player.isCollisionFlag(),
							player.isDead());
					if(player.isAttackFlag())
						System.out.println("ATTACK is true");
					if(player.isAttackedFlag())
						System.out.println("IsATTACKED is true");
					break;
				case "AddP":
					player = gson.fromJson(eachEnData.getData(),
							ClientPlayerFeature.class);
					instance.addVirtualCharacter(player.getClientNo(),
							player.getNickname());

					System.out.println("Add Player");

					break;
				case "UpdateI":
					item = gson.fromJson(eachEnData.getData(),
							ClientItemFeature.class);
					instance.updateItem(item.getItemID(), item.isDead(),
										item.getItemOwner(), item.getLocX(), item.getLocY());

					break;
				case "AddI":
					item = gson.fromJson(eachEnData.getData(),
							ClientItemFeature.class);
					instance.addItem(item.getItemType(), item.getItemID(),
							item.isDead(), item.getLocX(), item.getLocY());

					System.out.println("Add Item");

					break;
				case "Bullet":
					allBullet = gson.fromJson(eachEnData.getData(),
							new TypeToken<ArrayList<ClientBulletFeature>>() {}.getType());

					instance.updateBullets(allBullet);
					break;
				case "STOP":
					//call Game的function跳出總計分板，停止遊戲
                    GameManager.getInstance().openRankBoard();
					System.out.println("STOP: " + eachEnData.getData());

					break;
				default:
					System.out.println("ERROR");
					throw new Error("ERROR");
			}
		}
	}
}
