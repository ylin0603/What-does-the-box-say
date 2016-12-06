package portotype;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import portotype.DealScreen;

public class Test implements KeyListener{
	
	public static void main(String[] args){
		new Test();
		
	}
	
	public Test(){
		JFrame fm1 = new JFrame();
		fm1.addWindowListener(new ADD());
		fm1.setLayout(new BorderLayout());
		DealScreen sc1 = new DealScreen("screen1");
		DealScreen sc2 = new DealScreen("screen2");
		sc1.addKeyListener(this);
		sc2.addKeyListener(this);
		fm1.add(sc1, BorderLayout.NORTH);
		fm1.add(sc2, BorderLayout.SOUTH);
		fm1.setSize(500,500);
		fm1.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		DealScreen x = (DealScreen)e.getSource();
		System.out.println(x.getName());
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

class ADD extends WindowAdapter{
	public void windowClosing(WindowEvent e){
		System.exit(0);
	}
}

