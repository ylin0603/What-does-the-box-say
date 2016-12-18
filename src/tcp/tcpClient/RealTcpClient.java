package tcp.tcpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;

public class RealTcpClient {
    final int port = 8888;
    private Socket sc;
    private BufferedReader input;
    private PrintWriter output;
    private int clientNo = -1;

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

    // all client join game
    public int joinRoom(String nickName) {

        output.println(nickName);
        String clientNoS = recv(input);
        clientNo = Integer.valueOf(clientNoS);

        return clientNo;
    }

    // only room leader loadGame
    public boolean loadGame() {
        output.println("Start");
        while (!recv(input).equals("Game load"));
        return true;
    }

    public void inputMoves(boolean[] keys) throws Exception {
        // "wsad "
        Gson gson = new Gson();
        String SedMsg = gson.toJson(keys, boolean[].class);
        realTcpClient.output.println(SedMsg);
        realTcpClient.output.flush();
        if (sc.isClosed())
            throw new Exception();
    }

    public int getClientNo(){
        assert clientNo != -1;

        return clientNo;
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
        }
        return inputLine;
    }
}
