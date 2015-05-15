import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Player {
	private int x, y;
	private int width, height;
	
	private int speed = 5;
	
	public Player(){
		x = 0;
		y = 0;
		width = 50;
		height = 50;
	}
	
	public void update(Input in){
		updatePosition(in);
		updateShoot(in);
	}
	
	private void updatePosition(Input in){
		if(in.getKey(KeyEvent.VK_SHIFT))
			speed = 1;
		else 
			speed = 5;
		
		if(in.getKey(KeyEvent.VK_UP)){
			y-= speed;
		}
		if(in.getKey(KeyEvent.VK_DOWN)){
			y+= speed;
		}
		if(in.getKey(KeyEvent.VK_LEFT)){
			x-= speed;
		}
		if(in.getKey(KeyEvent.VK_RIGHT)){
			x+= speed;
		}
	}
	
	private void updateShoot(Input in){
		if(in.getKey(KeyEvent.VK_Z)){
			Bullet test = new Bullet(getCentre() - 5, y, getCentre() - 5, 0);
			Game.bullets.add(test);
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
