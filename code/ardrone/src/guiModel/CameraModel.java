package guiModel;

import java.util.Observable;

public class CameraModel extends Observable {

	private boolean _isFrontCamera;
	
	public CameraModel(){
		_isFrontCamera = true;
	}
	
	public void changeCamera(){
		if (_isFrontCamera){
			_isFrontCamera = false;
		}
		else{
			_isFrontCamera = true;
		}
		setChanged();
		notifyObservers();
	}
	
	public boolean isFrontCamera(){
		return _isFrontCamera;
	}
}
