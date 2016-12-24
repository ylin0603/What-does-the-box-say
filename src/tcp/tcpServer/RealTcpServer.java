package tcp.tcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RealTcpServer implements Runnable {
    final int port = 8888;
    ServerSocket ss;
    static int totalClient = 0;
    volatile ArrayList<String> IPtable = new ArrayList<String>();
    private static RealTcpServer realTcpServer;
    private Thread thisThread;
    private Socket serverSocket;
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
            thisThread = new Thread(this);
            thisThread.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void destory() {
        // setWaitLoad(false);
        if (thisThread != null)
            thisThread.stop();
        if (serverSocket != null)
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        if (realTcpServer != null)
            realTcpServer = null;
    }

    public ArrayList<String> getClientIPTable() {
        return IPtable;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            // every server thread
            try {
                serverSocket = ss.accept();
                String s1 = serverSocket.getInetAddress().toString().replace("/", "");
                IPtable.add(s1);
                TcpServerThread tcpServerThread =
                        new TcpServerThread(serverSocket, totalClient);
                if (totalClient == 0) {

                }
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
