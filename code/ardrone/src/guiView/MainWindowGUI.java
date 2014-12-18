package guiView;

import guiListener.KeyboardDrone;
import guiModel.ConsoleModel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import PartieANOUARetANAS.Controller;

public class MainWindowGUI extends JFrame{
	private static final int WIDTH 	= 750;
	private static final int HEIGHT	= 600;
	private static final String TITLE = "Sauveteur drone"; 
	//private TextField display;
	
	public MainWindowGUI(KeyboardDrone k,ConsoleModel model, Controller controller){
		this.setTitle(TITLE);
		this.setSize(WIDTH,HEIGHT);
		this.setLocationRelativeTo(null);
		this.setSize(1000,600);

		this.setLayout(new BorderLayout());
		LeftPanelGUI leftPanel = new LeftPanelGUI(controller);
		RightPanelGUI rightPanel = new RightPanelGUI(k,model);
		
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		
		this.setVisible(true);
		
		
	}
}
