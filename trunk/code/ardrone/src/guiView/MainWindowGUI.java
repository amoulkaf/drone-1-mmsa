package guiView;

import guiListener.KeyboardDrone;
import guiModel.ConsoleModel;

import java.awt.FlowLayout;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JRootPane;

public class MainWindowGUI extends JFrame{
	private static final int WIDTH 	= 750;
	private static final int HEIGHT	= 600;
	private static final String TITLE = "Sauveteur drone"; 
	private TextField display;
	
	public MainWindowGUI(KeyboardDrone keyboard, ConsoleModel model){
		this.setTitle(TITLE);
		this.setSize(WIDTH,HEIGHT);
		this.setLocationRelativeTo(null);

		this.setLayout(new FlowLayout());
		LeftPanelGUI leftPanel = new LeftPanelGUI();
		RightPanelGUI rightPanel = new RightPanelGUI(keyboard, model);
		
		this.add(leftPanel);
		this.add(rightPanel);
		
		this.setVisible(true);
		
		
	}
}
