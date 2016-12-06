package portotype;

import javax.swing.JTextArea;


public class DealScreen extends JTextArea{
	
	String obj_name;
	
	public DealScreen(String name){
		obj_name = name;
	}
	
	public String getName(){
		return obj_name;
	}

}
