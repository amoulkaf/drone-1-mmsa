package guiView;

import guiListener.ControlRobotListener;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class RightPanelGUI extends JPanel implements Observer{
	private static final int NBROWS = 15; 
	
	private String _consoleText;
	JButton _takeControl;
	
	public RightPanelGUI(){
		this.setLayout(new BorderLayout());
		
		_takeControl = new JButton("Prendre le controle du robot");
		_takeControl.setEnabled(false);
		_takeControl.setToolTipText("Vous ne pouvez pas prendre le controle du robot");
		_takeControl.addMouseListener(new ControlRobotListener());
		
		JPanel consolePanel = new JPanel();
		consolePanel.setLayout(new BorderLayout());
		JLabel consoleLabel = new JLabel("Console");
		_consoleText = "Voici un log des actions effectuees\nVoici un autre log";
		JTextArea consoleText = new JTextArea(_consoleText);
		consoleText.setRows(NBROWS);
		consolePanel.add(consoleLabel, BorderLayout.NORTH);
		consolePanel.add(consoleText, BorderLayout.SOUTH);
		
		this.add(_takeControl, BorderLayout.NORTH);
		this.add(consolePanel, BorderLayout.SOUTH);
	}

	public void update(Observable o, Object arg) {
		// TODO 
		
	}
}
