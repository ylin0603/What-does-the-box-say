package Input;

import renderer.data.DynamicObjectManager;
import tcp.tcpClient.RealTcpClient;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by TsunglinYang on 2016/12/7.
 */
public class AttackAction extends AbstractAction{
    private int code;
    public AttackAction(int code){
        this.code = code;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            RealTcpClient.getInstance().inputMoves(code);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
