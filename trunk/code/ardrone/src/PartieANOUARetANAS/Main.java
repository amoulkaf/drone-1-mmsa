package PartieANOUARetANAS;

import robot.RobotControll;
import guiListener.KeyboardDrone;
import guiModel.ConsoleModel;
import guiView.MainWindowGUI;

public class Main {
	
	public static void main (String[] args) throws InterruptedException{
	
		Controller controller; 		
		KeyboardDrone keyboard;
		
		ConsoleModel model = new ConsoleModel();
		RobotControll robot = new RobotControll();
		controller = new Controller("192.168.1.1", 5556, "127.0.0.1", 7000,model);
		keyboard = new KeyboardDrone(controller, model, robot);
		
		new MainWindowGUI(keyboard, model, controller, robot);
	}	
}