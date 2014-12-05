package PartieANOUARetANAS;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.highgui.Highgui;


public class MatToBufImg {
/*	Mat _matrix;
	MatOfByte _mob;
	String _fileExtern;
	
	
	public MatToBufImg(){
	}
	
	public MatToBufImg(Mat _matrixx,String fileExt){
		_matrix=_matrixx;
		_fileExtern=fileExt;
	}
	
	public void setMatrix(Mat _matrixx, String fileExt){
		_matrix=_matrixx;
		_fileExtern=fileExt;
		_mob=new MatOfByte();
	}
	
	public BufferedImage getBufferedImage(){
		Highgui.imencode(_fileExtern,_matrix,_mob);
		byte[] byteArray = _mob.toArray();
		BufferedImage bufImage = null;
		try{
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufImage;
	}	
*/
	/*
	public static BufferedImage MatToBufferedImage(Mat matrix) {  
        long startTime = System.nanoTime();  
        int cols = matrix.cols();  
        int rows = matrix.rows();  
        int elemSize = (int)matrix.elemSize();  
        byte[] data = new byte[cols * rows * elemSize];  
        int type;  
        matrix.get(0, 0, data);  
        switch (matrix.channels()) {  
          case 1:  
            type = BufferedImage.TYPE_BYTE_GRAY;  
            break;  
          case 3:   
            type = BufferedImage.TYPE_3BYTE_BGR;  
            // bgr to rgb  
            byte b;  
            for(int i=0; i<data.length; i=i+3) {  
              b = data[i];  
              data[i] = data[i+2];  
              data[i+2] = b;  
            }  
            break;  
          default:  
            return null; // Error  
        }  
        BufferedImage image = new BufferedImage(cols, rows, type);  
        image.getRaster().setDataElements(0, 0, cols, rows, data);  
        long endTime = System.nanoTime();  
        System.out.println(String.format("Elapsed time: %.2f ms", (float)(endTime - startTime)/1000000));  
        return image; // Successful  
	} 
	*/ 
}

