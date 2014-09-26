import java.io.*; 
import java.net.*;

public class Camera {
	//Chargement du codec
	//AT*CONFIG=<SEQ>,"video:video_codec","129"\r;
	
	//Camera frontale
	//AT*CONFIG=<SEQ>,"video:video_channel","0"\r;
	
	//Camera verticale
	//AT*CONFIG=<SEQ>,"video:video_channel","1"\r;
	
	//ffplay -flags low_delay tcp://<IP ADDR>:<PORT>
	
	
	
	//Chargement du codec
	public String downloadCodec(int seq,String eof){
		return "AT*CONFIG="+seq+"\"video:video_codec\",\"129\""+eof;
	}
	//Camera frontale
	public String frontCam(int seq,String eof){
		return "AT*CONFIG="+seq+"\"video:video_channel\",\"0\""+eof;
	}
	//Camera verticale
	public String downCam(int seq,String eof){
		return "AT*CONFIG="+seq+"\"video:video_channel\",\"1\""+eof;
	}
}
