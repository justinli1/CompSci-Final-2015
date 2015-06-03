import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	public static final int HEALTH = 0, POWER = 1, SPEEDBOOST = 2, BOMBS = 3, INVINCIBILITY = 4;
	
	private int x, y;
	private int width, height;
	private Rectangle2D collision;
	
	private int speed = 5;
	
	private int[] stats = new int[5];
	private int invincibleTime;
	
	private int shootCooldown = 1;
	private int shootTime;
	
	private BufferedImage sprite;
	
	public Player(int x, int y){
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height = 50;
		
		this.stats[HEALTH] = 3;
		this.stats[POWER] = 1;
		this.stats[SPEEDBOOST] = 0;
		this.stats[BOMBS] = 1;
		this.stats[INVINCIBILITY] = 0;
		
		this.invincibleTime = (int)System.currentTimeMillis()/100;
		
		this.collision = new Rectangle(x, y, width, height);
		this.shootTime = (int)System.currentTimeMillis()/100;
		
		try {
			sprite = ImageIO.read(new File("res/player/sprite.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
	
	public void update(Input in){
		updatePosition(in);
		updateStats();
		checkBoundary();
		updateCollision();
		updateShoot(in);
	}
	
	private void updatePosition(Input in){
		if(in.getKey(KeyEvent.VK_SHIFT))
			speed = 2;
		else 
			speed = 5;
		
		if(in.getKey(KeyEvent.VK_UP)){
			y-= speed;
		}
		else if(in.getKey(KeyEvent.VK_DOWN)){
			y+= speed;
		}
		if(in.getKey(KeyEvent.VK_LEFT)){
			x-= speed;
		}
		else if(in.getKey(KeyEvent.VK_RIGHT)){
			x+= speed;
		}
	}
	
	public void updateStats(){
		if(stats[HEALTH] > 3)
			stats[HEALTH] = 3;
		if(stats[POWER] > 3)
			stats[POWER] = 3;
		if(stats[SPEEDBOOST] > 3)
			stats[SPEEDBOOST] = 3;
		if(stats[BOMBS] > 3)
			stats[BOMBS] = 3;
		
		if((int)System.currentTimeMillis()/100 < invincibleTime){ //invul effect, make better
			stats[HEALTH] = 3;
		}
		
		//resets invincibility
		else if((int)System.currentTimeMillis()/100 > invincibleTime){
			stats[INVINCIBILITY]  = 0;
		}
	}
	
	private void checkBoundary(){
		if(x < 51)
			x = 51;
		else if(x + width > 665)
			x = 665 - width;
		if(y < 0)
			y = 0;
		else if(y + height > Game.HEIGHT)
			y = Game.HEIGHT - height;
	}
	
	private void updateCollision(){
		this.collision.setRect(x, y, width, height);
		
		for(int i = 0; i < Game.powerUps.size(); i++){
			PowerUp current = Game.powerUps.get(i);
			
			if(collision.intersects(current.getCollision())){
				performUpgrade(current.getUpgrade());
				Game.powerUps.remove(current);
			}
		}
	}
	
	private void performUpgrade(int[] upgrade){
		for(int i = 0; i < upgrade.length; i++){
			stats[i] += upgrade[i];
		}
		
		if(stats[INVINCIBILITY] > 1)
			stats[INVINCIBILITY] = 1;
		
		if(stats[INVINCIBILITY] >= 1){
			invincibleTime = ((int)System.currentTimeMillis()/100) + (stats[INVINCIBILITY] * 50); 
		}
	}
	
	private void updateShoot(Input in){
		if(in.getKey(KeyEvent.VK_Z) && (int)System.currentTimeMillis()/100 - shootTime > shootCooldown){
			Bullet test = new Bullet(getXCentre() - 5, y, getXCentre() - 5, 0, 10,null);
			Game.bullets.add(test);
			
			shootTime = (int)System.currentTimeMillis()/100;
		}
	}
	
	public void draw(Graphics graphics){
		graphics.drawRect(x, y, width, height);
		graphics.drawImage(sprite, x, y, null);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getXCentre(){
		return x + (width/2);
	}
	
	public int getYCentre(){
		return y + (height/2);
	}

	public Rectangle2D getCollision(){
		return collision;
	}
	
	public int getHealth() {
		return stats[HEALTH];
	}

	public void setHealth(int health) {
		this.stats[HEALTH] = health;
	}

	public int getPower() {
		return stats[POWER];
	}

	public void setPower(int power) {
		this.stats[POWER] = power;
	}

	public int getSpeedBoost() {
		return stats[SPEEDBOOST];
	}

	public void setSpeedBoost(int speedBoost) {
		this.stats[SPEEDBOOST] = speedBoost;
	}
	
	public int getBombs(){
		return stats[BOMBS];
	}
	
}
