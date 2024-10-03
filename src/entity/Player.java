package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	
	KeyHandler kh;
	
	public final int screenX;
	public final int screenY;
	
	//public int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler kh) {
		
		super(gp);
		
		this.kh = kh;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		collisionBox = new Rectangle(8, 16, 32, 32);
		collisionBoxDefaultX = collisionBox.x;
		collisionBoxDefaultY = collisionBox.y;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		up1 = setup("/player/boy_up_1");
		up2 = setup("/player/boy_up_2");
		down1 = setup("/player/boy_down_1");
		down2 = setup("/player/boy_down_2");
		left1 = setup("/player/boy_left_1");
		left2 = setup("/player/boy_left_2");
		right1 = setup("/player/boy_right_1");
		right2 = setup("/player/boy_right_2");
	}
	
	public void update() {
		
		if(kh.upPressed || kh.downPressed || kh.leftPressed || kh.rightPressed) {

			if(kh.upPressed == true) {
				direction = "up";
			}
			else if(kh.downPressed == true) {
				direction = "down";
			}
			else if(kh.leftPressed == true) {
				direction = "left";
			}
			else if(kh.rightPressed == true) {
				direction = "right";
			}
		
			//check tile collision
			collisionOn = false;
			gp.colCheck.checkTile(this);
			
			//check object collision
			int objIndex = gp.colCheck.checkObject(this, true);
			pickUpObject(objIndex);
			
			//check npc collision
			int npcIndex = gp.colCheck.checkEntity(this, gp.NPC);
			interactNPC(npcIndex);
			
			//if collision = false player can move
			if(collisionOn == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		
	}
	
	public void pickUpObject(int i) {
		
		 if(i != 999) {
		
		 }
	}
	
	public void interactNPC(int i) {
		
		if(i != 999) {
			
			if(gp.kh.interactPressed) {
				gp.gameState = gp.dialogueState;
				gp.NPC[i].speak();
			}
		 }
		gp.kh.interactPressed = false;
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
		}
		
		g2.drawImage(image, screenX, screenY, null);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
