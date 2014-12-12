package guiView;

import guiListener.CameraListener;
import guiModel.CameraModel;
import image.ImageOperation;

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


public class LeftPanelGUI extends JPanel implements Observer{
	private JLabel _camLabel;
	private BufferedImage _camImgNew;
	private CameraModel _camModel;
	private FFmpegFrameGrabber _ffg;
	private Controller _controller;
	private ShowImage _showImage;
	
	public LeftPanelGUI(Controller controller){
		_controller = controller;
		_camModel = new CameraModel();
		
		_camModel.addObserver(this);
		
		this.setLayout(new BorderLayout());
		this.setSize(640, 360);

		// pour initialiser le codec
		String message;
		message = Commands.configIDS(_controller.getSeq());
		_controller.sendMessage(message);
		message = Commands.configCodec(_controller.getSeq());
		_controller.sendMessage(message);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}	
			
		message = Commands.configIDS(_controller.getSeq());
		_controller.sendMessage(message);
		
		message = Commands.configCameraVertical(_controller.getSeq());
		_controller.sendMessage(message);
		_camModel.setFrontCamera(false);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Loader.load(swresample.class);
		
		_ffg = new FFmpegFrameGrabber("tcp://192.168.1.1:5555"); 

		try {
			System.out.println("1");
			_ffg.start();
			System.out.println("2");
			
			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) {				
				e.printStackTrace();
			}
			_showImage = new ShowImage(this);
			_showImage.start();
			_camModel.addObserver(_showImage);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		BufferedImage img=null;
		try {
			img = ImageIO.read(new File("../../doc/IHM/ihm.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(img);
		_camLabel = new JLabel(icon);
		this.add(_camLabel, BorderLayout.NORTH);
		
		JButton changeCameraButton = new JButton("Changer de camera");
		changeCameraButton.setToolTipText("Changer la camera du drone");
		changeCameraButton.addMouseListener(new CameraListener(_camModel));
		this.add(changeCameraButton, BorderLayout.SOUTH);	
		System.out.println("[LPGUI] Left Panel GUI ok ");
	}
	

	public void update(Observable o, Object arg) {
		String message;
		message = Commands.configIDS(_controller.getSeq());
		_controller.sendMessage(message);

		if (_camModel.isFrontCamera()){
			message = Commands.configCameraVertical(_controller.getSeq());
		}
		else{
			message = Commands.configCameraHorizontal(_controller.getSeq());
		}
		_controller.sendMessage(message);
	}
	
	@Override
	public void paintComponent(Graphics g){
		ImageIcon icon = new ImageIcon(_camImgNew);
		_camLabel = new JLabel(icon);
		this.remove(_camLabel);
		this.add(_camLabel, BorderLayout.NORTH);
		System.out.println("okookko k ok ok okok");
		//g.drawImage(img,0,0,_camImgNew.getWidth(),_camImgNew.getHeight(),_camLabel);
	}
	
	class ShowImage extends Thread implements Observer{
		private LeftPanelGUI _panel;
		private boolean _changed;
		
		public ShowImage(LeftPanelGUI panel){
			_panel = panel;
			_camImgNew = null;
			_changed = false;
		}
		
		public void run(){
			int i = 0;
			while(true){
				try {	
					if(_changed){
						System.out.println("On sleep ici");
						try {
							this.sleep(1000);
							_changed = false;
							_ffg.stop();
							_ffg = new FFmpegFrameGrabber("tcp://192.168.1.1:5555"); 
							_ffg.start();
							this.sleep(1000);
							i=0;
						} 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					++i;
					//Horizontal : 60fps
					if (_camModel.isFrontCamera()){
						if (i==60){
							_ffg.restart();
						}
					}
					//Vertical : 40 fps
					else{
						if (i==40){
							_ffg.restart();
						}	
					}
					
					_camImgNew = _ffg.grab().getBufferedImage();
					
					//toutes les 60 images (toutes les secondes), on calcule
					if (i%40 == 0){
						ImageOperation img = new ImageOperation();
						if(img.detectRobot(_camImgNew)){
							// TODO : essaie de connexion avec le robot
						}
					}
					_panel.setSize(640,360);
					_panel.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void update(Observable o, Object arg) {
			System.out.println("[LeftPanelGUI] okokoko");
			_changed = true;
		}
	}	
}
