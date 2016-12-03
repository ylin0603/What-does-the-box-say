package tcpServer;

import java.net.InetAddress;
import java.util.Vector;

public class TcpServer {
    public static void main(String args[]) {
        System.out.println("server");
        RealTcpServer realTcpServer = RealTcpServer.getInstance();
        realTcpServer.initTCPServer();
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Vector<InetAddress> v = realTcpServer.getClientIPTable();
            for (int i = 0; i < v.size(); i++) {
                InetAddress s = v.get(i);
                System.out.println(s);
            }
        }
    }
}
