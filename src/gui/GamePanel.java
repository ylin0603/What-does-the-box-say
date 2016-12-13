package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	
	private JLabel myBox;
	public String myName;
	private Object[] roomInfo;
	private JPanel bloodBar, smallMap;
	private Weapon weapon;
	private int bullet = 3;

	public GamePanel(JFrame frame, String name, Object[] roomInfo) {
		this.myName = name;
		this.roomInfo = roomInfo;
		frame.setLayout(new BorderLayout());
		this.setLayout(null);
		putRoomInfo();
		putBoxes();
		putBloodBar();
		putWeapon();
		putSmallMap();
		setBackground(new ImageIcon("res/grass.png"));
	}
	
	// Set grass background. Must be added in panel "last".
	private void setBackground(ImageIcon image) {
		JLabel background = new JLabel(image);
		background.setBounds(0, 0, 600, 600);
		this.add(background);
	}
	
	// Put boxes.
	private void putBoxes() {
		myBox = new BoxBox(50, 50);
		this.add(myBox);
	}
	
	// Put room information.
	private void putRoomInfo() {
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		infoPanel.setBounds(0, 0, 600, 20);
		infoPanel.setBackground(Color.BLACK);
		infoPanel.add(createLabel("房號："+roomInfo[0]));
		infoPanel.add(Box.createGlue());
		infoPanel.add(createLabel("房名："+roomInfo[1]));
		infoPanel.add(Box.createGlue());
		infoPanel.add(createLabel("房主暱稱："+roomInfo[2]));
		infoPanel.add(Box.createGlue());
		this.add(infoPanel);
	}
	
	// Put a blood bar.
	private void putBloodBar() {
		JLabel showName = createLabel(myName);
		showName.setBounds(10, 500, 100, 20);
		this.add(showName);
		bloodBar = new BloodBar();
		this.add(bloodBar);
	}
	
	// Put weapon.
	private void putWeapon() {
		// Show weapon.
		JLabel weaponLabel = createLabel("使用的武器：");
		weaponLabel.setBounds(280, 500, 100, 20);
		this.add(weaponLabel);
		weapon = new Weapon();
		this.add(weapon.showWeapon);
		// Show remaining bullet.
		JLabel bulletLabel = createLabel("剩餘子彈數："+bullet);
		bulletLabel.setBounds(280, 530, 100, 20);
		this.add(bulletLabel);
	}
	
	// Put small map.
	private void putSmallMap() {
		smallMap = new SmallMapPanel();
		this.add(smallMap);
	}
	
	// Create a label.
	private JLabel createLabel(String labelName) {
		JLabel label = new JLabel(labelName);
		label.setForeground(Color.WHITE);
		return label;
	}
}
