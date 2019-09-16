package main;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class GameServer extends Thread{
	
	private DatagramSocket socket;
	public static int port =3000;

	private byte[] data;
	private DatagramPacket packet;
	
	public InetAddress clientIP;
	public int clientPort;
	
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
			parsePacket(packet.getData(), packet.getAddress(),packet.getPort());
			
		}
	}
	
	public void parsePacket(byte[] data,InetAddress ip, int port){
		this.clientIP = ip;
		this.clientPort = port;
		
		String message = new String(data).trim();
		String[] tokens = message.split("\\s+");
		
		String id = tokens[0];
		
		if(id.equalsIgnoreCase("00")){//LOGIN
			Game.playerMP = new PlayerMP(Game.player2X, Game.playerY, Color.white, tokens[1]);
			String toSend = "00 " + Game.player.name + " " + Game.player2X;
			sendData(toSend.getBytes(), ip, port);
			Game.connected = true;
		}else if(id.equalsIgnoreCase("01")){//Arrow launch info
			float x  = toFloat(tokens[1]);
			float y  = toFloat(tokens[2]);
			float vx = toFloat(tokens[3]);
			float vy = toFloat(tokens[4]);
			
			Game.player.arrowsMP.add(new Arrow(new Vector(x,y), new Vector(vx,vy), new Vector(), Color.white, false));
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
	
	public float toFloat(String num){
		try{
			return Float.parseFloat(num);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		return 0;
	}
	
}
