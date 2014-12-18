package guiView;

import guiListener.KeyboardDrone;
import guiModel.CameraModel;
import guiModel.ConsoleModel;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import robot.RobotControll;
import PartieANOUARetANAS.Controller;

public class MainWindowGUI extends JFrame{
	private static final int WIDTH 	= 750;
	private static final int HEIGHT	= 600;
	private static final String TITLE = "Sauveteur drone"; 
	//private TextField display;
	
	public MainWindowGUI(KeyboardDrone k,ConsoleModel model, Controller controller, RobotControll robot){
		this.setTitle(TITLE);
		this.setSize(WIDTH,HEIGHT);
		this.setLocationRelativeTo(null);
		this.setSize(1000,400);

		this.setLayout(new BorderLayout());
		
		CameraModel camModel = new CameraModel();
		RightPanelGUI rightPanel = new RightPanelGUI(k,model, camModel, robot);
		LeftPanelGUI leftPanel = new LeftPanelGUI(controller, camModel, rightPanel, robot, model);
		
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		
		this.setVisible(true);
		
		
	}
}
