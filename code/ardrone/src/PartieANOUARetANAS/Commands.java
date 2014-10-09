package PartieANOUARetANAS;
import java.io.*;
import java.net.*;

public final class Commands {

	  private final static String LANDING = "290717696";
	  private final static String EMERGENCYMOTORSCUT = "290717952";
	  private final static String TAKEOFF = "290718208";
	  private final static String ID = "1";
	  private final static String SESSION = "ses";
	  private final static String PROFILE = "pro";
	  private final static String APPLI = "app";
	  private final static String KEYVIDEOCHANNEL = "video:video_channel";
	  private final static String VALUEVIDEOCHANNELFRONT = "0";
	  private final static String VALUEVIDEOCHANNELVERTICAL = "1";
	  private final static String KEYVIDEOCODEC = "video:video_codec";
	  private final static String VALUEVIDEOCODEC129 ="129";
	  
	  //calibre le drone, une fois stabilise
	  public static String calibrate(int seq, String ID, String eof){
		  return "AT*CALIB=" + ++seq + "," + ID +eof;
	  }
	  
	  //calibrage horizontal : verifie que le drone soit sur un support stable
	  public static String check(int seq, String eof){
		  return "AT*FTRIM=" + ++seq + eof;
	  }
	  
	  //fait planer le drone
	  public static String hover(int seq, String eof){
		  return "AT*PCMD="+ ++seq + ",0,0,0,0,0" + eof;
	  }
	  
	  //fait bouger le drone suivant les angles Roll(L/R), Pitch(F/B), Throttle(Gaz) et Yaw(Angle)
	  public static String move(int roll, int pitch, int throttle, int yaw, int seq, String eof){
		  return "AT*PCMD=" + ++seq + "," + ID + "," + roll + "," + pitch + "," + throttle + "," + yaw + eof;
	  }
	 
	  
	  //fait decoller le drone
	  public static String takeOff(int seq, String eof){
		  return "AT*REF=" + ++seq + "," + TAKEOFF + eof;
	  }
	  
	  //pose le drone
	  public static String landing(int seq, String eof){
		  return "AT*REF=" + ++seq + "," + LANDING + eof;
	  }
	  
	  //coupe les moteurs du drone (en cas d'urgence uniquement)
	  public static String emergencyMotorsCut(int seq, String eof){
		  return "AT*REF="+ ++seq + "," + EMERGENCYMOTORSCUT + eof;
	  }
	  
	  //commande qui permet de configurer le drone selon la key et sa valeur
	  public static String config(String key, String value, int seq, String eof){
		  return "AT*CONFIG=" + ++seq + ",\"" + key + "\",\"" + value + "\"" + eof;
	  }
	  
	  //commande a envoyer avant chaque nouvelle config
	  public static String configIDS(int seq, String eof){
		  return "AT*CONFIG_IDS=" + ++seq + ",\"7870b07f\",\"6bb4d6ff\",\"c96e70cf\"" + eof;
	  }

	 
	
}
