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
    private static ArrayList<String> NameList = new ArrayList<String>();
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
        String nickName = initGame(input);
        loadGame(output, nickName);
        // game state
        while (true) {
            try {
                String buf = recv(input);
                Gson gson = new Gson();
                boolean[] keys = gson.fromJson(buf, boolean[].class);
                // "wsad "
                Cdc.getInstance().updateDirection(ClientID, keys);
                
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("sc close");
                NameList.remove(myName);
                break;
            }
        }
    }

    String initGame(BufferedReader input) {
        // room wait

        try {
            myName = recv(input);
            NameList.add(myName);
            send(output, String.valueOf(ClientID));
            if (ClientID == 0) {
                while (!recv(input).equals("Start"));
                load = true;
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nickName;
    }

    void loadGame(PrintWriter output, String nickName) {
        // loading state
        while (!load);
        Cdc.getInstance().addVirtualCharacter(ClientID, nickName);
        loadNum++;

        while (loadNum != totalClient) {
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
