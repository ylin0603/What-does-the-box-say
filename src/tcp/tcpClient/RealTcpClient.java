package tcp.tcpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RealTcpClient {
    final int port = 8888;
    private Socket sc;
    private BufferedReader input;
    private PrintWriter output;
    private int clientID = -1;
    private ArrayList<String> NameList = new ArrayList<String>();

    private static RealTcpClient realTcpClient;

    private RealTcpClient() {}

    public static RealTcpClient getInstance() {
        if (realTcpClient == null) {
            realTcpClient = new RealTcpClient();
        }
        return realTcpClient;
    }

    public boolean connectServer(String serverip) {
        try {
            realTcpClient.sc = new Socket(serverip, port);
            realTcpClient.input = new BufferedReader(
                    new InputStreamReader(realTcpClient.sc.getInputStream()));
            realTcpClient.output =
                    new PrintWriter(realTcpClient.sc.getOutputStream(), true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getMagicWord() {
        return recv(input);
    }

    // all client join game
    public int joinRoom(String nickName, int port) {
        output.println(nickName);
        output.println(port);
        String clientNoS = recv(input);
        clientID = Integer.valueOf(clientNoS);
        return clientID;
    }

    // all room member call it periodically
    public ArrayList<String> getNameList() {
        output.println("Get list");
        String nameString = recv(input);
        NameList = new Gson().fromJson(nameString,
                new TypeToken<ArrayList<String>>() {}.getType());
        return NameList;
    }

    // all room member call it periodically
    public boolean isGameload() {
        output.println("game load?");
        boolean gameLoaded = Boolean.parseBoolean(recv(input));
        System.out.println(gameLoaded);
        return gameLoaded;
    }

    // room leader call it when want start
    public void startGame() {
        output.println("Start");
    }

    public void recvedGameLoad() {
        output.println("game load");
    }

    public int getTotalPlayerNumer() {
        output.println("Get Number");
        return Integer.valueOf(recv(input));
    }

    public void inputMoves(boolean[] keys) throws Exception {
        // "wsad j"
        int sendCode = 0;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i]) {
                sendCode += (int) (Math.pow(2, i));
            }
        }
        output.println(sendCode);
        output.flush();
        if (sc.isClosed())
            throw new Exception();
    }

    public int getClientNo() {
        assert clientID != -1;

        return clientID;
    }

    private String recv(BufferedReader input) {
        String inputLine = null;
        try {
            while ((inputLine = input.readLine()) != null) {
                break;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }
        return inputLine;
    }
}
