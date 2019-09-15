package main;

public class Vector {
	
	public float x,y;
	
	public Vector(float x,float y){
		this.x=x;
		this.y=y;
	}
	
	public Vector(){
		this.x=0;
		this.y=0;
	}
	
	public Vector(Vector v){
		this.x=v.x;
		this.y=v.y;
	}
	
	public void add(Vector v){
		this.x += v.x;
		this.y += v.y;
	}
	
	public void sub(Vector v){
		this.x-=v.x;
		this.y-=v.y;
	}
	
	public void mul(float  scale){
		this.x*=scale;
		this.y*=scale;
	}
	
	public float getMag(){
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public void normalize(){
		float mag = getMag();
		x/=mag;
		y/=mag;
	}
	
	public void setMag(float mag){
		normalize();
		x*=mag;
		y*=mag;
	}
	
	//static methods
	public static Vector add(Vector v1, Vector v2){
		Vector v = new Vector();
		v.x = v1.x + v2.x;
		v.y = v1.y + v2.y;
		return v;
	}
	
	public static Vector sub(Vector v1, Vector v2){
		Vector v = new Vector();
		v.x = v1.x - v2.x;
		v.y = v1.y - v2.y;
		return v;
	}
	
	public static Vector mul(Vector v1,float scale){
		Vector v = new Vector(v1);
		v.mul(scale);
		return v;
	}
	
	public static float dot(Vector v1, Vector v2){
		return v1.x*v2.x + v1.y*v2.y;
	}
	
}
