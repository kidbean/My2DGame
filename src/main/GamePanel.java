package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//Screen settings
	final int originalTileSize = 16; //16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize*scale; //48x48 tile (actual)
	
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixel
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	//public final int worldWidth = maxWorldCol * tileSize;
	//public final int worldHeight = maxWorldRow * tileSize;
	
	int FPS = 60;
	public long drawCount;
	
	TileManager tileM = new TileManager(this);
	public KeyHandler kh = new KeyHandler(this);
	
	Sound music = new Sound();
	Sound SFX = new Sound();
	
	public CollisionChecker colCheck = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	//ENTITY AND OBJECT
	public Player player = new Player(this, kh);
	public SuperObject obj[] = new SuperObject[10];
	public Entity NPC[] = new Entity[10];
	
	//GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	//tested
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		
		this.addKeyListener(kh);
		this.setFocusable(true);
		
	}
	
	public void setUpGame() {
		
		aSetter.setObject();
		aSetter.setNPC();
		playMusic(0);
		stopMusic();
		gameState = playState;
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}


	public void run() {
		
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
			
				update();
				repaint();
				
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				drawCount = 0;
				timer = 0;
			}
			
			
		}
	}
	
	public void update() {
		
		if(gameState == playState) {
			player.update();
			
			for(int i = 0; i < NPC.length; i++) {
				if(NPC[i] != null) {
					NPC[i].update();
				}
			}
		}
		if(gameState == pauseState) {
			
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//debug
		long drawStart = 0;
		if(kh.checkDrawTime) {
			drawStart = System.nanoTime();
		}
		
		//TILE
		tileM.draw(g2);
		
		//OBJECT
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2,  this);
			}
		}
		
		
		//NPC
		for(int i = 0; i < NPC.length; i++) {
			if(NPC[i] != null) {
				NPC[i].draw(g2);
			}
		}
		
		//PLAYER
		player.draw(g2);
		
		
		ui.draw(g2);
		
		if(kh.checkDrawTime) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 500);
		}
		
		
		g2.dispose();
		
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSFX(int i) {
		
		SFX.setFile(i);
		SFX.play();
	}

}
