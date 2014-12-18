package guiView;

import guiModel.CameraModel;
import guiModel.ConsoleModel;
import image.ImageOperation;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.swresample;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;

import robot.HttpClientArduino;
import robot.RobotControll;
import PartieANOUARetANAS.Commands;
import PartieANOUARetANAS.Controller;


public class LeftPanelGUI extends JPanel implements Observer{
	private JLabel _camLabel;
	private BufferedImage _camImgNew;
	private CameraModel _camModel;
	private FFmpegFrameGrabber _ffg;
	private Controller _controller;
	private ShowImage _showImage;
	private RobotControll _robot;
	private ConsoleModel _model;
	
	public LeftPanelGUI(Controller controller, 
						CameraModel camModel, 
						RightPanelGUI rightPanel, 
						RobotControll robot,
						ConsoleModel model){
		
		_controller = controller;
		_camModel = camModel;
		_camModel.addObserver(this);
		_robot = robot;
		_model = model;
		
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
			_showImage = new ShowImage(this, rightPanel);
			_showImage.start();
			_camModel.addObserver(_showImage);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		/*
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
		*/
		_camLabel = new JLabel();
		
		this.add(_camLabel,BorderLayout.NORTH);
	}

	public void update(Observable o, Object arg) {
		String message;
		message = Commands.configIDS(_controller.getSeq());
		_controller.sendMessage(message);

		if (_camModel.isFrontCamera()){
			System.out.println("Camera frontale");
			message = Commands.configCameraHorizontal(_controller.getSeq());
		}
		else{
			System.out.println("Camera verticale");
			message = Commands.configCameraVertical(_controller.getSeq());
		}
		_controller.sendMessage(message);
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.drawImage(_camImgNew,0,0,_camImgNew.getWidth(),_camImgNew.getHeight(),_camLabel);
	}
	
	class ShowImage extends Thread implements Observer{
		private LeftPanelGUI _panel;
		private boolean _changed;
		private RightPanelGUI _rightPanel;
		private boolean _detected;
		
		public ShowImage(LeftPanelGUI panel, RightPanelGUI rightPanel){
			_panel = panel;
			_camImgNew = null;
			_changed = false;
			_rightPanel = rightPanel;
			_detected = false;
		}
		
		public void run(){
			int i = 0;
			while(true){
				try {	
					if(_changed){
						System.out.println("On sleep ici");
						try {
							Thread.sleep(1000);
							_changed = false;
						//	_ffg.stop();
						//	_ffg = new FFmpegFrameGrabber("tcp://192.168.1.1:5555"); 
						//	_ffg.start();
							_ffg.restart();
							Thread.sleep(1000);
							i=0;
						} 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("On retourne recuperer la video");
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
							_rightPanel.setTakeControle(true);
							_detected = true;
							if(_detected)
								_model.writeInFile("Robot detected ! ");
						}
						else{
							if (_detected){
								_detected = false;
								_rightPanel.setTakeControle(false);
								_robot.setControlRobot(false);
								try {
									HttpClientArduino.sendGet("Stop");
								} 
								catch (java.lang.Exception e) {
									e.printStackTrace();
								}
							}
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
