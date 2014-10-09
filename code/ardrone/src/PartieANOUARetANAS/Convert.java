package PartieANOUARetANAS;
import java.util.zip.CRC32;
import java.util.zip.Checksum;


public class Convert {
	//fonction qui permet de convertir un float en int selon la norme IEE754
	  public static int convert754(double x){
		  return Float.floatToRawIntBits((float)x);
	  }
	  
	  //fonction qui ,permet de convertir un string ASCII en string CRC32
	  public static String convertCRC32(String str){
		  	byte bytes[] = str.getBytes();
			Checksum checksum = new CRC32();
			checksum.update(bytes,0,bytes.length);
			long lngChecksum = checksum.getValue();
			String ret = Long.toHexString(lngChecksum);
			return ret;
	  }
}
