import java.io.*;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import guiView.MainWindowGUI;

public class Main {

	public static void main(String[] args) {
		//new MainWindowGUI();
		
		//Drone ardrone = new Drone("192.168.1.1", 5556, "\r", "AR-Drone");
		//Drone localhost = new Drone("localhost", 7000, "\n", "localhost");
		  
		testPy();
		
		//ardrone.start();
		//localhost.start();
	}
	
	public static void testPy(){
			//TEST:GEOFFREY:17-10-2014:test d'import fichier .py
			String s = null;
			try {
				String prg ="python ../fonctionImage/imageDetection.py";
				
				Chrono chrono = new Chrono();
				chrono.start();
				
				Process p = Runtime.getRuntime().exec(prg);
				
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader stdError = new BufferedReader(new  InputStreamReader(p.getErrorStream()));
				
	            System.out.println("Here is the standard output of the command:");
				while ((s = stdInput.readLine()) != null) {
	                if (s.equals("true")){
	                	System.out.println("Zone detectee");
	                }
	            }
				
				System.out.println("Here is the standard error of the command:");
	            while ((s = stdError.readLine()) != null) {
	                System.out.println(s);
	            }
	            chrono.stop();
				chrono.printMilliSec();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	 
}


