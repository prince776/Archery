package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game implements Runnable{
	
	private JFrame frame;
	private Canvas canvas;
	private Thread thread;
	
	public int width,height;
	public String title;
	private boolean running = false;

	private BufferStrategy bs;
	private Graphics g;
	
	public KeyManager keyManager;
	public MouseManager mouseManager;
	
	public Assets assets;
	public Sounds sounds;
	
	//
	
	private Player player;
	
	public static Vector gravity = new Vector(0,0.14f);
	
	public Game(String title, int width,int height){
		this.width = width;
		this.height = height;
		this.title = title;
		initDisplay();
	}
	
	public void initDisplay(){
		frame = new JFrame(title);
		frame.setSize(width,height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width,height));
		canvas.setMaximumSize(new Dimension(width,height));
		canvas.setMinimumSize(new Dimension(width,height));
		
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
		
		keyManager = new KeyManager();
		frame.addKeyListener(keyManager);
		
		mouseManager = new MouseManager();
		frame.addMouseListener(mouseManager);
		frame.addMouseMotionListener(mouseManager);
		canvas.addMouseListener(mouseManager);
		canvas.addMouseMotionListener(mouseManager);
		
	}
	
	public void init(){
		assets = new Assets();
		assets.init();
		
		sounds = new Sounds();
		sounds.init();
		
		player = new Player(80, 350, Color.white);
	}
	
	public void tick(){
		player.tick(this);
	}
	
	public void render(){
		bs = canvas.getBufferStrategy();
		if(bs == null){
			canvas.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.clearRect(0, 0, width, height);
		//

		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height);
		
		player.render(g,assets);
		
		//
		bs.show();
		g.dispose();
	}
	
	public void run(){
		init();
		long lastTime = System.nanoTime(),now,timer=0;
		double delta =0,  nsPerTick = 1000000000/60;
		int frames = 0;
		
		while(running){
			now = System.nanoTime();
			timer += now-lastTime;
			delta += (now-lastTime)/nsPerTick;
			lastTime=now;
			if(delta >= 1){
				tick();
				render();
				frames++;
				delta--;
			}
			if(timer >= 1000000000){
				frame.setTitle(title + " FPS: " + frames);
				frames = 0;
				timer = 0;
			}
		}	
	}
	
	public void start(){
		if(!running){
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void stop(){
		if(running){
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.err.println("Error while terminating the thread!");
			}
		}
	}
	
}
