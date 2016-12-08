package Input;

import renderer.data.DynamicObjectManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by TsunglinYang on 2016/12/7.
 */
public class FetchAction extends AbstractAction{
    @Override
    public void actionPerformed(ActionEvent e) {
        DynamicObjectManager.getInstance().keyGETPressed();
    }
}
