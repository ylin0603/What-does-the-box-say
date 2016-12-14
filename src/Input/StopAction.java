package Input;

import tcp.tcpClient.RealTcpClient;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Tsunglin on 2016/12/13.
 */
public class StopAction extends AbstractAction{
    private int code;
    public StopAction(int code){
        this.code = code;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            System.out.println("released");
            RealTcpClient.getInstance().inputMoves(code);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
