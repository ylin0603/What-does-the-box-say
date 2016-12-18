package tcp.tcpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import CDC.Cdc;
import udp.broadcast.client.UDP_Client;

public class TcpServerThread implements Runnable {
    private int ClientID = 0;
    static int totalClient;
    private PrintWriter output;
    private BufferedReader input;
    volatile static public boolean load = false;
    private String myName = null;
    private static ArrayList<String> nameList = new ArrayList<String>();
    volatile static private int loadNum = 0;
    Gson gson;


    TcpServerThread() {
        gson = new Gson();
    }

    public TcpServerThread(Socket sc, int ClientID) {
        this.ClientID = ClientID;
        TcpServerThread.totalClient = ClientID + 1;
        try {
            output = new PrintWriter(sc.getOutputStream(), true);
            input = new BufferedReader(
                    new InputStreamReader(sc.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String nickName = initGame(input);
            loadGame(output, nickName);
            // game state
            while (true) {
                String buf = recv(input);
                Gson gson = new Gson();
                boolean[] keys = gson.fromJson(buf, boolean[].class);
                // "wsad j"
                Cdc.getInstance().updateDirection(ClientID, keys);
                if (keys[4]) {
                    Cdc.getInstance().attack(ClientID);
                }
                if (false) {
                    Cdc.getInstance().changeWeapon(ClientID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("sc close");
            nameList.remove(myName);
        }
    }

    String initGame(BufferedReader input) throws IOException {
        // room wait

        myName = recv(input);
        nameList.add(myName);
        send(output, String.valueOf(ClientID));
        if (ClientID == 0) {
            while (!recv(input).equals("Start")) {
                send(output, new Gson().toJson(nameList));
            }
            load = true;
        }
        return myName;
    }

    void loadGame(PrintWriter output, String nickName) throws IOException {
        // loading state
        while (!load) {
            String caseType = recv(input);
            switch (caseType) {
                case "Start":
                    send(output, String.valueOf(load));
                case "Get list":
                    send(output, new Gson().toJson(nameList));
            }
        }
        Cdc.getInstance().addVirtualCharacter(ClientID, nickName);
        loadNum++;
        int oldLoadNum = loadNum;
        while (loadNum != totalClient) {
            if (oldLoadNum != loadNum) {
                oldLoadNum = loadNum;
                send(output, new Gson().toJson(nameList));
            }
        }
        if (ClientID == 0) {
            send(output, "Game load");
            new UDP_Client().startUDPBroadCast();
            Cdc.getInstance().startUpdatingThread();
        }
    }

    void send(PrintWriter output, String outputString) {
        output.println(outputString);
    }

    private static String recv(BufferedReader input) throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            break;
        }
        return inputLine;
    }
}
