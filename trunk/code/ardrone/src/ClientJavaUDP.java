import java.io.*; 
import java.net.*;


public class ClientJavaUDP extends Thread{
  //private byte _buffer[] = new byte[1024];
  private String _addr;
  private String _message;
  private int _port;
  private int _seq = 0;
  private String _eof;
  private final static String LANDING = "290717696";
  private final static String EMERGENCYMOTORSCUT = "290717952";
  private final static String TAKEOFF = "290718208";
  private final static String ID = "1";
  
  public ClientJavaUDP(String addr, int port, String eof, String name){
	  super(name);
	  _addr = addr;
	  _port = port;
	  _eof = eof;
  }
  
  public void run() {
	InetAddress server = null;
	try {
		server = InetAddress.getByName(_addr);
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    DatagramSocket socket = null;
	try {
		socket = new DatagramSocket();
	} catch (SocketException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
      
      for (int i=0; i < 6; i++) {
    	  if (i == 0) 
    		  setMessage(check());
    	  else if(i == 1) 
    		  setMessage(takeOff());
    	  else if(i == 2) {
    		  setMessage(hover());
    		  try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  }
    	  else if(i ==3) {
    		setMessage(move(0,0,0,convert754(0.8)));  
    		break;
    		  
    	  }
    	  else if(i == 4) {
    		  setMessage(hover());
    		  try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
    	  }
    	  else {
    		  /*
    		  try {
				//Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
    		  setMessage(landing());
    	  }
    	  
          byte buffer[] = _message.getBytes();
          int length = _message.length();
          DatagramPacket dataSent = new DatagramPacket(buffer,length,server,_port);      
          try {
			socket.send(dataSent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
      }
    	   
      socket.close();
  }

  //arrete les mouvements du drone
  public String hover(){
	  return "AT*PCMD="+ ++_seq + ",0,0,0,0,0\r";
  }
  
  //fait bouger le drone suivant les angles Roll, Pitch, Throttle et Yaw
  public String move(int roll, int pitch, int throttle, int yaw){
	  return "AT*PCMD=" + ++_seq + ",1," + roll + "," + pitch + "," + throttle + "," + yaw + _eof;
  }
  
  public String calibrate(){
	  return "AT*CALIB=" + ++_seq + "," + ID +_eof;
  }
  
  //calibrage horizontal : verifie que le drone soit sur un support stable
  public String check(){
	  return "AT*FTRIM=" + ++_seq + _eof;
  }
  
  //fait decoller le drone
  public  String takeOff(){
	  return "AT*REF=" + ++_seq + "," + TAKEOFF + _eof;
  }
  
  //pose le drone
  public  String landing(){
	  return "AT*REF=" + ++_seq + "," + LANDING + _eof;
  }
  
  //coupe les moteurs du drone (en cas d'urgence uniquement)
  public  String emergencyMotorsCut(){
	  return "AT*REF="+ ++_seq + "," + EMERGENCYMOTORSCUT + _eof;
  }
  
  //message (ordre) qui sera envoye au drone
  public  void setMessage(String m){
	  _message = m;
  }
  
  //fonction qui permet de convertir un float en int selon la norme IEE754
  public static int convert754(double x){
	  return Float.floatToRawIntBits((float)x);
  }
  
  public static void main (String[] args){
	  ClientJavaUDP ardrone = new ClientJavaUDP("192.168.1.1", 5556, "\r", "AR-Drone");
	  ClientJavaUDP localhost = new ClientJavaUDP("localhost", 7000, "\n", "localhost");
	  
	  ardrone.start();
	  localhost.start();
  }
}
