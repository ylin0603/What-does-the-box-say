package Input;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import gui.game.GameManager;

@SuppressWarnings("serial")
public class InfoAction extends AbstractAction {

	String command;
	
	public InfoAction(String command) {
		this.command = command;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		GameManager.getInsatance().openInfo(command);
		System.out.println(command);
	}

}
