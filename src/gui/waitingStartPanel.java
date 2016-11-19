package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class WaitingStartPanel extends JPanel implements ActionListener{
	
	private JFrame frame;
	private Object[] room = new Object[3];
	public String myName;
	public String[] playersName = {"已加入的玩家：", "hello", "hehe"};

	public WaitingStartPanel(JFrame frame, String name, Object room[]) {
		this.frame = frame;
		this.myName = name;
		this.room = room;
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(350, 200));
		
		// Left side: players list.
		showPlayersList();
		// Right side: room information.
		showRoomInfo();
	}
	
	// Show the players list in scroll.
	private void showPlayersList() {
		JScrollPane scroll = new JScrollPane(null,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(150,100));
		JList<String> playersList = new JList<String>(playersName);
		scroll.setViewportView(playersList);
		this.add(scroll, BorderLayout.WEST);
	}
	
	// Show room information (normal)
	private void showRoomInfo() {
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setPreferredSize(new Dimension(150, 200));
		infoPanel.add(new JLabel("玩家暱稱："+myName));
		infoPanel.add(Box.createGlue());
		infoPanel.add(new JLabel("房號："+room[0]));
		infoPanel.add(new JLabel("房名："+room[1]));
		infoPanel.add(new JLabel("房主暱稱："+room[2]));
		infoPanel.add(Box.createGlue());
		if (myName.equals(room[2])) {
			infoPanel.add(createButton("開始遊戲", "start_game"));
			infoPanel.add(createButton("解散房間", "disband_room"));
		} else {
			infoPanel.add(new JLabel("等待中..."));
		}
		this.add(infoPanel, BorderLayout.EAST);
	}
	
	// Create a button.
	private JButton createButton(String name, String actionCommand) {
		JButton button = new JButton(name);
		button.setActionCommand(actionCommand);
		button.addActionListener(this);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start_game")) {
			frame.getContentPane().removeAll();
			frame.getContentPane().repaint();
			frame.add(new GamePanel(frame, myName, room));
			frame.setVisible(true);
		}
		else if (e.getActionCommand().equals("disband_game")) {
			
		}
	}
}
