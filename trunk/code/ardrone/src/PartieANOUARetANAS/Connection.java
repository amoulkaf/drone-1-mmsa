package PartieANOUARetANAS;
import java.io.*;
import java.net.*;

public class Connection {

	private String _addr;
	private int _port;
	private String _eof;
	private String _message;
	DatagramSocket _socket;
	InetAddress _server;
	DatagramPacket _packet;
	
	public Connection(String addr, int port, String eof) {
		_addr = addr;
		_port = port;
		_eof = eof;
		initServer();
		initPacket();
		initSocket();
		
	}

	public void initPacket() {
		byte buffer[] = null;
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

	}

	// initialise le socket
	public void initSocket() {
		try {
			_socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message){
		 byte buffer[] = _message.getBytes();
		 _packet.setData(buffer);
		 try {
			_socket.send(_packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
}
