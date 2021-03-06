import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bullet {
	private double x,y;
	private double xIncrement, yIncrement;
	private double targetAngle;
	
	private int speed;
	
	private int width = 10, height = 10;
	private Rectangle2D collision;
	private Player playerReference;
	private BufferedImage sprite;

	//creates bullet heading towards target
	public Bullet(int x, int y, int targetX, int targetY, int speed, Player player){
		this.x = x;
		this.y = y;
		this.speed = speed;
		
		this.playerReference = player;
		this.collision = new Rectangle(x, y, width, height);
		vectorize(x, y, targetX, targetY);
		
		try {
			if(player != null)
				sprite = ImageIO.read(new File("res/EBullet.png"));
			else
				sprite = ImageIO.read(new File("res/PBullet.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
	
	//creates bullet heading at an angle
	public Bullet(int x, int y, int speed, double angle, Player player){
		this.x = x;
		this.y = y;
		this.speed = speed;
		
		this.playerReference = player;
		this.collision = new Rectangle(x, y, width, height);
		
		this.targetAngle = angle;
		Math.toRadians(targetAngle);
		vectorize(x,y,angle);
		
		try {
			if(player != null)
				sprite = ImageIO.read(new File("res/EBullet.png"));
			else
				sprite = ImageIO.read(new File("res/PBullet.png"));
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
	
	//vector math for normalized movement
	private void vectorize(int x, int y, int targetX, int targetY){
		double xComponent = (targetX - x);
		double yComponent = (targetY - y);
		targetAngle = Math.atan2(yComponent, xComponent);
		
		xIncrement = (speed*Math.cos(targetAngle));
		yIncrement = (speed*Math.sin(targetAngle));
	}
	
	private void vectorize(int x, int y, double angle){
		xIncrement = (speed*Math.cos(angle));
		yIncrement = (speed*Math.sin(angle));
	}
	
	public void update(){
		updatePosition();
		updateCollision(playerReference);
	}
	
	private void updatePosition(){	
		x += xIncrement;
		y += yIncrement;
		
		if(x < 51 || y < 0 || x > 665 || y > Game.HEIGHT){
			Game.bullets.remove(this);
		}
	}
	
	//checks for player collision if from an enemy, else checks for enemy collision
	private void updateCollision(Player player){
		collision.setRect(x, y, width, height);
		
		if(player != null){ //player collision
			if(collision.intersects(player.getCollision())){
				if(player.getHealth() > 0)
					player.setHealth(player.getHealth() - 1);
				Game.bullets.remove(this);
			}
		}
		
		else{ //enemy collision
			for(int i = 0; i < Game.enemies.size(); i++){
				if(collision.intersects(Game.enemies.get(i).getCollision() )){
					Game.enemies.get(i).setHealth(Game.enemies.get(i).getHealth() - 1);;
					Game.bullets.remove(this);
				}
			}
		}
	}
	
	public void draw(Graphics graphics){
		if(playerReference != null)
			graphics.setColor(Color.red);
		else
			graphics.setColor(Color.blue);
		graphics.fillRect((int)x, (int)y, width, height);
		graphics.drawImage(sprite, (int)x, (int)y, null);
		
	}
}
