package PartieANOUARetANAS;
import java.io.*;
import java.net.*;

public final class Commands {

	  private final static String LANDING = "290717696";
	  private final static String EMERGENCYMOTORSCUT = "290717952";
	  private final static String TAKEOFF = "290718208";
	  private final static String ID = "1";
	  protected final static String SESSION = "ses";
	  protected final static String PROFILE = "pro";
	  protected final static String APPLI = "app";
	  public final static String KEYVIDEOCHANNEL = "video:video_channel";
	  public final static String VALUEVIDEOCHANNELFRONT = "0";
	  public final static String VALUEVIDEOCHANNELVERTICAL = "1";
	  public final static String KEYVIDEOCODEC = "video:video_codec";
	  public final static String VALUEVIDEOCODEC129 ="129";
	  private static float neg = -1;
	  private static float pos = 1;
	  private static long neg754 = Convert.convert754(neg);
	  private static long pos754 = Convert.convert754(pos);
	  
	  //Avancer
	  public static String forward(int seq){
		  return move(0,neg754,0,0,seq);
	  }
	  
	  //Reculer
	  public static String backward(int seq){
		  return move(0,pos754,0,0,seq);
	  }
	  
	  //Deplacement horizontal a droite
	  public static String horizontalRight1(int seq){
		  return move(pos754,0,0,0,seq);
	  }
	  
	  //Deplacement horizontal a gauche
	  public static String horizontalLeft(int seq){
		  return move(neg754,0,0,0,seq);
	  }
	  
	  //Decoller
	  public static String takeOff(int seq){
		  return "AT*REF=" + seq + "," + TAKEOFF;
	  } 
	  
	  //Atterir
	  public static String landing(int seq){
		  return "AT*REF=" + seq + "," + LANDING;
	  }
	  
	  //Pivoter a droite
	  public static String rotateRight(int seq){
		  return move(0,0,0,pos754,seq);
	  }
	  
	  //Pivoter a gauche
	  public static String rotateLeft(int seq){
		  return move(0,0,0,neg754,seq);
	  }
	  
	  //Descendre
	  public static String goDown(int seq){
		  return move(0,0,neg754,0,seq);
	  }
	  
	  //Monter
	  public static String goUp(int seq){
		  return move(0,0,pos754,0,seq);
	  }
	  
	  //calibre le drone, une fois stabilise (a faire manuellement)
	  public static String calibrate(int seq, String ID){
		  return "AT*CALIB=" + seq + "," + ID ;
	  }
	  
	  //calibrage horizontal : verifie que le drone soit sur un support stable (a faire au debut)
	  public static String check(int seq){
		  return "AT*FTRIM=" + seq;
	  }
	  
	  //fait planer le drone (il est stable en l'air)
	  public static String hover(int seq){
		  return "AT*PCMD="+ seq + ",0,0,0,0,0" ;
	  }
	  
	  //fait bouger le drone suivant les angles Roll(L/R), Pitch(F/B), Throttle(Gaz) et Yaw(Angle)
	  public static String move(long roll, long pitch, long throttle, long yaw, int seq){
		  return "AT*PCMD=" + seq + "," + ID + "," + roll + "," + pitch + "," + throttle + "," + yaw;
	  }
	  
	  //coupe les moteurs du drone (en cas d'urgence uniquement)
	  public static String emergencyMotorsCut(int seq){
		  return "AT*REF="+ seq + "," + EMERGENCYMOTORSCUT;
	  }
	  
	  //commande qui permet de configurer le drone selon la key et sa valeur
	  public static String config(String key, String value, int seq){
		  return "AT*CONFIG=" + seq + ",\"" + key + "\",\"" + value + "\"" ;
	  }
	  
	  //commande a envoyer avant chaque nouvelle config
	  public static String configIDS(int seq){
		  return "AT*CONFIG_IDS=" + seq + ",\"7870b07f\",\"6bb4d6ff\",\"c96e70cf\"";
	  }

	 
	
}
