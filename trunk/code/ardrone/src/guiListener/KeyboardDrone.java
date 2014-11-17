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
 SHIFT : calibrer
 
 ---MODE EXTERIEUR---
 BACKSPACE : passer en mode exterieur
 O : flip en avant
 L : flip en arriere
 K : flip a gauche
 M : flip a droit
 T : wave
 Y : turnarroud
 
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
				System.out.println("key UP pressed\n");
				_context.forward();
			
			}
			break;
			case KeyEvent.VK_DOWN :
			{
				System.out.println("key DOWN pressed\n");
				_context.backward();
			}
			break;
			case KeyEvent.VK_RIGHT :
			{
				System.out.println("key RIGHT pressed\n");
				_context.horizontalRight();
			}
			break;
			case KeyEvent.VK_LEFT :
			{
				System.out.println("key LEFT pressed\n");
				_context.horizontalLeft();
			}
			break;
			case KeyEvent.VK_SPACE:
			{
				System.out.println("key SPACE pressed\n");
				_context.takeOff();
			}
			break;
			case KeyEvent.VK_CONTROL:
			{
				System.out.println("key Ctrl pressed\n");
				_context.landing();
			}
			break;
			case KeyEvent.VK_Q:
			{
				System.out.println("key Q pressed\n");
				_context.rotateLeft();
			}
			break;
			case KeyEvent.VK_D:
			{
				System.out.println("key D pressed\n");
				_context.rotateRight();
			}
			break;
			case KeyEvent.VK_S:
			{
				System.out.println("key S pressed\n");
				_context.goDown();
			}
			break;
		
			case KeyEvent.VK_Z:
			{
				System.out.println("key Z pressed\n");
				_context.goUp();
			}
			break;
			
			case KeyEvent.VK_SHIFT:
			{
				System.out.println("key SHIFT pressed\n");
				_context.calibrate();
			}
			break;	
			case KeyEvent.VK_ESCAPE:
			{
				System.out.println("key ESCAPE pressed\n");
				_context.switchState();
			}
			break;
			case KeyEvent.VK_O:
			{
				System.out.println("key O pressed\n");
				_context.frontFlip();
			}
			break;
			case KeyEvent.VK_K:
			{
				System.out.println("key K pressed\n");
				_context.leftFlip();
			}
			break;
			case KeyEvent.VK_L:
			{
				System.out.println("key L pressed\n");
				_context.backFlip();
			}
			break;
			case KeyEvent.VK_M:
			{
				System.out.println("key M pressed\n");
				_context.rightFlip();
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
