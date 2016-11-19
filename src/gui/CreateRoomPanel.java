package gui;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CreateRoomPanel extends JPanel{

	private JFrame frame;
	private Object[] room = new Object[3];

	public CreateRoomPanel(JFrame frame) {
		this.frame = frame;
		this.frame.setLayout(new GridBagLayout());
		this.setLayout(new GridLayout(3,1));
		initUI();
	}
	
	// Initial.
	private void initUI() {
		// Input room name.
		JPanel inputRoomName = inputField("©Ð¦W¡G");
		JTextField roomName = new JTextField(20);
		inputRoomName.add(roomName);
		this.add(inputRoomName);
		
		// Input nickname.
		JPanel inputNickName = inputField("¼ÊºÙ¡G");
		JTextField ownerName = new JTextField(20);
		inputNickName.add(ownerName);
		this.add(inputNickName);
		
		// Submit button.
		JButton submitBtn = new JButton("°e¥X");
		submitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				room[0] = getRoomNumber();
				room[1] = roomName.getText();
				room[2] = ownerName.getText();
				frame.getContentPane().removeAll();
				frame.getContentPane().repaint();
				frame.add(new waitingStartPanel(
						frame, room[2].toString(), room));
				frame.setVisible(true);
			}
			
		});
		this.add(submitBtn);
	}
	
	// Create an input field.
	private JPanel inputField(String labelName) {
		JPanel boxPanel = new JPanel();
		// Use BoxLayout instead of GridLayout because of auto change width.
		boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.X_AXIS));
		boxPanel.add(createLabel(labelName));
		return boxPanel;
	}
	
	// Create a label.
	private JLabel createLabel(String labelName) {
		JLabel label = new JLabel(labelName);
		return label;
	}
	
	// Random room number.
	private int getRoomNumber() {
		return 100;
	}
}
