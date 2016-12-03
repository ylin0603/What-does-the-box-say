package tcpClient;

public class TcpClient {
    public static void main(String[] args) {
        System.out.println("client");
        RealTcpClient realTcpClient = RealTcpClient.getInstance();

        boolean isConnect =
                realTcpClient.connectServer("127.0.0.1" /* args[0] */);
        System.out.println(isConnect);
        while (true) {
            try {
                realTcpClient.inputMoves(0);
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                break;
            }
        }
    }
}
