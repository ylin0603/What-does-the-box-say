package tcp.tcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RealTcpServer implements Runnable {
    final int port = 8888;
    ServerSocket ss;
    public static String magicWord = String.valueOf(System.currentTimeMillis());
    static int totalClient = 0;
    volatile ArrayList<String> IPTable = new ArrayList<String>();
    volatile ArrayList<Integer> portTable = new ArrayList<Integer>();
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

    public void stop() {
        if (thisThread != null)
            thisThread.stop();
    }

    public void destory() {
        stop();
        if (ss != null)
            try {
                ss.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        if (realTcpServer != null)
            realTcpServer = null;
    }

    public void setClientIPTable(String IP) {
        IPTable.add(IP);
    }

    public ArrayList<String> getClientIPTable() {
        return IPTable;
    }

    public void setClientPortTable(int port) {
        portTable.add(port);
    }

    public ArrayList<Integer> getClientPortTable() {
        return portTable;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            // every server thread
            try {
                serverSocket = ss.accept();
                TcpServerThread tcpServerThread =
                        new TcpServerThread(serverSocket, totalClient);
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
