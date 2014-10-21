package PartieANOUARetANAS;

// GEOFFREY:13-10-14:
//	ce import ne sert a rien
//	il essaie d'importer le package Convert qui n'existe pas
//import Convert;

import java.io.*;
import java.net.*;

public class Controller extends Thread {
	private Connection _connection;
	
	public Controller(String addr, int port, String eof){
		_connection = new Connection(addr,port,eof);
	}
	
	public void run(){
		try {
			initialize();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_connection.sendMessage(Commands.check(_connection.getSeq(),_connection.getEof()));
		
	}
	
	//sequence d'initialisation a executer avant tout autre commande a envoyer
	  public void initialize() throws InterruptedException{
		  _connection.sendMessage(Commands.configIDS(_connection.getSeq(),_connection.getEof()));
		  _connection.sendMessage(Commands.config("custom:session_id", "-all", _connection.getSeq(), _connection.getEof()));
		  Thread.sleep(500);
		  
		  _connection.sendMessage(Commands.configIDS(_connection.getSeq(),_connection.getEof()));
		  _connection.sendMessage(Commands.config("custom:profile_id", "-"+Convert.convertCRC32(Commands.PROFILE), _connection.getSeq(), _connection.getEof()));;
		  Thread.sleep(500);
		  
		  _connection.sendMessage(Commands.configIDS(_connection.getSeq(),_connection.getEof()));
		  _connection.sendMessage(Commands.config("custom:application_id", "-"+Convert.convertCRC32(Commands.APPLI), _connection.getSeq(), _connection.getEof()));
		  Thread.sleep(500);
		  
		  _connection.sendMessage(Commands.configIDS(_connection.getSeq(),_connection.getEof()));
		  _connection.sendMessage(Commands.config("custom:session_id", Convert.convertCRC32(Commands.SESSION), _connection.getSeq(), _connection.getEof()));
		  Thread.sleep(500);
		  
		  _connection.sendMessage(Commands.configIDS(_connection.getSeq(),_connection.getEof()));
		  _connection.sendMessage(Commands.config("custom:application_id",Convert.convertCRC32(Commands.APPLI), _connection.getSeq(), _connection.getEof()));
		  Thread.sleep(500);
		  
		  _connection.sendMessage(Commands.configIDS(_connection.getSeq(),_connection.getEof()));
		  _connection.sendMessage(Commands.config("custom:profile_id", Convert.convertCRC32(Commands.PROFILE), _connection.getSeq(), _connection.getEof()));
		  Thread.sleep(500);
		  
		  _connection.sendMessage(Commands.configIDS(_connection.getSeq(),_connection.getEof()));
		  _connection.sendMessage(Commands.config("custom:application_desc", Commands.APPLI, _connection.getSeq(), _connection.getEof()));
		  Thread.sleep(500);
		  
		  _connection.sendMessage(Commands.configIDS(_connection.getSeq(),_connection.getEof()));
		  _connection.sendMessage(Commands.config("custom:profile_desc", Commands.PROFILE, _connection.getSeq(), _connection.getEof()));
		  Thread.sleep(500);
		  	
		  _connection.sendMessage(Commands.configIDS(_connection.getSeq(),_connection.getEof()));
		  _connection.sendMessage(Commands.config("custom:session_desc", Commands.SESSION, _connection.getSeq(), _connection.getEof()));
		  Thread.sleep(500);
	}
}
