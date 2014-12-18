package guiListener;

import guiModel.CameraModel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CameraListener implements MouseListener{

	private CameraModel _camModel;
	
	public CameraListener(CameraModel camModel){
		_camModel = camModel;
	}
	
	public void mouseClicked(MouseEvent e) {
		_camModel.changeCamera();
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
