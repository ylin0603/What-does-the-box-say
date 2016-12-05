package tcpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class RealTcpServer implements Runnable {
    final int port = 8888;
    ServerSocket ss;
    static int totalClient = 0;
    volatile Vector<InetAddress> v = new Vector<InetAddress>();
    private static RealTcpServer realTcpServer;

    private RealTcpServer() {

    }

    public static RealTcpServer getInstance() {
        if (realTcpServer == null) {
            realTcpServer = new RealTcpServer();
        }
        return realTcpServer;
    }

    public void initTCPServer() {
        try {
            ss = new ServerSocket(port);
            System.out
                    .println("server" + ss.getLocalSocketAddress().toString());
            Thread t = new Thread(this);
            t.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Vector<InetAddress> getClientIPTable() {
        return v;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            // every server thread
            Socket so;
            try {
                so = ss.accept();
                InetAddress s1 = so.getInetAddress();
                System.out.println(s1);
                v.add(s1);
                System.out.println(s1);
                TcpServerThread tcpServerThread =
                        new TcpServerThread(so, totalClient);
                Thread t = new Thread(tcpServerThread);
                t.start();
                totalClient++;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                break;
            }
        }
    }
}
