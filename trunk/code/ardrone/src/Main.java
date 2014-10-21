import java.io.*;

import guiView.MainWindowGUI;

public class Main {

	public static void main(String[] args) {
		//new MainWindowGUI();
		
		Drone ardrone = new Drone("192.168.1.1", 5556, "\r", "AR-Drone");
		Drone localhost = new Drone("localhost", 7000, "\n", "localhost");
		  
		testPy();
		
		//ardrone.start();
		//localhost.start();
	}
	
	public static void testPy(){
		try{
			//TEST:GEOFFREY:17-10-2014:test d'import fichier .py
			String prg ="from PIL.Image import *\n"
					+ "def main():\n"
					+ "\timg = open(\"../IHM/ihm.png\")\n"
					+ "main()";

			System.out.println("1");
			BufferedWriter out = new BufferedWriter(new FileWriter("test1.py"));
			out.write(prg);
			out.close();
System.out.println("2");
			ProcessBuilder pb = new ProcessBuilder("python","test1.py");
			Process p = pb.start();
			 System.out.println("3");
			 
			//BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			//int ret = new Integer(in.readLine()).intValue();
			
			//System.out.println("value is : "+ret);
		}
		catch(Exception e){System.out.println(e);}
	}
	
}


