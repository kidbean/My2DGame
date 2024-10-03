package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{
	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
	}
	
	public void getImage() {
		up1 = setup("/NPC/oldman_up_1");
		up2 = setup("/NPC/oldman_up_2");
		down1 = setup("/NPC/oldman_down_1");
		down2 = setup("/NPC/oldman_down_2");
		left1 = setup("/NPC/oldman_left_1");
		left2 = setup("/NPC/oldman_left_2");
		right1 = setup("/NPC/oldman_right_1");
		right2 = setup("/NPC/oldman_right_2");
	}
	
	public void setDialogue() {
		
		dialogues[0] = "Now then laddie!";
		dialogues[1] = "Been a while since we've had \nsomeone come to the island.";
		dialogues[2] = "I used to be a wizard, but alas, \nI'm stranded here! No magic! \nNo hope...";
		dialogues[3] = "Please help me off this vile land mass! \nI'll make it worth your while.";
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter >= 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1; //pick a number from 1 - 100
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
	}
	
	public void speak() {
		super.speak();
	}


}
