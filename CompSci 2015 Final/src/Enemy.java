
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Enemy {
	private int x, y;
 	private int width = 50, height = 50;
	
 	private Player playerReference;
 	private Rectangle2D collision;
 	
 	private int health;
 	private int speed;
 	
	private int shootCooldown = 12;
	private int shootTime;
	
	public Enemy(int x, int y, Player player) {
		this.x = x;
		this.y = y;
		this.playerReference = player;
		this.collision = new Rectangle(x, y, width, height);
		
		setHealth(3);
		setSpeed(2);
		
		this.shootTime = (int)System.currentTimeMillis()/100;
	}
	
	public void update() {
		updatePosition();
		updateShoot();
		updateCollision();
	}
	
	private void updatePosition(){
		y += getSpeed();
		
		if(y > Game.HEIGHT)
			Game.enemies.remove(this);
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
			Bullet test = new Bullet(getXCentre(), getYCentre(), playerReference.getXCentre(), playerReference.getYCentre(), 5, playerReference);
			Game.bullets.add(test);
			
			shootTime = (int)System.currentTimeMillis()/100;
		}
	}
	
	private void updateCollision(){
		this.collision.setRect(x, y, width, height);
	}
	
	public void draw(Graphics graphics){
		graphics.setColor(Color.red);
		graphics.fillRect(x, y, width, height);
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
	
	
}
