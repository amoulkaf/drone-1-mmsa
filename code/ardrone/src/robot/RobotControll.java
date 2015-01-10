package robot;

import java.util.Observable;

public class RobotControll extends Observable {
	private boolean _canControllRobot;
	
	public RobotControll(){
		_canControllRobot = false;
	}
	
	public void setControlRobot(boolean bool){
		_canControllRobot = bool;
		setChanged();
		notifyObservers();
	}
	
	public boolean getControlRobot(){
		return _canControllRobot;
	}
}
