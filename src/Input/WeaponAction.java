package Input;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import gui.game.GameManager;

@SuppressWarnings("serial")
public class WeaponAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		GameManager.getInsatance().setWeapon();
	}
}
