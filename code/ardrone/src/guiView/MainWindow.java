package guiView;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainWindow extends JFrame{
	public MainWindow(){
		this.setTitle("Sauveteur drone");
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		
		JButton button = new JButton("Test");
		
		this.getContentPane().add(button);
		this.setVisible(true);
	}
}
