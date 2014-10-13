package guiListener;

import guiModel.CameraModel;
import guiView.LeftPanelGUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CameraListener implements MouseListener{

	private CameraModel _camModel;
	
	public CameraListener(CameraModel camModel){
		_camModel = camModel;
	}
	
	public void mouseClicked(MouseEvent e) {
		System.out.println("[ChangeCameraListener] Mouse clicked");
		//Envoyer ici l'info au drone pour changer de camera
		//	.... TODO
		_camModel.changeCamera();
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
