package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

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
	
	public void sendData(byte[] data,InetAddress address,int port){
		DatagramPacket p = new DatagramPacket(data, data.length, address,port);
		try {
			socket.send(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
