package guiView;

import guiListener.ControlRobotListener;
import guiModel.ConsoleModel;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class RightPanelGUI extends JPanel implements Observer{
	private static final int NBROWS = 15; 
	
	private ConsoleModel _consoleModel;
	private JTextArea _consoleText;
	private JButton _takeControl;
	
	public RightPanelGUI(){
		this.setLayout(new BorderLayout());
		
		_takeControl = new JButton("Prendre le controle du robot");
		_takeControl.setEnabled(false);
		_takeControl.setToolTipText("Vous ne pouvez pas prendre le controle du robot");
		_takeControl.addMouseListener(new ControlRobotListener());
		
		JPanel consolePanel = new JPanel();
		consolePanel.setLayout(new BorderLayout());
		JLabel consoleLabel = new JLabel("Console");
		_consoleModel = new ConsoleModel();
		_consoleModel.addObserver(this);
		_consoleText = new JTextArea(_consoleModel.getText());
		//<TEST>
		_consoleModel.addText("ceci est un test");
		//</TEST>
		_consoleText.setRows(NBROWS);
		_consoleText.setEditable(false);
		consolePanel.add(consoleLabel, BorderLayout.NORTH);
		consolePanel.add(_consoleText, BorderLayout.SOUTH);
		
		this.add(_takeControl, BorderLayout.NORTH);
		this.add(consolePanel, BorderLayout.SOUTH);
	}

	public void update(Observable o, Object arg) {
		// TODO 
		//	*update si le robot est bien detecte : classe Robot (observable) avec boolean "isDetected"
		//	*update a chaque instruction :
		//		**deplacement drone : classe Drone observable
		//		**robot detecte/plus detecte
		//		**deplacement robot...
		_consoleText.setText(_consoleModel.getText());
	}
}
