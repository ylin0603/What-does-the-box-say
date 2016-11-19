package box;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoxBoard extends JFrame implements ActionListener {

	// vertical and horizontal center
	public GridBagConstraints gbc = new GridBagConstraints();
	private JPanel centerButtons;
	
	public BoxBoard() {
		super("What Does the Box Say?");
		initUI();
		beforeGame();
	}

	public static void main(String[] args) {
		BoxBoard boxBoard = new BoxBoard();
		boxBoard.setVisible(true);
	}
	
	// BoxBoard frame's setting
	private void initUI() {
		this.setSize(600, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null); //set frame in middle
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon("res/box.png");
		this.setIconImage(icon.getImage());
	}
	
	// Before game start, three buttons: create room, join room, exit game.
	public void beforeGame() {
		centerButtons = new JPanel();
		
		createButton(new JButton("建立房間"), "create_room");
		createButton(new JButton("加入房間"), "join_room");
		createButton(new JButton("離開遊戲"), "exit_game");
		
		this.setLayout(new GridBagLayout());
		this.add(centerButtons, gbc);
	}

	// Click button action
	@Override
	public void actionPerformed(ActionEvent e) {
		// Refresh frame.
		this.getContentPane().removeAll();
		this.getContentPane().repaint();
		
		if (e.getActionCommand().equals("create_room")) {
			this.add(new CreateRoomPanel(this), gbc);
		}
		else if (e.getActionCommand().equals("join_room")) {
			this.add(new JoinRoomPanel(this), gbc);
		}
		
		this.setVisible(true);
	}
	
	// Create a button.
	private void createButton(JButton button, String actionCommand) {
		button.setActionCommand(actionCommand);
		button.addActionListener(this);
		centerButtons.add(button);
	}

}
