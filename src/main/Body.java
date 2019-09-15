package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Body {
	
	public Head head;
	public Limb leftH,rightH,leftL,rightL;
	public Center body;
	public Color color;
	
	public static int headR = 6,limbW = 20,limbH = 5,bodyW = 15, bodyH = 18;
	
	public Body(int x, int y, Color color){
		
		head = new Head(x, y-bodyH/2-headR-1, headR);
		body = new Center(x-bodyW/2, y-bodyH/2, bodyW, bodyH, 0);
		
		leftL = new Limb(x-bodyW/2+1, y+bodyH/2+1, limbH, limbW, (float)0,limbH/2,0);
		rightL = new Limb(x+bodyW/2-limbH, y+bodyH/2+1, limbH, limbW, (float)0,limbH/2,0);
		
		leftH = new Limb(x-bodyW/2-1, y-bodyH/2+1, limbW, limbH, (float)Math.PI,0,limbH/2);
		rightH = new Limb(x+bodyW/2+2, y-bodyH/2, limbW, limbH, 0,0,limbH/2);
		
		this.color = color;
		
	}
	
	public void tick(){

	}
	
	public void render(Graphics g){
		
		head.render(g, color);
		body.render(g, color);
		leftH.render(g, color);
		rightH.render(g, color);
		leftL.render(g, color);
		rightL.render(g, color);
		
//		g.setColor(Color.red);
//		g.drawOval(leftH.x + leftH.jointX -1, leftH.y + leftH.jointY-1 , 2, 2);

	}		
	
	
	class Head{
		int x,y,r;
		
		public Head(int x,int y,int r){
			this.x=x;
			this.y=y;
			this.r=r;
		}
		
		public void render(Graphics g, Color color){
			g.setColor(color);
			g.fillOval(x-r, y-r, 2*r, 2*r);
		}
		
	}	
	
	class Limb{
		int x,y,w,h;
		float rot;
		int jointX,jointY;
		
		public Limb(int x,int y,int w,int h, float rot,int jointX , int jointY){
			this.x=x;
			this.y=y;
			this.rot=rot;
			this.w=w;
			this.h=h;
			this.jointX = jointX;
			this.jointY = jointY;
		}
		
		public void render(Graphics g1,Color color){
			Graphics2D g = (Graphics2D)g1;
			
			
			g.translate(x + jointX, y + jointY);
			g.rotate(-rot);
			g.translate(-jointX, -jointY);
			
			g.setColor(color);
			g.fillRect(0, 0, w, h);
			
			g.translate(jointX,jointY);
			g.rotate(rot);
			g.translate(-x-jointX,-y-jointY);
			
		}
		
	}
	
	class Center{
		int x,y,w,h;
		float rot;
		
		public Center(int x,int y,int w,int h, float rot){
			this.x=x;
			this.y=y;
			this.rot=rot;
			this.w=w;
			this.h=h;
		}	
		
		public void render(Graphics g1,Color color){
			Graphics2D g = (Graphics2D)g1;
			
			g.setColor(color);
			

			g.translate(x, y);
			g.rotate(-rot);
			
			g.fillRect(0, 0, w, h);
			
			g.rotate(rot);
			g.translate(-x,-y);
			
		}		
	}
	
}	


