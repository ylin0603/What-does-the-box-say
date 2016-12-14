package tcp.tcpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RealTcpClient {
    final int port = 8888;
    private Socket sc;
    private BufferedReader input;
    private PrintWriter output;


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
        int ClientNO;
        output.println(nickName);
        String ClientNOS = recv(input);
        ClientNO = Integer.valueOf(ClientNOS);
        return ClientNO;
    }

    // only room leader loadGame
    public boolean loadGame() {
        output.println("Start");
        while (!recv(input).equals("Game load"));
        return true;
    }

    public void inputMoves(int eventType) throws Exception {
        // key release -1
        // move forward | backward 0 1
        // spin right | left 2 3
        // attack closeRange | longRange 4 5
        String SedMsg = String.valueOf(eventType);
        realTcpClient.output.println(SedMsg);
        realTcpClient.output.flush();
        System.out.println(SedMsg);
        if (sc.isClosed())
            throw new Exception();
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
