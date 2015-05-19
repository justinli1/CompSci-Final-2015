import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;

import javax.imageio.ImageIO;

public class Player {
	private int x, y;
	private int width, height;
	private Rectangle2D collision;
	
	private int speed = 5;
	private int health;
	private int power;
	private int speedBoost;
	
	private int shootCooldown = 1;
	private int shootTime;
	
	private BufferedImage sprite;
	
	public Player(int x, int y){
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height = 50;
		
		this.health = 3;
		this.power = 1;
		this.speedBoost = 0;
		
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
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getSpeedBoost() {
		return speedBoost;
	}

	public void setSpeedBoost(int speedBoost) {
		this.speedBoost = speedBoost;
	}
	
	
}
