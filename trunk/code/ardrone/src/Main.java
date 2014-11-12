import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;

import guiView.MainWindowGUI;

public class Main {

	public static void main(String[] args) {
		//new MainWindowGUI();
		
		//Drone ardrone = new Drone("192.168.1.1", 5556, "\r", "AR-Drone");
		//Drone localhost = new Drone("localhost", 7000, "\n", "localhost");
		  
		testPy();
		test();
		
		//ardrone.start();
		//localhost.start();
	}
	
	private static int[] getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);

		int rgb[] = new int[] {
		    (argb >> 16) & 0xff, //red
		    (argb >>  8) & 0xff, //green
		    (argb      ) & 0xff  //blue
		};

//		System.out.println("rgb: " + rgb[0] + " " + rgb[1] + " " + rgb[2]);
		return rgb;
	}

	// Pire des cas (pas de detection de zone) : 0.185s
	// Alors qu'en python : 1.7s
	public static int test(){
		System.out.println("---------- Programme Java");

		BufferedImage img;
		Chrono chrono = new Chrono();
		chrono.start();

	    try {
	        img = ImageIO.read(new File("../../doc/IHM/ihm.png"));

	        int[] rgb;
	        int r,g,b;
	        int r1,g1,b1;
	        
	        int width = img.getWidth();
	        int height = img.getHeight();
	        final int COLOR = 20;
	        final int SCALE = 20;
	        
	        for(int x = 0; x < width; x++){
	            for(int y = 0; y < height; y++){
	            	rgb = getPixelData(img, x, y);
	            	r = rgb[0];
	            	g = rgb[1];
	            	b = rgb[2];
	            	
	            	int l_area = width/SCALE;
	            	int h_area = height/SCALE;
	            	boolean dif = false;
	            	if ((x+l_area) < width && 
            			(y+h_area) < height && 
            			r == 0 &&
            			g == 0 &&
            			b == 0){
	            		for (int i=x; i<=x+l_area; i++){
	            			for (int j=y; j<=y+h_area;j++){
	            				//System.out.println(rgb);
	            				int[] rgb1 = getPixelData(img, i, j);
	            				r1 = rgb1[0];
	        	            	g1 = rgb1[1];
	        	            	b1 = rgb1[2];
	            				//System.out.println("Pix : "+i+";"+j+"-"+rgb1[0]+" "+rgb1[1]+" "+rgb1[2]);
	            				if (r1 != r || g1 != g || b1 != b){
	            					dif = true;
	            					//System.out.println(i+";"+j);
	            					break;
	            				}
	            			}
	            		}
	            		
	            		if (!dif){
	            			System.out.println("Detected : ");
	            			System.out.println("Coordinates : "+x+";"+y);
	            			chrono.stop();
	            			chrono.printMilliSec();
	            			return 0;
	            		}
	            	}
	            }
	        }
	        System.out.println("Non detected");
	        chrono.stop();
			chrono.printMilliSec();


	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}
	
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
	
	 
}


