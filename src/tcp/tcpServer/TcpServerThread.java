package tcp.tcpServer;

import CDC.Cdc;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class TcpServerThread implements Runnable {
    private int ClientID = 0;
    volatile static public int totalClient = 0;
    private PrintWriter output;
    private BufferedReader input;
    volatile static public boolean load = false;
    private static ArrayList<String> nameList = new ArrayList<String>();
    Gson gson;
    private final static int W = 0;
    private final static int S = 1;
    private final static int A = 2;
    private final static int D = 3;
    private final static int SPACE = 4;
    private final static int J = 5;

    private final static int MOVE = 0;
    private final static int SPIN = 1;
    private final static int ATTACK = 2;
    private final static int CHANGEWEAPON = 3;

    TcpServerThread() {
        gson = new Gson();
    }

    public TcpServerThread(Socket sc, int ClientID) {
        this.ClientID = ClientID;
        Cdc.getInstance().addVirtualCharacter(ClientID);
        totalClient++;
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
                int recvCode = Integer.valueOf(buf);
                boolean[] keys = new boolean[6];
                // "wsad j"
                for (int i = 0; i < keys.length && recvCode != 0; i++) {
                    if ((recvCode - 1) % 2 == 0) {
                        recvCode = (recvCode - 1) / 2;
                        keys[i] = true;
                    } else {
                        recvCode = (recvCode) / 2;
                    }
                }

                int[] moveCode = new int[4];
                // "move,spin,attack,change weapon"
                if (keys[W]) {
                    moveCode[MOVE]++;
                }
                if (keys[S]) {
                    moveCode[MOVE]--;
                }
                if (keys[A]) {
                    moveCode[SPIN]--;
                }
                if (keys[D]) {
                    moveCode[SPIN]++;
                }
                if (keys[SPACE]) {
                    moveCode[ATTACK] = 1;
                }
                if (keys[J]) {
                    moveCode[CHANGEWEAPON] = 1;
                }
                Cdc.getInstance().updateKeys(ClientID, moveCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ClientID + " sc close");
            totalClient--;
            if (totalClient == 0) {
                System.exit(0);
            }
        }
    }

    String initGame(BufferedReader input, PrintWriter output)
            throws IOException {
        // room wait
        String myName = recv(input);
        nameList.add(myName);
        send(output, String.valueOf(ClientID));
        Cdc.getInstance().SetName(ClientID, myName);
        return myName;
    }

    void waitLoad(BufferedReader input, PrintWriter output) throws IOException {
        boolean localLoad = false;
        while (!load || !localLoad) {
            String action = recv(input);
            switch (action) {
                case "Get list":
                    send(output, new Gson().toJson(nameList));
                    localLoad = false;
                    break;
                case "game load?":
                    send(output, String.valueOf(load));
                    localLoad = false;
                    break;
                case "Start":
                    load = true;
                    localLoad = false;
                    break;
                case "game load":
                    localLoad = true;
                    break;
            }
        }
    }

    synchronized void loadGame(PrintWriter output, String nickName)
            throws IOException, InterruptedException {
        // loading state
        if (recv(input).equals("Get Number")) {
            output.println(totalClient);
        }
        System.out.println(ClientID + " start");
        if (ClientID == 0) {
            RealTcpServer.getInstance().stop();
            System.out.println(ClientID + " room start");
            Cdc cdc = Cdc.getInstance();
            cdc.gameItemsInital();
            System.out.println(
                    "Cdc player num" + cdc.getPlayersUpdateInfo().size());
            cdc.startUpdatingTimer();
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
