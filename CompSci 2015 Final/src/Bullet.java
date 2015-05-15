import java.awt.Color;
import java.awt.Graphics;


public class Bullet {
	private double x,y;
	private double xIncrement, yIncrement;
	private double tarAngle;
	
	private int speed = 10;
	
	private int width = 10, height = 10;

	public Bullet(int x, int y, int targetX, int targetY){
		this.x = x;
		this.y = y;

		vectorize(x, y, targetX, targetY);
	}
	
	private void vectorize(int x, int y, int targetX, int targetY){
		double xComponent = (targetX - x);
		double yComponent = (targetY - y);
		tarAngle = Math.atan2(yComponent, xComponent);
		
		xIncrement = (speed*Math.cos(tarAngle));
		yIncrement = (speed*Math.sin(tarAngle));
	}
	
	public void update(){
		updatePosition();
	}
	
	private void updatePosition(){	
		x += xIncrement;
		y += yIncrement;
		
		if(x < 0 || y < 0 || x > Game.WIDTH || y > Game.HEIGHT){
			Game.bullets.remove(this);
		}
	}
	
	public void draw(Graphics graphics){
		graphics.setColor(Color.green);
		graphics.fillRect((int)x, (int)y, width, height);
		
	}
}
