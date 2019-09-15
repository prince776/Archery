package main;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Assets {
	
	private BufferedImage loadImage(String file){
		try{
			return ImageIO.read(Assets.class.getResource(file));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
//	private BufferedImage getSubImage(BufferedImage image, int x,int y,int width,int height){
//		return image.getSubimage(x, y, width, height);
//	}
	
	public BufferedImage arrow;
	
	public void init(){
		arrow = loadImage("/arrow.png");
	}
	
	
}
