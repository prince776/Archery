package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class GameServer extends Thread{
	
	private DatagramSocket socket;
	public static int port =3030;

	private byte[] data;
	private DatagramPacket packet;
	
	public GameServer(){
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.err.println("Failed to initialize server: " );
			e.printStackTrace();
		}
		data = new byte[128];
	}
	
	public void run(){
		while(true){
			
			packet = new DatagramPacket(data,data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
