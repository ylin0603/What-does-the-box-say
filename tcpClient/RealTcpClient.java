package tcpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RealTcpClient {
    final int port = 8888;
    Socket sc;
    BufferedReader input;
    PrintWriter output;

    private static RealTcpClient realTcpClient;

    private RealTcpClient() {

    }

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

    public void inputMoves(int moveCode) throws Exception {
        assert moveCode >= 0 || moveCode < 5;
        String[] moveChar =
                {"TURNEAST", "TURNSOUTH", "TURNNORTH", "TURNWEST", "GET"};
        String sendMsg = moveChar[moveCode];
        realTcpClient.output.println(sendMsg);
        realTcpClient.output.flush();
        System.out.println(sendMsg);
        if (sc.isClosed())
            throw new Exception();
    }

    private static String recv(BufferedReader input) throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            break;
        }
        return inputLine;
    }
}
