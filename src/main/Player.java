package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Body.Limb;

public class Player {
	
	private Body body;
	public ArrayList<Arrow> arrows;
	public static final float maxThrowVel = 10f;//at 50 pixels length
	public static final int maxThrowLength = 30; // per unit
	
	public float lastThrowVel = 0;
	
	private int tx1,tx2,ty1,ty2;
	
	public Player(int x,int y, Color color){
		
		this.body = new Body(x, y, color);
		this.body.leftH.rot = (float)(Math.PI/3 + Math.PI);
		this.body.rightH.rot = -(float)Math.PI/3;
		this.arrows = new ArrayList<Arrow>();
		
	}
	
	public void tick(Game game){
		getInput(game.mouseManager,game.sounds);
		for(int i=arrows.size()-1;i>=0;i--){
			arrows.get(i).tick(game);
			if(arrows.get(i).outside)
				arrows.remove(i);
		}
		body.tick();
	}
	
	public void render(Graphics g,Assets assets){
		for(int i=arrows.size()-1;i>=0;i--){
			arrows.get(i).render(g,assets);
		}
		body.render(g);
		
		g.setColor(Color.white);
		if(tx1 != 0 && ty1 != 0){
			g.drawLine(tx1, ty1, tx2, ty2);
			g.fillOval(tx1-2, ty1-2, 4, 4);
			g.fillOval(tx2-2, ty2-2, 4, 4);
			
		}
		
		g.fillRect(body.leftL.x - 10, body.leftL.y+body.leftL.h + 1, 10 + body.body.w + 10, 10);
		
		renderPower(g);
		lastThrowVel = 0;
		
	}
	
	public void renderPower(Graphics g){
		
		g.setColor(new Color(255-(int)((lastThrowVel/maxThrowVel)*255),(int)((lastThrowVel/maxThrowVel)*255),0));
		g.fillRect(5, 5, (int)((lastThrowVel/maxThrowVel)*80), 15);
		
		g.setColor(Color.ORANGE);
		g.drawRect(5, 5, 80, 15);
		
	}

	public float calculateXOff(Vector vel){
		float xOff = (vel.getMag()/(maxThrowVel*maxThrowLength)) * Arrow.length;
		if(xOff >= Arrow.length)
			xOff = Arrow.length;
		vel.mul((1f/(float)maxThrowLength));
		if(vel.getMag()> maxThrowVel){
			vel.setMag(maxThrowVel);
		}
		lastThrowVel = vel.getMag();
		xOff -= Arrow.length/2;
		if(tx1< tx2)
			xOff *= -1;
		return 0;
	}
	
	public void getInput(MouseManager mouse,Sounds sounds){
		if(mouse.clicking){
			if (tx1 == 0 || ty1 == 0){
				tx1 = mouse.x;
				ty1 = mouse.y;
				Vector vel = new Vector(tx1-tx2 , ty1-ty2);
				float xOff = calculateXOff(vel);
				arrows.add(new Arrow(new Vector(body.head.x-xOff,body.head.y -
						body.head.r-10), vel, new Vector(), Color.LIGHT_GRAY,true));
//				adjustLimb(body.leftH,arrows.get(arrows.size()-1));
//				adjustLimb(body.rightH, arrows.get(arrows.size()-1));

			}
			
			Vector vel = new Vector(tx1-tx2 , ty1-ty2);
			float xOff = calculateXOff(vel);
			arrows.get(arrows.size()-1).vel = vel;
			arrows.get(arrows.size()-1).pos.x = body.head.x-xOff;
			
//			adjustLimb(body.leftH,arrows.get(arrows.size()-1));
//			adjustLimb(body.rightH, arrows.get(arrows.size()-1));

			
			tx2 = mouse.x;
			ty2 = mouse.y;
		}
		if(!mouse.clicking){
			if(tx1 !=0 && tx2 != 0)
				launchArrow(sounds);
			tx1 = 0;
			ty1 = 0;
		}
	}
	
	public void launchArrow(Sounds sounds){
		Vector vel = new Vector(tx1-tx2 , ty1-ty2);
		vel.mul((1f/(float)maxThrowLength));
		if(vel.getMag()> maxThrowVel){
			vel.setMag(maxThrowVel);
		}
		arrows.get(arrows.size()-1).stopped = false;
		sounds.play(sounds.arrow);
	}
	
	public void adjustLimb(Limb limb,Arrow arrow){
		Vector target = arrow.getMidPoint();
		Vector intialPoint = new Vector(limb.x + limb.jointX , limb.y + limb.jointY);
		
		Vector v1 = Vector.sub(target, intialPoint);
		Vector v2 = new Vector((float)Math.cos(limb.rot),(float)Math.sin(limb.rot));

		float dRot = (float)Math.acos(Math.abs(Vector.dot(v1, v2)/v1.getMag()));
		limb.rot += dRot;
		
	}
	
}
