package PartieANOUARetANAS;
import guiModel.ConsoleModel;

import java.io.*;
import java.net.*;

public class Connection {

	private String _addr;
	private int _port;
	private String _eof;
	private int _seq;
	private DatagramSocket _socket;
	private InetAddress _server;
	private DatagramPacket _packet;
	
	private ConsoleModel _consoleModel;
	
	public Connection(String addr, int port, String eof, ConsoleModel consoleModel) {
		_addr = addr;
		_port = port;
		_eof = eof;
		initServer();
		initPacket();
		initSocket();
		_consoleModel = consoleModel;
	}
	
	

	public String getEof() {
		return _eof;
	}



	public int getSeq() {
		return ++_seq;
	}


	public void initPacket() {
		String init = "Init packet";
		byte buffer[] = init.getBytes();
		int length = 0;
		_packet = new DatagramPacket(buffer, length, _server, _port);
	}

	// initialise le serveur
	public void initServer() {
		try {
			_server = InetAddress.getByName(_addr);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	System.out.println("Initialisation serveur\n");
	//_consoleModel.writeInFile("Initializing server.");
	}

	// initialise le socket
	public void initSocket() {
		try {
			_socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		System.out.println("Initialisation socket\n");
		//_consoleModel.writeInFile("Initializing socket.");
	}

	public void sendMessage(String message){
		 byte buffer[] = message.getBytes();
		 _packet.setData(buffer);
		 try {
			_socket.send(_packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println("send message \n");
		 //_consoleModel.writeInFile("Message sent.");
	}
	
}
