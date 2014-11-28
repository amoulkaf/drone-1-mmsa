package PartieANOUARetANAS;

import guiListener.KeyboardDrone;
import guiModel.ConsoleModel;
import guiView.MainWindowGUI;

public class Main {
	
	public static void main (String[] args) throws InterruptedException{
	Controller controller;
	MainWindowGUI view;
	KeyboardDrone keyboard;

	controller = new Controller("192.168.1.1", 5556, "127.0.0.1", 7000);
	ConsoleModel model = new ConsoleModel();
	keyboard = new KeyboardDrone(controller, model);
	view = new MainWindowGUI(keyboard, model);
	
	}
	
}