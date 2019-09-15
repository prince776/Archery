package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Arrow {
	
	public Vector pos,vel,acc,dir;
	public static int length = 50;
	public Color color;
	
	public boolean outside = false,stopped = false,hitted = false;
	public long lastTime,stoppedTime = 0 , maxTimeStopped = 3000; //once stopped
	
	
	public Arrow(Vector pos, Vector vel, Vector acc, Color color,boolean stopped){
		this.pos = pos;
		this.vel = vel;
		this.dir = new Vector(vel);
		this.acc = acc;
		this.color = color;
		this.stopped=stopped;
	}
	
	public void applyAcc(Vector acc){
		this.acc.add(acc);
	}
	
	public void tick(Game game){
		applyAcc(Game.gravity);
		if(!stopped && !hitted){
			vel.add(acc);
			pos.add(vel);
		}
		acc.mul(0);
		
		dir.x = vel.x;
		dir.y = vel.y;
		
		int x2 = (int)pos.x + (int)((dir.x/dir.getMag())*length);		
		int y2 = (int)pos.y + (int)((dir.y/dir.getMag())*length)+100;		
		int x  = (int)pos.x;
		int y  = (int)pos.y+100;
		
		
		if(!hitted && !stopped){
			if(Game.player.getRect().contains(x2,y2-100)){
				hitted = true;
				lastTime = System.currentTimeMillis();
			}
			if(Game.playerMP != null){
				 if(Game.playerMP.getRect().contains((x2+x)/2 , (y2+y-200)/2)){
					 hitted = true;
					lastTime = System.currentTimeMillis();
				}
			}
		}
		
		if(hitted){
			long now = System.currentTimeMillis();
			stoppedTime += now - lastTime;
			lastTime = now;
			if(stoppedTime > maxTimeStopped){
				outside = true;
				stoppedTime = 0;
				hitted = false;
			}
		}
		
		Rectangle rect = new Rectangle(0,0,game.width,game.height+100);
		if(!rect.contains(x,y) && ! rect.contains(x2,y2)){
			outside = true;
		}
	}
	
	
	public void render(Graphics g,Assets assets){
		int x2 = (int)pos.x + (int)((dir.x/dir.getMag())*length);		
		int y2 = (int)pos.y + (int)((dir.y/dir.getMag())*length);
//		
//		g.setColor(Color.red);
//		g.fillOval(x2-2, y2-2, 4, 4);
//		
//		g.setColor(color);
//		g.drawLine((int)pos.x	, (int)pos.y	,x2		, y2);
//		g.drawLine((int)pos.x+1	, (int)pos.y	,1 + x2	, y2);
//		g.drawLine((int)pos.x	, (int)pos.y+1	,+ x2	, y2+1);
//		
//		g.fillOval(x2-2, y2-2, 6, 6);
		float dy = y2-pos.y;
		float dx = x2-pos.x;
		float slope=0;
		
		float rot ;
		
		if(dx != 0)	
			slope =  ((y2-pos.y)/(x2-pos.x));
		else{
			if(dy>=0)
				rot = (float)Math.PI/2;
			else
				rot = -(float)Math.PI/2;
		}
		
		rot = (float)Math.atan(slope);
		
		if(dy<=0 && dx <=0)
			rot += (float)Math.PI;
		if(dy>=0 && dx <=0)
			rot += (float)Math.PI;
			
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(pos.x, pos.y + assets.arrow.getHeight()/2);
		g2.rotate(rot);
		g2.translate(0, -assets.arrow.getHeight()/2);
		
		g2.drawImage(assets.arrow, 0,0,length,10,null);
		
		g2.translate(0,assets.arrow.getHeight()/2);
		g2.rotate(-rot);
		g2.translate(-pos.x, -pos.y - assets.arrow.getHeight()/2);
		
	}
	
	public Vector getMidPoint(){
		int x2 = (int)pos.x + (int)((dir.x/dir.getMag())*length*0.5f);		
		int y2 = (int)pos.y + (int)((dir.y/dir.getMag())*length*0.5f);
		return new Vector(x2-pos.x,y2-pos.y);
	}
	
}
