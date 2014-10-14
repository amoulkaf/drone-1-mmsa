import java.io.*; 
import java.net.*;

public class Drone extends Thread{
  private String _addr;
  private int _port;
  private String _eof;
  private String _message;
  
  private int _seq = 0;
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
  
  public Drone(String addr, int port, String eof, String name){
	  super(name);
	  _addr = addr;
	  _port = port;
	  _eof = eof;
  }
  
  public void run() {
	InetAddress server = initServer();
	
	DatagramSocket socket = initSocket();

	
	//Phase de tests
	//en chantier !!!
	try {
		initialize(socket, server);
	} 
	catch (InterruptedException e1) {
		e1.printStackTrace();
	}
	
	/*
	  for (int i=0; i < 25; i++) {
		if (i == 0) 
			setMessage(check());
		else if(i == 1) 
			  setMessage(takeOff());
		else if(i == 2) {
			setMessage(hover());
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
		else if(i == 3) {
		  setMessage(calibrate());
		}
		else if(i == 4){
		  try {
			Thread.sleep(8000);
			} 
		  catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		}
		else if (i == 24){
		  try {
			Thread.sleep(5000);
		  } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		
		  setMessage(landing());
		}
		else {
			setMessage(move(0,Convert.convert754(-0.2),0,0));  
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		  
	      sendMessage(socket, server); 
		  }
	  }
  */
	
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
  }

  //initialise le serveur
  public InetAddress initServer(){
	  InetAddress server = null;
	  try {
		  server = InetAddress.getByName(_addr);
	  } 
	  catch (UnknownHostException e) {
			// TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	  
	  return server;
  }

  //initialise le socket 
  public DatagramSocket initSocket() {
	  DatagramSocket socket = null;
	  try {
		  socket = new DatagramSocket();
	  } 
	  catch (SocketException e) {
		  e.printStackTrace();
	  } 
	  return socket;
  }
  
  //Envoie un paquet
  public void sendMessage(DatagramSocket socket,InetAddress server){
	  byte buffer[] = _message.getBytes();
      int length = _message.length();
      DatagramPacket dataSent = new DatagramPacket(buffer,length,server,_port);      
      try {
		socket.send(dataSent);
      } 
      catch (IOException e) {
		e.printStackTrace();
      }  
      System.out.println("Message sent with num seq : " + _seq);
  }
  
  //calibre le drone, une fois stabilise
  public String calibrate(){
	  return "AT*CALIB=" + ++_seq + "," + ID +_eof;
  }
  
  //calibrage horizontal : verifie que le drone soit sur un support stable
  public String check(){
	  return "AT*FTRIM=" + ++_seq + _eof;
  }
  
  //fait planer le drone
  public String hover(){
	  return "AT*PCMD="+ ++_seq + ",0,0,0,0,0" + _eof;
  }
  
  //fait bouger le drone suivant les angles Roll(L/R), Pitch(F/B), Throttle(Gaz) et Yaw(Angle)
  public String move(int roll, int pitch, int throttle, int yaw){
	  return "AT*PCMD=" + ++_seq + "," + ID + "," + roll + "," + pitch + "," + throttle + "," + yaw + _eof;
  }
 
  
  //fait decoller le drone
  public String takeOff(){
	  return "AT*REF=" + ++_seq + "," + TAKEOFF + _eof;
  }
  
  //pose le drone
  public String landing(){
	  return "AT*REF=" + ++_seq + "," + LANDING + _eof;
  }
  
  //coupe les moteurs du drone (en cas d'urgence uniquement)
  public String emergencyMotorsCut(){
	  return "AT*REF="+ ++_seq + "," + EMERGENCYMOTORSCUT + _eof;
  }
  
  //commande qui permet de configurer le drone selon la key et sa valeur
  public String config(String key, String value){
	  return "AT*CONFIG=" + ++_seq + ",\"" + key + "\",\"" + value + "\"" + _eof;
  }
  
  //commande a envoyer avant chaque nouvelle config
  public String configIDS(){
	  return "AT*CONFIG_IDS=" + ++_seq + ",\"7870b07f\",\"6bb4d6ff\",\"c96e70cf\"" + _eof;
  }

  //message (ordre) qui sera envoye au drone
  public void setMessage(String m){
	  _message = m;
	  System.out.println(m);
  }
  
  //sequence d'initialisation a executer avant tout autre commande a envoyer
  public void initialize(DatagramSocket socket,InetAddress server) throws InterruptedException{
	  
	  setMessage("AT*LED="+ ++_seq+",5,"+Convert.convert754(0.8)+",8"+_eof);
	  sendMessage(socket, server);
	  
	  setMessage(configIDS());
	  sendMessage(socket, server);
	  setMessage(config("custom:session_id", "-all"));
	  sendMessage(socket, server);
	  Thread.sleep(500);
	  
	  setMessage("AT*LED="+ ++_seq+",10,"+Convert.convert754(0.8)+",8"+_eof);
	  sendMessage(socket, server);
	  
	  setMessage(configIDS());
	  sendMessage(socket, server);
	  setMessage(config("custom:profile_id", "-"+Convert.convertCRC32(PROFILE)));
	  sendMessage(socket, server);
	  Thread.sleep(500);
	  
	  
	  setMessage(configIDS());
	  sendMessage(socket, server);
	  setMessage(config("custom:application_id", "-"+Convert.convertCRC32(APPLI)));
	  sendMessage(socket, server);
	  Thread.sleep(500);
	  
	  
	  setMessage(configIDS());
	  sendMessage(socket, server);
	  setMessage(config("custom:session_id", Convert.convertCRC32(SESSION)));
	  sendMessage(socket, server);
	  Thread.sleep(500);
	      
	  setMessage(configIDS());
	  sendMessage(socket, server);
	  setMessage(config("custom:application_id",Convert.convertCRC32(APPLI)));
	  sendMessage(socket, server);
	  Thread.sleep(500);
	  	  
	  setMessage(configIDS());
	  sendMessage(socket, server);
	  setMessage(config("custom:profile_id", Convert.convertCRC32(PROFILE)));
	  sendMessage(socket, server);
	  Thread.sleep(500);
	  
	 
	  setMessage(configIDS());
	  sendMessage(socket, server);
	  setMessage(config("custom:application_desc", APPLI));
	  sendMessage(socket, server);
	  Thread.sleep(500);
	  
	  setMessage(configIDS());
	  sendMessage(socket, server);
	  setMessage(config("custom:profile_desc", PROFILE));
	  sendMessage(socket, server);
	  Thread.sleep(500);
	  	  
	  setMessage(configIDS());
	  sendMessage(socket, server);
	  setMessage(config("custom:session_desc", SESSION));
	  sendMessage(socket, server);
	  Thread.sleep(500);
  }
  
 }
