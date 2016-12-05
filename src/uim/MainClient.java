package uim;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainClient extends Canvas {

	private final static int WIDTH = 300;
	private final static int HEIGHT = 300;
	private final static int SCALE = 3;
	private final static int TURNEAST = 0;
	private final static int TURNSOUTH = 1;
	private final static int TURNNORTH = 2;
	private final static int TURNWEST = 3;
	private final String serverip = "127.0.0.1";
	
	private JFrame frame;
	
	public MainClient() {
		frame = new JFrame();
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		
		try {
			InetAddress addr = InetAddress.getByName(serverip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		// boolean connectServer(InetAddr addr)
		// initUDPServer()
		// loadMap(String mapfile)
		// startRenderThread()
	}
	
	private void initFrame() {
		frame.setTitle("Game");
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.addKeyListener(gameKeyListener);
		frame.setVisible(true);
	}
	
	private KeyListener gameKeyListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					// keyGETPressed()
					break;
				case KeyEvent.VK_RIGHT:
					// inputMoves(TURNEAST)
					break;
				case KeyEvent.VK_DOWN:
					// inputMoves(TURNSOUTH)
					break;
				case KeyEvent.VK_UP:
					// inputMoves(TURNNORTH)
					break;
				case KeyEvent.VK_LEFT:
					// inputMoves(TURNWEST)
					break;
				default:
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
	};
	
	public static void main(String[] args) {
		new MainClient();
	}

}
