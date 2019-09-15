package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;


public class PlayerMP {
	private Body body;
	
	public String name ;
	
	public PlayerMP(int x,int y, Color color,String name){
		
		this.body = new Body(x, y, color);
		this.body.leftH.rot = (float)(Math.PI/3 + Math.PI);
		this.body.rightH.rot = -(float)Math.PI/3;
		this.name = name;
	}
	
	public void tick(){
		body.tick();
	}
	
	public void render(Graphics g){
		body.render(g);
		
		
		g.fillRect(body.leftL.x - 10, body.leftL.y+body.leftL.h + 1, 10 + body.body.w + 10, 10);
		
		renderGUI(g);
		
	}
	
	public void renderGUI(Graphics g){
		
		g.setColor(Color.white);
		g.setFont(new Font("Verdana", Font.BOLD, 12));
		g.drawString(name, body.head.x - 20, body.head.y - body.head.r -2);
		
		
	}

	
	public Rectangle getRect(){
		return body.getOuterRect();
	}
	
}
