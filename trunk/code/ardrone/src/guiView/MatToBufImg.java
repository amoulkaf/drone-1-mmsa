package guiView;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;


public class MatToBufImg {
	Mat _matrix;
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
}

