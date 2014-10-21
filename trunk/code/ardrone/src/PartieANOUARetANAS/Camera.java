package PartieANOUARetANAS;
import java.io.*; 
import java.net.*;

public class Camera extends Thread{
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
	
	/*
	public void switchCamera(){
		
	_connection.sendMessage();
	
	}
	
	
	  // TEST DE LA VIDEO FRONT & VERTICAL
	
	  setMessage(configIDS());
	  sendMessage(socket,server);
	  setMessage(config(KEYVIDEOCODEC,VALUEVIDEOCODEC129));
	  sendMessage(socket,server);
	  
	  try {
		Thread.sleep(1000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  for(int i = 0 ; i < 1000 ; i++){
		  setMessage(configIDS());
		  sendMessage(socket,server);
		  
		  if(i % 2 == 0){
			  setMessage(config(KEYVIDEOCHANNEL,VALUEVIDEOCHANNELVERTICAL));
		  }
		  else{
			  setMessage(config(KEYVIDEOCHANNEL,VALUEVIDEOCHANNELFRONT));
		  }
		  sendMessage(socket,server);
		  try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
    System.out.println("----End of program----");
    socket.close();
}*/
	
	
	
	
	
	
	
	
	
	
	
}
