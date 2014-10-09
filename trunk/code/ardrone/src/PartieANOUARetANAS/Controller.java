package PartieANOUARetANAS;
import Convert;

import java.io.*;
import java.net.*;

public class Controller {
	
}
	public Controller(){
		
	}
	
	public void init(){
		//sequence d'initialisation a executer avant tout autre commande a envoyer
		  public void initialize(DatagramSocket socket,InetAddress server) throws InterruptedException{
			  
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
			  Thread.slee
	}
}
