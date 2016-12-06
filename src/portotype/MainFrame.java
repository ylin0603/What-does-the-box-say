package portotype;

import java.awt.event.*;

import javax.swing.JFrame;

import portotype.DealScreen;

public class MainFrame extends MouseAdapter implements KeyListener{
	public static void main(String[] args){
		new MainFrame();
	}
	
	public MainFrame(){
		JFrame mainFrame = new JFrame("Prototype-A");
		mainFrame.addWindowListener(new AD());
		DealScreen screen9 = new DealScreen("screen 9");
		DealScreen screen8 = new DealScreen("screen 8");
		DealScreen screen7 = new DealScreen("screen 7");
		DealScreen screen6 = new DealScreen("screen 6");
		DealScreen screen5 = new DealScreen("screen 5");
		DealScreen screen4 = new DealScreen("screen 4");
		DealScreen screen3 = new DealScreen("screen 3");
		DealScreen screen2 = new DealScreen("screen 2");
		DealScreen screen1 = new DealScreen("screen 1");
		
		
		screen9.addMouseMotionListener(this);
		screen9.addMouseListener(this);
		screen9.addKeyListener(this);
		screen8.addMouseMotionListener(this);
		screen8.addMouseListener(this);
		screen8.addKeyListener(this);
		screen7.addMouseMotionListener(this);
		screen7.addMouseListener(this);
		screen7.addKeyListener(this);
		screen6.addMouseMotionListener(this);
		screen6.addMouseListener(this);
		screen6.addKeyListener(this);
		screen5.addMouseMotionListener(this);
		screen5.addMouseListener(this);
		screen5.addKeyListener(this);
		screen4.addMouseMotionListener(this);
		screen4.addMouseListener(this);
		screen4.addKeyListener(this);
		screen3.addMouseMotionListener(this);
		screen3.addMouseListener(this);
		screen3.addKeyListener(this);
		screen2.addMouseMotionListener(this);
		screen2.addMouseListener(this);
		screen2.addKeyListener(this);
		screen2.addKeyListener(this);
		screen1.addMouseMotionListener(this);
		screen1.addMouseListener(this);
		screen1.addKeyListener(this);		
		
		mainFrame.setSize(500, 420);
		mainFrame.getContentPane().setLayout(null);
		
		
		mainFrame.add(screen1);
		screen1.setBounds(20, 20, 440, 274);
		
		mainFrame.getContentPane().add(screen2);
		screen2.setBounds(334,314,41,32);
		
		mainFrame.getContentPane().add(screen3);
		screen3.setBounds(376,314,41,32);
		
		mainFrame.getContentPane().add(screen4);
		screen4.setBounds(418,314,41,32);
		
		mainFrame.getContentPane().add(screen5);
		screen5.setBounds(334,347,41,32);
		
		mainFrame.getContentPane().add(screen6);
		screen6.setBounds(376,347,41,32);

		mainFrame.getContentPane().add(screen7);
		screen7.setBounds(418,347,41,32);

		mainFrame.getContentPane().add(screen8);
		screen8.setBounds(146,314,182,66);

		mainFrame.getContentPane().add(screen9);
		screen9.setBounds(20,314,120,66);
		
		mainFrame.setVisible(true);
	}
	
	//function in mouseAdapter and keyboardListner
	public void mouseClicked(MouseEvent e){
		DealScreen x = (DealScreen)e.getSource();
		System.out.print(x.getName());
		System.out.println(": mouse click");
	}
	
	public void mousePressed(MouseEvent e){
		DealScreen x = (DealScreen)e.getSource();
		System.out.print(x.getName());
		System.out.println(": mouse down");
	}
	
	public void mouseReleased(MouseEvent e){
		DealScreen x = (DealScreen)e.getSource();
		System.out.print(x.getName());
		System.out.println(": mouse release");
	}
	
	public void mouseDragged(MouseEvent e){
		DealScreen x = (DealScreen)e.getSource();
		System.out.print(x.getName());
		System.out.println(": mouse drag");
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		DealScreen x = (DealScreen)e.getSource();
		System.out.print(x.getName());
		System.out.println(": keybord input");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
}

//To close the frame
class AD extends WindowAdapter{
	public void windowClosing(WindowEvent e){
		System.exit(0);
	}
}
