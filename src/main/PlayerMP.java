package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;


public class PlayerMP {
	public Body body;
	
	public String name ;
	public int health;
	
	public PlayerMP(int x,int y, Color color,String name){
		this.body = new Body(x, y, color);
		this.body.leftH.rot = (float)(Math.PI/3 + Math.PI);
		this.body.rightH.rot = -(float)Math.PI/3;
		this.name = name;
		this.health = Player.maxHealth;
	}
	
	public void tick(){
		body.tick();
	}
	
	public void render(Graphics g,Game game){
		body.render(g);
		
		
		g.fillRect(body.leftL.x - 10, body.leftL.y+body.leftL.h + 1, 10 + body.body.w + 10, 10);
		
		renderGUI(g,game);
		
	}
	
	public void renderGUI(Graphics g,Game game){
		
		g.setColor(Color.white);
		g.setFont(new Font("Verdana", Font.BOLD, 12));
		g.drawString(name, body.head.x - 20, body.head.y - body.head.r -2);
		//health
		int x = game.width-5-100, y =5;
		if(Game.connected){
			if(!game.runServer){
				x=5;
				y=5;
			}
		}
		g.setColor(Color.white);
		g.fillRect(x,y,(int)(Player.maxHealth*0.01f * health), 15);
		
		g.setColor(Color.gray);
		g.drawRect(x,y, 100, 15);
		
		int winner = declareWinner();
		if (winner == 2){
			g.drawString("WINNER: " + name, game.width/2-50, game.height/2-6);
		}else if(winner == 1){
			g.drawString("WINNER: " + Game.player.name, game.width/2-50, game.height/2-6);
		}
		
	}
	public int declareWinner(){
		int winner = 0;
		if(this.health <= 0)
			winner = 2;
		if(Game.playerMP.health <= 0)
			winner = 1;
		
		return winner;
	}
	
	public Rectangle getRect(){
		return body.getOuterRect();
	}
	
}
