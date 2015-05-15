import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.sql.Time;

public class Player {
	private int x, y;
	private int width, height;
	
	private int speed = 5;
	
	private int shootCooldown = 1;
	private int shootTime;
	
	public Player(){
		x = 0;
		y = 0;
		width = 50;
		height = 50;
		
		shootTime = (int)System.currentTimeMillis()/100;
	}
	
	public void update(Input in){
		updatePosition(in);
		updateBoundary();
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
	
	private void updateBoundary(){
		if(x < 0)
			x = 0;
		else if(x + width > Game.WIDTH)
			x = Game.WIDTH - width;
		if(y < 0)
			y = 0;
		else if(y + height > Game.HEIGHT)
			y = Game.HEIGHT - height;
	}
	
	private void updateShoot(Input in){
		
		if(in.getKey(KeyEvent.VK_Z) && (int)System.currentTimeMillis()/100 - shootTime > shootCooldown){
			Bullet test = new Bullet(getCentre() - 5, y, getCentre() - 5, 0);
			Game.bullets.add(test);
			
			shootTime = (int)System.currentTimeMillis()/100;
		}
	}
	
	public void draw(Graphics graphics){
		graphics.fillRect(x, y, width, height);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getCentre(){
		return x + (width/2);
	}
}
