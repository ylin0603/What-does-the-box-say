package udp.broadcast.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import CDC.ClientBulletFeature;
import CDC.ClientItemFeature;
import CDC.ClientPlayerFeature;
import tcp.tcpServer.RealTcpServer;

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

    public void stopBroadCast(ArrayList<String> allIPAddress,
            ArrayList<Integer> allPort) {
        // allIPAddress = RealTcpServer.getInstance().getClientIPTable();
        ArrayList<EncodedData> stopFlag = new ArrayList<>();
        stopFlag.add(new EncodedData("STOP", "stop client"));

        broadcast(allIPAddress, allPort, stopFlag);
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

    public void broadcast(ArrayList<String> allIP, ArrayList<Integer> allPort,
            ArrayList<EncodedData> encodedData) {
        String jsonEncodedData =
                RealTcpServer.magicWord + gson.toJson(encodedData);
        byte[] sendData = jsonEncodedData.getBytes();

        DatagramSocket clientSocket = null;
        InetAddress IPAddress;
        try {
            for (int i = 0; i < allIP.size(); i++) {
                String ip = allIP.get(i);
                int port = allPort.get(i);
                clientSocket = new DatagramSocket();
                IPAddress = InetAddress.getByName(ip);

                DatagramPacket sendPacket = new DatagramPacket(sendData,
                        sendData.length, IPAddress, port);
                clientSocket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientSocket.close();
        }
    }
}
