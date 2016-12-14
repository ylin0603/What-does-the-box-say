package Input;

import tcp.tcpClient.RealTcpClient;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 * Created by TsunglinYang on 2016/12/9.
 */
public class RotateAction extends AbstractAction {
    private int direction;
    public RotateAction(int dir) {
        this.direction = dir;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
           RealTcpClient.getInstance().inputMoves(direction);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
