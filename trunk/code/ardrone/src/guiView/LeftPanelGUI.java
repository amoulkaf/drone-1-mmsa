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

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.swresample;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import PartieANOUARetANAS.Commands;
import PartieANOUARetANAS.Controller;
import PartieANOUARetANAS.MatToBufImg;

//import PartieANOUARetANAS.MatToBufImg; //j'ai mis le MatToBufImg dans ton Pakage guiView, 
//pour ne pas créer de conflit c'est provisoir, j'arrange ça lundi car on a les fichiers au cremi
    

public class LeftPanelGUI extends JPanel implements Observer{
//	private static final String FRONTAL 	= "frontal";
//	private static final String VERTICAL 	= "vertical";
//	private static final int WIDTHIMG 		= 500;
//	private static final int HEIGHTIMG 		= 500;
	
//	private String _cameraView; 
	private JLabel _camLabel;
	private BufferedImage _camImgNew;
	private CameraModel _camModel;
	private FFmpegFrameGrabber _ffg;
	//private VideoCapture _capture;
	//private Mat _mat_stream;
	
	public LeftPanelGUI(Controller controller){
	//	_cameraView = FRONTAL;
		_camModel = new CameraModel();
		
		_camModel.addObserver(this);
		
		this.setLayout(new BorderLayout());
		
		//nu.pattern.OpenCV.loadLibrary();
		//_mat_stream = new Mat();
		
		// pour initialiser le codec
		String message;
		message = Commands.configIDS(controller.getSeq());
		controller.sendMessage(message);
		message = Commands.configCodec(controller.getSeq());
		controller.sendMessage(message);
		
		System.out.println("Fin d'initialisation, going to sleep 1sec");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String message1;
			
		//message1 = Commands.configIDS(controller.getSeq());
		//controller.sendMessage(message1);
		
		message1 = Commands.configCameraVertical(controller.getSeq());
		controller.sendMessage(message1);
		//_context.takeOff();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Test with Javacv + FFmpeg
		Loader.load(swresample.class);
		
		_ffg = new FFmpegFrameGrabber("tcp://192.168.1.1:5555"); 
		System.out.println("banana");

		try {
			_ffg.start();
			System.out.println("poire");
			ShowImage a = new ShowImage(this);
			a.start();
			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) {				
				e.printStackTrace();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("chocolat");
			e1.printStackTrace();
			System.out.println("vanille");
		}
		
		// ERREUR ICI (opencv not to chaud)
		/*
		_capture = new VideoCapture("tcp://192.168.1.1:5555");	
		if(_capture == null){
			System.out.println("do not work");
		} 
		else {
			ShowImage a = new ShowImage(this);
			a.start();
			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		*/
		//<TEST>
		/*
		try {
			_camImgTmp =  ImageIO.read(new File("../../doc/IHM/ihm.png"));
		}
		catch(IOException e){
			System.err.println("File doesn't exist.");
		}
		//</TEST>
		
		_camImgNew = new BufferedImage(WIDTHIMG, HEIGHTIMG, BufferedImage.TYPE_INT_RGB);

		*/
		_camLabel = new JLabel();		
		this.add(_camLabel, BorderLayout.NORTH);
		
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
	
	@Override
	public void paintComponent(Graphics g){
		g.drawImage(_camImgNew,0,0,_camImgNew.getWidth(),_camImgNew.getHeight(),_camLabel);
	}
	
	class ShowImage extends Thread{
		LeftPanelGUI panel;
		private BufferedImage _camImgTmp;
		
		public ShowImage(LeftPanelGUI panel){
			this.panel = panel;
			_camImgNew = null;
		}
		
		@Override
		public void run(){
			int i = 0;
			while(true){
				try {
					System.out.println(++i);
					if (i==60){
						_ffg.restart();
					}
					_camImgTmp = _ffg.grab().getBufferedImage();
					
					_camImgNew = _camImgTmp;

					panel.setSize(640, 360);
					panel.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
