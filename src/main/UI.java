package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	
	Font arial_40, arial_80b;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCount = 0;
	public boolean finished = false;
	public String currentDialogue;
	
	
	public UI(GamePanel gp) {
		
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80b = new Font("Arial", Font.BOLD, 80);

	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		//make into switch statement
		//play state
		if(gp.gameState == gp.playState) {
			//do play state stuff later
		}
		//pause state
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		//dialogue state
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
		
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
		
	}
	
	public void drawDialogueScreen() {
		
		//dialogue window
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int width  = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x, y, width, height, 25, 25);
		
	}
	
	public int getXForCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}
		
}
