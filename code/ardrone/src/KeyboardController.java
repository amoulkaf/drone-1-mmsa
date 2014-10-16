import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;


public class KeyboardController implements KeyListener{

	public void run(int source)throws InterruptedException, IOException {
		switch(source) {
		case KeyEvent.VK_UP:
			break;
			
		case KeyEvent.VK_DOWN: 
			break;
			
		case KeyEvent.VK_RIGHT : 
			break;
			
		case KeyEvent.VK_LEFT: 
			break;

		case KeyEvent.VK_SPACE:
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
