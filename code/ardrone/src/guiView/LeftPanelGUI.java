package guiView;

import guiListener.CameraListener;
import guiModel.CameraModel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeftPanelGUI extends JPanel implements Observer{
	private static final String FRONTAL 	= "frontal";
	private static final String VERTICAL 	= "vertical";
	private static final int WIDTHIMG 		= 500;
	private static final int HEIGHTIMG 		= 500;
	
	private String _cameraView; 
	private JLabel _camLabel;
	private BufferedImage _camImgTmp;
	private BufferedImage _camImgNew;
	private CameraModel _camModel;
	
	public LeftPanelGUI(){
		_cameraView = FRONTAL;
		_camImgTmp = null;
		_camModel = new CameraModel();
		
		_camModel.addObserver(this);
		
		this.setLayout(new BorderLayout());
		
		// TODO
		//	* recuperer le stream du ARDrone
		
		//<TEST>
		try {
			_camImgTmp =  ImageIO.read(new File("../../doc/IHM/ihm.png"));
		}
		catch(IOException e){
			System.err.println("File doesn't exist.");
		}
		//</TEST>
		
		_camImgNew = new BufferedImage(WIDTHIMG, HEIGHTIMG, BufferedImage.TYPE_INT_RGB);
		Graphics g = _camImgNew.createGraphics();
		g.drawImage(_camImgTmp, 0, 0, WIDTHIMG, HEIGHTIMG, null);
		g.dispose();
		
		_camLabel = new JLabel(new ImageIcon(_camImgNew));		
		add(_camLabel, BorderLayout.NORTH);
		
		JButton changeCameraButton = new JButton("Changer de camera");
		changeCameraButton.setToolTipText("Changer la camera du drone");
		changeCameraButton.addMouseListener(new CameraListener(_camModel));
		this.add(changeCameraButton, BorderLayout.SOUTH);
	}

	public void update(Observable o, Object arg) {
		if (_camModel.isFrontCamera()){
			System.out.println("[LeftPaneGUI] Afficher ici la cam frontale");
			// TODO
		}
		else{
			System.out.println("[LeftPaneGUI] Afficher ici la cam verticale");
			// TODO
		}
	}
}