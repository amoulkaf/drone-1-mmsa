package PartieANOUARetANAS;

import guiListener.KeyboardDrone;
import guiView.MainWindowGUI;

public class Main {
	
	public static void main (String[] args) throws InterruptedException{
	Controller controller;
	MainWindowGUI view;
	KeyboardDrone keyboard;

	controller = new Controller("192.168.1.1", 5556, "127.0.0.1", 7000);
	keyboard = new KeyboardDrone(controller);
	view = new MainWindowGUI(keyboard);
	
	
	}
	
}