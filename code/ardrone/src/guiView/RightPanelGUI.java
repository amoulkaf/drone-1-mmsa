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
		this.setSize(180,240);
		
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
		
		_consoleText.addKeyListener(k);
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
		_consoleText.setText(_consoleModel.getText());
	}
}
