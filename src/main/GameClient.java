package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GameClient extends Thread{
	
	private DatagramSocket socket;
	private InetAddress serverIP;
	
	private DatagramPacket packet;
	private byte[] data;
	
	public GameClient(String serverIP){
		try {
			socket = new DatagramSocket();
			this.serverIP = InetAddress.getByName(serverIP);
		} catch (SocketException  | UnknownHostException e) {
			System.err.println("Failed to initialize GameClient!");
			e.printStackTrace();
		}
		data = new byte[128];
	}
	
	public void run(){
		while(true){
			packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
