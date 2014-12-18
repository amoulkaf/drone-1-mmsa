package guiListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import robot.RobotControll;

public class ControlRobotListener implements MouseListener{
	private RobotControll _robot;
	
	public ControlRobotListener(RobotControll robot){
		_robot = robot;
	}
	
	public void mouseClicked(MouseEvent e) {
		_robot.setControlRobot(true);
	}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
