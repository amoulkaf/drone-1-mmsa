package guiView;

import guiListener.ControlRobotListener;
import guiModel.ConsoleModel;
import guiListener.KeyboardDrone;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import PartieANOUARetANAS.Controller;

public class RightPanelGUI extends JPanel implements Observer{
	private static final int NBROWS = 15; 
	
	private ConsoleModel _consoleModel;
	private JScrollPane _consolePane;
	private JTextArea _consoleText;
	private JButton _takeControl;
	
	/* 
	 * 
	 * ADD A JSCROLLPANE FOR DISPLAYING LOGS
	 *
	*/
	public RightPanelGUI(KeyboardDrone k,ConsoleModel model){
		this.setLayout(new BorderLayout());
		
		_takeControl = new JButton("Prendre le controle du robot");
		_takeControl.setEnabled(false);
		_takeControl.setToolTipText("Vous ne pouvez pas prendre le controle du robot");
		_takeControl.addMouseListener(new ControlRobotListener());
		
		JPanel consolePanel = new JPanel();
		consolePanel.setLayout(new BorderLayout());
		JLabel consoleLabel = new JLabel("Console");
		_consoleModel = model;
		_consoleModel.addObserver(this);
		_consoleText = new JTextArea(_consoleModel.getText());
		
		/*
		 * Situation reelle : decommenter ci-dessous et commenter ligne juste apres
		 * */
		
		/*
		Controller controller;
		try{
		controller = new Controller("192.168.1.1", 5556, "127.0.0.1", 7000, model);
		} catch (InterruptedException e1){
			e1.printStackTrace();
		}
		KeyboardDrone keyboard = new KeyboardDrone(controller, model);
		*/
		//KeyboardDrone keyboard = new KeyboardDrone(null, model);
		_consoleText.addKeyListener(k);
		//<TEST>
		//_consoleModel.addText("ceci est un test");
		//</TEST>
		_consoleText.setRows(NBROWS);
		_consoleText.setEditable(false);
		
		_consolePane = new JScrollPane(_consoleText);
		_consolePane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
	    });
		consolePanel.add(consoleLabel, BorderLayout.NORTH);
		consolePanel.add(_consolePane, BorderLayout.SOUTH);

		this.add(_takeControl, BorderLayout.NORTH);
		this.add(consolePanel, BorderLayout.SOUTH);
	}

	public void update(Observable o, Object arg) {
		//	*update si le robot est bien detecte : classe Robot (observable) avec boolean "isDetected"
		//	*update a chaque instruction :
		//		**deplacement drone : classe Drone observable
		//		**robot detecte/plus detecte
		//		**deplacement robot...
		_consoleText.setText(_consoleModel.getText());
	}
}
