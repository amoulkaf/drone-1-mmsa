package image;

import java.awt.image.BufferedImage;

public class ImageOperation extends Thread {
	final static int RANGE_INF = 0;
    final static int RANGE_SUP = 35;
    final static int SCALE = 20;
	
    private BufferedImage _image;
    
    public ImageOperation(){
    	_image = null;
    }
    
	public boolean detectRobot(BufferedImage image){
		_image = image;
		
		Chrono chrono = new Chrono();
		chrono.start();


        int[] rgb;
        int r,g,b;
        int r1,g1,b1;
        
        int width = _image.getWidth();
        int height = _image.getHeight();
        
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
            	rgb = getPixelData(_image, x, y);
            	r = rgb[0];
            	g = rgb[1];
            	b = rgb[2];
            	
            	int l_area = width/SCALE;
            	int h_area = height/SCALE;
            	boolean dif = false;
            	if ((x+l_area) < width && 
        			(y+h_area) < height && 
        			pixelEqualsTo(rgb)){
            		for (int i=x; i<=x+l_area; i++){
            			for (int j=y; j<=y+h_area;j++){
            				int[] rgb1 = getPixelData(_image, i, j);
            				r1 = rgb1[0];
        	            	g1 = rgb1[1];
        	            	b1 = rgb1[2];
        	            	for (int s = RANGE_INF; s<=RANGE_SUP; s++){
	            				if (r1 != r || g1 != g || b1 != b){
	            					dif = true;
	            					break;
	            				}
        	            	}
            			}
            		}
            		
            		if (!dif){
            			//System.out.println("Detected : ");
            			System.out.println("Coordinates : "+x+";"+y);
            			chrono.stop();
            			//chrono.printMilliSec();
            			return true;
            		}
            	}
            }
        }
        
       // System.out.println("Non detected");
        chrono.stop();
		chrono.printMilliSec();
	    
	    return false;
	}

	private static int[] getPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);

		int rgb[] = new int[] {
		    (argb >> 16) & 0xff, //red
		    (argb >>  8) & 0xff, //green
		    (argb      ) & 0xff  //blue
		};

		return rgb;
	}
	
	public static boolean pixelEqualsTo(int[] rgb){
		int r = rgb[0];
		int g = rgb[1];
		int b = rgb[2];
		for (int x=RANGE_INF; x <=RANGE_SUP; x++){
			if (r == x && g == x && b == x)
				return true;
		}
		return false;
	}
	
	@Override
	public void run() {
	}
}