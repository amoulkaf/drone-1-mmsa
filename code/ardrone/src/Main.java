import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;

import guiListener.KeyboardDrone;
import guiModel.ConsoleModel;
import guiView.MainWindowGUI;

public class Main {

	public static void main(String[] args) {
		ConsoleModel model = new ConsoleModel();
		// KeyboardDrone dans RightPanelGUI
		//new MainWindowGUI(model);
		
		//Drone ardrone = new Drone("192.168.1.1", 5556, "\r", "AR-Drone");
		//Drone localhost = new Drone("localhost", 7000, "\n", "localhost");
		  
		//testPy();
		//testJava();
		
		//ardrone.start();
		//localhost.start();
	}
	
	
/*	
	public static void testPy(){
		//TEST:GEOFFREY:17-10-2014:test d'import fichier .py
		System.out.println("---------- Programme Python");
		String s = null;
		try {
			String prg ="python ../fonctionImage/imageDetection.py";
			
			Chrono chrono = new Chrono();
			chrono.start();
			
			Process p = Runtime.getRuntime().exec(prg);
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		//	BufferedReader stdError = new BufferedReader(new  InputStreamReader(p.getErrorStream()));
			
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
				if (s.equals("true")){
                	p.destroy();
                	break;
                }
              
            }
			
           // while ((s = stdError.readLine()) != null) {
            //    System.out.println(s);
           // }
			chrono.stop();
			chrono.printMilliSec();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	 */
}


