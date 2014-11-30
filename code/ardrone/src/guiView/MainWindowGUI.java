package guiView;

import guiModel.ConsoleModel;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class MainWindowGUI extends JFrame{
	private static final int WIDTH 	= 750;
	private static final int HEIGHT	= 600;
	private static final String TITLE = "Sauveteur drone"; 
	//private TextField display;
	
	public MainWindowGUI(ConsoleModel model){
		this.setTitle(TITLE);
		this.setSize(WIDTH,HEIGHT);
		this.setLocationRelativeTo(null);

		this.setLayout(new FlowLayout());
		LeftPanelGUI leftPanel = new LeftPanelGUI();
		RightPanelGUI rightPanel = new RightPanelGUI(model);
		
		this.add(leftPanel);
		this.add(rightPanel);
		
		this.setVisible(true);
		
		
	}
}
