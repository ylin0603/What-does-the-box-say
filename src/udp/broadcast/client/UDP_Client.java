package udp.broadcast.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.List;
=======
import java.util.HashSet;
import java.util.List;
import java.util.Set;
>>>>>>> origin/dev

import CDC.ClientBulletFeature;
import CDC.ClientItemFeature;
import CDC.ClientPlayerFeature;

import com.google.gson.Gson;

public class UDP_Client {

    private Gson gson = new Gson();
    private Set<Integer> clientNumberSet = new HashSet<Integer>();
    private Set<Integer> itemNumberSet = new HashSet<Integer>();
    private static UDP_Client instance;

    public static UDP_Client getInstance() {
        if (instance == null)
            instance = new UDP_Client();
        return instance;
    }

    public void stopBroadCast(ArrayList<String> allIPAddress) {
        // allIPAddress = RealTcpServer.getInstance().getClientIPTable();
        ArrayList<EncodedData> stopFlag = new ArrayList<>();
        stopFlag.add(new EncodedData("STOP", "stop client"));

        broadcast(allIPAddress, stopFlag);
    }

    public ArrayList<EncodedData> encapsulateData(
            List<ClientPlayerFeature> updatePlayers,
            List<ClientItemFeature> updateItems,
            List<ClientBulletFeature> allBullets) {
        String type;
        ArrayList<EncodedData> encodedData = new ArrayList<>();

        for (ClientPlayerFeature player : updatePlayers) {
            if (clientNumberSet.contains(player.getClientNo())) {
                type = "UpdateP";
            } else {
                type = "AddP";
                clientNumberSet.add(player.getClientNo());
            }
            encodedData.add(new EncodedData(type, gson.toJson(player)));
        }

        for (ClientItemFeature item : updateItems) {
            if (itemNumberSet.contains(item.getItemID())) {
                type = "UpdateI";
            } else {
                type = "AddI";
                itemNumberSet.add(item.getItemID());
            }

            encodedData.add(new EncodedData(type, gson.toJson(item)));
        }

        encodedData.add(new EncodedData("Bullet", gson.toJson(allBullets)));

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
