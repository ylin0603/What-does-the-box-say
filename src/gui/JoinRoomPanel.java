package gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class JoinRoomPanel extends JPanel {

	private JFrame frame;
	public String myName;
	public Object[][] rooms = {
			{100, "test", "hello"}
	};
	public int selectedRoom;

	public JoinRoomPanel(JFrame frame) {
		this.frame = frame;
		this.frame.setLayout(new GridBagLayout());
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initUI();
	}
	
	private void initUI() {
		// Input player nickname.
		JPanel inputField = new JPanel();
		inputField.setLayout(new BoxLayout(inputField, BoxLayout.X_AXIS));
		inputField.add(createLabel("暱稱："));
		JTextField nameText = new JTextField(20);
		inputField.add(nameText);
		this.add(inputField);
		
		// Rooms list.
		JScrollPane scroll = new JScrollPane(null,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(150,200));
		scroll.setViewportView(createRoomsList());
		this.add(scroll);
		
		// Button.
		JPanel boxPanel = new JPanel();
		boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.X_AXIS));
		boxPanel.add(createLabel("請點選欲加入的房間"));
		boxPanel.add(Box.createGlue());
		JButton joinBtn = new JButton("進入房間");
		joinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				myName = nameText.getText();
				frame.getContentPane().removeAll();
				frame.getContentPane().repaint();
				frame.add(new WaitingStartPanel(
						frame, myName, rooms[selectedRoom]));
				frame.setVisible(true);
			}
			
		});
		boxPanel.add(joinBtn);
		this.add(boxPanel);
	}
	
	// Create a list for rooms.
	private JList<String> createRoomsList() {
		String[] roomsInfo = new String[rooms.length+1];
		roomsInfo[0] = "房號／房名／房主暱稱";
		for(int i=0; i<rooms.length; i++) {
			String temp = "";
			for(int j=0; j<3; j++) {
				temp += rooms[i][j].toString();
				if (j!=2) temp+="／";
			}
			roomsInfo[i+1] = temp;
		}
		
		JList<String> roomsList = new JList<String>(roomsInfo);
		roomsList.addListSelectionListener(new ListSelectionListener() {
			// When player select a room.
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					selectedRoom = roomsList.getSelectedIndex() - 1;
				}
			}
			
		});
		return roomsList;
	}
	
	// Create a label.
	private JLabel createLabel(String labelName) {
		JLabel label = new JLabel(labelName);
		return label;
	}
}
