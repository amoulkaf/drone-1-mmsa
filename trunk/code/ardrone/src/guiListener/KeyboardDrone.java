package guiListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import PartieANOUARetANAS.Controller;
import PartieANOUARetANAS.DroneStateContext;

/*
 Manuel d'utilisation:

 FLECHE HAUT : avancer
 FLECHE BAS : reculer
 FLECHE DROITE : deplacement horizontal a droite
 FLECHE GAUCHE : deplacement horizontal a gauche
 ESPACE : decoller
 CTRL : atterir
 D : pivoter a droite
 Q : pivoter a gauche
 S : descendre
 Z : monter
 */

//for ar-drone
public class KeyboardDrone implements KeyListener {

	private DroneStateContext _context;


	public KeyboardDrone(Controller controller) {
		_context = new DroneStateContext(controller);
		
	}

	public void execute(int key){
		switch(key){
		case KeyEvent.VK_UP:
		{
			_context.forward();
		}
		break;
		case KeyEvent.VK_DOWN :
		{
			_context.backward();
		}
		break;
		case KeyEvent.VK_RIGHT :
		{
			_context.horizontalRight();
		}
		break;
		case KeyEvent.VK_LEFT :
		{
			_context.horizontalLeft();
		}
		break;
		case KeyEvent.VK_SPACE:
		{
			_context.takeOff();
		}
		break;
		case KeyEvent.VK_CONTROL:
		{
			_context.landing();
		}
		break;
		case KeyEvent.VK_Q:
		{
			_context.rotateLeft();
		}
		break;
		case KeyEvent.VK_D:
		{
			_context.rotateRight();
		}
		break;
		case KeyEvent.VK_S:
		{
			_context.goDown();
		}
		break;
		
		case KeyEvent.VK_Z:
		{
			_context.goUp();
		}
		break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int source = e.getKeyCode();
		execute(source);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
