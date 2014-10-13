package guiListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControlRobotListener implements MouseListener{

	public void mouseClicked(MouseEvent e) {
		System.out.println("[ControlRobotListener] Mouse clicked");
		//envoyer ici la demande de co au robot
	}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
