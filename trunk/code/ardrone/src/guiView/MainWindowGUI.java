package guiView;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class MainWindowGUI extends JFrame{
	private static final int WIDTH 	= 750;
	private static final int HEIGHT	= 600;
	private static final String TITLE = "Sauveteur drone"; 
	
	public MainWindowGUI(){
		this.setTitle(TITLE);
		this.setSize(WIDTH,HEIGHT);
		this.setLocationRelativeTo(null);
		
		this.setLayout(new FlowLayout());
		LeftPanelGUI leftPanel = new LeftPanelGUI();
		RightPanelGUI rightPanel = new RightPanelGUI();
		
		this.add(leftPanel);
		this.add(rightPanel);
		this.setVisible(true);
	}
}
