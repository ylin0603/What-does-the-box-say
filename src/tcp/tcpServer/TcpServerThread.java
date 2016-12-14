package tcp.tcpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

import CDC.Cdc;
import udp.broadcast.client.UDP_Client;

public class TcpServerThread implements Runnable {
    private int ClientID = 0;
    static int totalClient;
    private PrintWriter output;
    private BufferedReader input;
    volatile static public boolean load = false;
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
                int eventType = Integer.valueOf(buf);
                // key release -1
                // move forward | backward 0 1
                // spin right | left 2 3
                // attack closeRange | longRange 4 5
                switch (eventType) {
                    case 0:
                    case 1:
                        //Cdc.getInstance().updateWalk(this.ClientID,transferModify.isTypeDetail());
                        Cdc.getInstance().updateDirection(this.ClientID, eventType);
                        break;
                    case 2:
                    case 3:
                        //Cdc.getInstance().updateDirection(this.ClientID,transferModify.isTypeDetail());
                        Cdc.getInstance().updateDirection(this.ClientID, eventType);
                        break;
                    case 4:
                    case 5:
                         //Cdc.getInstance().updateAttack(this.ClientID,transferModify.isTypeDetail());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("sc close");
                break;
            }
        }
    }

    String initGame(BufferedReader input) {
        // room wait
        String nickName = null;
        try {
            nickName = recv(input);
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
