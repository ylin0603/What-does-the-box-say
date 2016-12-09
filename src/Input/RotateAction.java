package Input;

import renderer.data.DynamicObjectManager;
import tcp.tcpClient.RealTcpClient;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by TsunglinYang on 2016/12/9.
 */
public class RotateAction extends AbstractAction {
    private int direction;
    static double degree = 0;
    public RotateAction(int dir) {
        this.direction = dir;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            //RealTcpClient.getInstance().inputMoves(angle);
            DynamicObjectManager dom = DynamicObjectManager.getInstance();
            dom.updateVirtualCharacter(0,degree++,0,dom.getVirtualCharacterXY().x,dom.getVirtualCharacterXY().y);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
