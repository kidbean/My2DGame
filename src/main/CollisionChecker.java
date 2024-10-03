package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		
		this.gp = gp;
	}
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + entity.collisionBox.x;
		int entityRightWorldX = entity.worldX + entity.collisionBox.x + entity.collisionBox.width;
		int entityTopWorldY = entity.worldY + entity.collisionBox.y;
		int entityBottomWorldY = entity.worldY + entity.collisionBox.y + entity.collisionBox.height;
		
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNumber[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNumber[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNumber[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		for(int i = 0; i < gp.obj.length; i++) {
			
			if(gp.obj[i] != null) {
				//get entity collision box position
				entity.collisionBox.x = entity.worldX + entity.collisionBox.x;
				entity.collisionBox.y = entity.worldY + entity.collisionBox.y;
				
				gp.obj[i].collisionBox.x = gp.obj[i].worldX + gp.obj[i].collisionBox.x;
				gp.obj[i].collisionBox.y = gp.obj[i].worldY + gp.obj[i].collisionBox.y;
				
				switch(entity.direction) {
				case "up":
					entity.collisionBox.y -= entity.speed;
					if(entity.collisionBox.intersects(gp.obj[i].collisionBox)) {
						if(gp.obj[i].collision) {
							entity.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				case "down":
					entity.collisionBox.y += entity.speed;
					if(entity.collisionBox.intersects(gp.obj[i].collisionBox)) {
						if(gp.obj[i].collision) {
							entity.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				case "left":
					entity.collisionBox.x -= entity.speed;
					if(entity.collisionBox.intersects(gp.obj[i].collisionBox)) {
						if(gp.obj[i].collision) {
							entity.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				case "right":
					entity.collisionBox.x += entity.speed;
					if(entity.collisionBox.intersects(gp.obj[i].collisionBox)) {
						if(gp.obj[i].collision) {
							entity.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				}
				entity.collisionBox.x = entity.collisionBoxDefaultX;
				entity.collisionBox.y = entity.collisionBoxDefaultY;
				gp.obj[i].collisionBox.x = gp.obj[i].collisionBoxDefaultX;
				gp.obj[i].collisionBox.y = gp.obj[i].collisionBoxDefaultY;
			}
		}
		
		return index;
	}
	
	//check NPC or monster collision
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for(int i = 0; i < target.length; i++) {
			
			if(target[i] != null) {
				//get entity collision box position
				entity.collisionBox.x = entity.worldX + entity.collisionBox.x;
				entity.collisionBox.y = entity.worldY + entity.collisionBox.y;
				
				target[i].collisionBox.x = target[i].worldX + target[i].collisionBox.x;
				target[i].collisionBox.y = target[i].worldY + target[i].collisionBox.y;
				
				switch(entity.direction) {
				case "up":
					entity.collisionBox.y -= entity.speed;
					if(entity.collisionBox.intersects(target[i].collisionBox)) {
						index = i;
					}
					break;
				case "down":
					entity.collisionBox.y += entity.speed;
					if(entity.collisionBox.intersects(target[i].collisionBox)) {
						index = i;
					}
					break;
				case "left":
					entity.collisionBox.x -= entity.speed;
					if(entity.collisionBox.intersects(target[i].collisionBox)) {
						index = i;
					}
					break;
				case "right":
					entity.collisionBox.x += entity.speed;
					if(entity.collisionBox.intersects(target[i].collisionBox)) {
						index = i;
					}
					break;
				}
				entity.collisionBox.x = entity.collisionBoxDefaultX;
				entity.collisionBox.y = entity.collisionBoxDefaultY;
				target[i].collisionBox.x = target[i].collisionBoxDefaultX;
				target[i].collisionBox.y = target[i].collisionBoxDefaultY;
			}
		}
		
		return index;
	}
	
	public void checkPlayer(Entity entity) {
			//get entity collision box position
			entity.collisionBox.x = entity.worldX + entity.collisionBox.x;
			entity.collisionBox.y = entity.worldY + entity.collisionBox.y;
			
			gp.player.collisionBox.x = gp.player.worldX + gp.player.collisionBox.x;
			gp.player.collisionBox.y = gp.player.worldY + gp.player.collisionBox.y;
			
			switch(entity.direction) {
			case "up":
				entity.collisionBox.y -= entity.speed;
				if(entity.collisionBox.intersects(gp.player.collisionBox)) {
					entity.collisionOn = true;
				}
				break;
			case "down":
				entity.collisionBox.y += entity.speed;
				if(entity.collisionBox.intersects(gp.player.collisionBox)) {
					entity.collisionOn = true;
				}
				break;
			case "left":
				entity.collisionBox.x -= entity.speed;
				if(entity.collisionBox.intersects(gp.player.collisionBox)) {
					entity.collisionOn = true;
				}
				break;
			case "right":
				entity.collisionBox.x += entity.speed;
				if(entity.collisionBox.intersects(gp.player.collisionBox)) {
					entity.collisionOn = true;
				}
				break;
			}
			entity.collisionBox.x = entity.collisionBoxDefaultX;
			entity.collisionBox.y = entity.collisionBoxDefaultY;
			gp.player.collisionBox.x = gp.player.collisionBoxDefaultX;
			gp.player.collisionBox.y = gp.player.collisionBoxDefaultY;
	}

}
