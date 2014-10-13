package PartieANOUARetANAS;

// GEOFFREY:13-10-14:
//	ce import ne sert a rien
//	il essaie d'importer le package Convert qui n'existe pas
//import Convert;

import java.io.*;
import java.net.*;

public class Controller {
	
	public Controller(){
		
	}
	
	public void init(){
		
	}
	//sequence d'initialisation a executer avant tout autre commande a envoyer
	  public void initialize(DatagramSocket socket,InetAddress server) throws InterruptedException{
		  
		  //GEOFFREY:j'ai rajouté Commands. mais il faut des arguments a la fonction configIDS
		  setMessage(Commands.configIDS());
		  sendMessage(socket, server);
		  //GEOFFREY:il manque 2 arguments dans cette fonction...
		  setMessage(Commands.config("custom:session_id", "-all"));
		  sendMessage(socket, server);
		  Thread.sleep(500);
		  
		  setMessage("AT*LED="+ ++_seq+",10,"+Convert.convert754(0.8)+",8"+_eof);
		  sendMessage(socket, server);
		  
		  setMessage(Commands.configIDS());
		  sendMessage(socket, server);
		  setMessage(config("custom:profile_id", "-"+Convert.convertCRC32(Commands.PROFILE)));
		  sendMessage(socket, server);
		  Thread.sleep(500);
		  
		  
		  setMessage(Commands.configIDS());
		  sendMessage(socket, server);
		  setMessage(config("custom:application_id", "-"+Convert.convertCRC32(Commands.APPLI)));
		  sendMessage(socket, server);
		  Thread.sleep(500);
		  
		  
		  setMessage(Commands.configIDS());
		  sendMessage(socket, server);
		  setMessage(config("custom:session_id", Convert.convertCRC32(Commands.SESSION)));
		  sendMessage(socket, server);
		  Thread.sleep(500);
		      
		  setMessage(configIDS());
		  sendMessage(socket, server);
		  setMessage(config("custom:application_id",Convert.convertCRC32(Commands.APPLI)));
		  sendMessage(socket, server);
		  Thread.sleep(500);
		  	  
		  setMessage(configIDS());
		  sendMessage(socket, server);
		  setMessage(config("custom:profile_id", Convert.convertCRC32(Commands.PROFILE)));
		  sendMessage(socket, server);
		  Thread.sleep(500);
		  
		 
		  setMessage(Commands.configIDS());
		  sendMessage(socket, server);
		  setMessage(config("custom:application_desc", Commands.APPLI));
		  sendMessage(socket, server);
		  Thread.sleep(500);
		  
		  setMessage(Commands.configIDS());
		  sendMessage(socket, server);
		  setMessage(config("custom:profile_desc", Commands.PROFILE));
		  sendMessage(socket, server);
		  Thread.sleep(500);
		  	  
		  setMessage(Commands.configIDS());
		  sendMessage(socket, server);
		  setMessage(config("custom:session_desc", Commands.SESSION));
		  sendMessage(socket, server);
		  Thread.sleep(500);
	}
}
