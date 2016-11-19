package box;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Weapon {
	
	ImageIcon knife = new ImageIcon("res/knife.png");
	ImageIcon gun = new ImageIcon("res/gun.png");
	JLabel showWeapon;

	public Weapon() {
		showWeapon = new JLabel(knife);
		showWeapon.setBounds(360, 485, 50, 50);
	}
	
	public void setKnife() {
		showWeapon.setIcon(knife);
	}
	
	public void setGun() {
		showWeapon.setIcon(gun);
	}
	
	public JLabel getLabel() {
		return showWeapon;
	}
}
