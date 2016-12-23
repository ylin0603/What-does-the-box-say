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
            String nickName = initGame(input, output);
            waitLoad(input, output);
            loadGame(output, nickName);
            // game state
            while (true) {
                String buf = recv(input);
                Gson gson = new Gson();
                System.out.println(ClientID + " " + buf + " in game");
                boolean[] keys = gson.fromJson(buf, boolean[].class);
                // "wsad j"
                Cdc.getInstance().updateKeys(ClientID, keys);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ClientID + " sc close");
            System.exit(0);
        }
    }

    String initGame(BufferedReader input, PrintWriter output)
            throws IOException {
        // room wait
        String myName = recv(input);
        nameList.add(myName);
        send(output, String.valueOf(ClientID));
        return myName;
    }

    void waitLoad(BufferedReader input, PrintWriter output) throws IOException {
        boolean localLoad = false;
        while (!load || !localLoad) {
            String action = recv(input);
            System.out.println(ClientID + " " + action + " in while");
            switch (action) {
                case "Get list":
                    send(output, new Gson().toJson(nameList));
                    localLoad = false;
                    break;
                case "game load?":
                    send(output, String.valueOf(load));
                    localLoad = true;
                    break;
                case "Start":
                    send(output, "true");
                    load = true;
                    localLoad = false;
                    break;
            }
        }
    }

    void loadGame(PrintWriter output, String nickName)
            throws IOException, InterruptedException {
        // loading state
        Cdc.getInstance().addVirtualCharacter(ClientID, nickName);
        if (ClientID == 0) {
            Cdc.getInstance().gameItemsInital();
            Cdc.getInstance().startUpdatingTimer();
        }
        loadNum++;
        while (loadNum != totalClient) {
            Thread.sleep(20);
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
