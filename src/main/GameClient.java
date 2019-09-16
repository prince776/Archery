package main;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class GameClient extends Thread{
	
	private DatagramSocket socket;
	public InetAddress serverIP;
	
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
			parsePacket(packet.getData(), packet.getAddress(),packet.getPort());

		}
	}
	
	public void parsePacket(byte[] data,InetAddress ip, int port){
		
		String message = new String(data).trim();
		String[] tokens = message.split("\\s+");
		
		String id = tokens[0];
		
		if(id.equalsIgnoreCase("00")){
			String name = tokens[1];
			String nameP1 = Game.player.name;
			int x2 = toInt(tokens[2]);
			Game.playerMP = new PlayerMP(Game.playerX, Game.playerY, Color.white, name);
			Game.player = new Player(x2, Game.playerY, Color.WHITE, nameP1);
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
	
	public int toInt(String num){
		try{
			return Integer.parseInt(num);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		return 0;
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
