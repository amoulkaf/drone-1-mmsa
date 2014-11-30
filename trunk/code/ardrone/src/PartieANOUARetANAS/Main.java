package PartieANOUARetANAS;

import guiModel.ConsoleModel;
import guiView.MainWindowGUI;

public class Main {
	
	public static void main (String[] args) throws InterruptedException{
	
	//Controller dans RightPaneGUI
	ConsoleModel model = new ConsoleModel();
	// KeyboardDrone dans RightPanelGUI
	new MainWindowGUI(model);
	
	}
	
}