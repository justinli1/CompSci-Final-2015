
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy {
	private int x, y;
 	private int width = 50, height = 50;

 	private Rectangle2D collision;
 	
 	private int health;
 	private int speed;
 	
	private int shootCooldown = 12;
	private int shootTime;
	
	private boolean active;
	
	private BufferedImage sprite;
	
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.collision = new Rectangle(x, y, width, height);
		
		setHealth(2);
		setSpeed(2);
		
		try {
			setSprite(ImageIO.read(new File("res/enemy/med.png")));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
		this.active = false;
		this.shootTime = (int)System.currentTimeMillis()/100;
	}
	
	public void update() {
		updatePosition();
		updateCollision();
		
		if(active){
			updateShoot();
		}
	}
	
	private void updatePosition(){
		if(active)
			y += getSpeed();
		else
			y += 1;
		
		if(y > Game.HEIGHT)
			Game.enemies.remove(this);
		if(y < Game.HEIGHT && y > 0)
			active = true;
		
		if(health <= 0){
			Game.enemies.remove(this);
			Game.score += 1000;
			
			createPowerUp();
		}
	}
	
	private void createPowerUp(){
		int random = (int)(Math.random()*10);
		
		if(random < 5){
			PowerUp powerUp = new PowerUp(getXCentre(), getYCentre(), random);
			Game.powerUps.add(powerUp);
		}
	}
	
	private void updateShoot(){
		if((int)System.currentTimeMillis()/100 - shootTime > shootCooldown){
			Bullet test = new Bullet(getXCentre(), getYCentre(), Game.player.getXCentre(), Game.player.getYCentre(), 5, Game.player);
			Game.bullets.add(test);
			
			shootTime = (int)System.currentTimeMillis()/100;
		}
	}
	
	private void updateCollision(){
		this.collision.setRect(x, y, width, height);
	}
	
	public void draw(Graphics graphics){
		graphics.setColor(Color.red);
		graphics.drawImage(sprite, x, y, null);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getXCentre(){
		return  x + (width/2);
	}
	
	public int getYCentre(){
		return y + (height/2);
	}
	
	public Rectangle2D getCollision(){
		return collision;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getShootCooldown() {
		return shootCooldown;
	}

	public void setShootCooldown(int shootCooldown) {
		this.shootCooldown = shootCooldown;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	
}
