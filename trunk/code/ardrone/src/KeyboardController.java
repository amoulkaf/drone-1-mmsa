import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;


public class KeyboardController implements KeyListener{
	
private static final String FORWARD = "Forward";
private static final String LEFT = "Left";
private static final String RIGHT = "Right";
private static final String BACKWARD = "Backward";
private static final String STOP = "Stop";
	
	public void run(int source)throws Exception {
		switch(source) {
		case KeyEvent.VK_NUMPAD8:
			HttpClientArduino.sendGet(FORWARD);
			break;
			
		case KeyEvent.VK_NUMPAD4: 
			HttpClientArduino.sendGet(LEFT);
			break;
			
		case KeyEvent.VK_NUMPAD6 :
			HttpClientArduino.sendGet(RIGHT);
			break;
			
		case KeyEvent.VK_NUMPAD2 : 
			HttpClientArduino.sendGet(BACKWARD);
			break;

		case KeyEvent.VK_NUMPAD5:
			HttpClientArduino.sendGet(STOP);
			break;
		}
	}
	
	public void keyPressed(KeyEvent event){
		int source = event.getKeyCode();	
		try {
			run(source);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void keyReleased(KeyEvent event){}


	public void keyTyped(KeyEvent event){}
}
