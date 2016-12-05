package tcpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class TcpServerThread implements Runnable {
    private int ClientID = 0;
    static int totalClient;
    PrintWriter output;
    BufferedReader input;
    private static TcpServerThread tcpServerThread;

    TcpServerThread() {

    }

    public TcpServerThread(Socket sc, int ClientID) {

        this.ClientID = ClientID + 1;
        TcpServerThread.totalClient = ClientID + 1;
        try {
            output = new PrintWriter(sc.getOutputStream(), true);
            input = new BufferedReader(
                    new InputStreamReader(sc.getInputStream()));
            // CDC.addVirtualCharacter(ClientID);
            assert input.ready();
            assert !input.ready() : "no";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void initGame() {
        Random random = new Random();
        /*String[] bagType = {"補包", "子彈"};
        for (int i = 0; i < bagType.length; i++) {
            int x = random.nextInt(CDC.mapX);
            int y = random.nextInt(CDC.mapY);
            CDC.addItem(bagType[i], i, true, x, y);
        }
        CDC.startUpdatingThread();*/
    }

    @Override
    public void run() {
        while (true) {
            try {
                String[] moveChar = {"TURNEAST", "TURNSOUTH", "TURNNORTH",
                        "TURNWEST", "GET"};
                String buf = recv(input);
                System.out.println(buf);
                int moveCode = -1;
                for (int i = 0; i < moveChar.length; i++) {
                    if (buf.equals(moveChar[i])) {
                        moveCode = i;
                        break;
                    }
                    assert i == moveChar.length - 1 : buf;
                }
                switch (moveCode) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        System.out.println("move" + moveChar[moveCode]);
                        // updateDirection(this.ClientID,moveCode);
                        break;
                    case 4:
                        System.out.println("get");
                        // getItem(this.ClientID);
                        break;
                    default:
                        assert false : moveCode + "非定義的行為";
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("sc close");
                TcpServerThread.totalClient--;
                break;
            }
        }
    }



    private static String recv(BufferedReader input) throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            break;
        }
        return inputLine;
    }
}
