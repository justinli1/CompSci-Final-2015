import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class PowerUp {
	private int x, y;
	private int width = 20, height = 20;
	
	private Rectangle2D collision;
	private int speed = 2;
	private int[] upgrade = new int[5];
	
	public PowerUp(int x, int y, int random) {
		this.x = x;
		this.y = y;
		
		upgrade[random] = 1;
		
		collision = new Rectangle(x, y, width, height);
	}
	
	public void update(){
		updatePosition();
		updateCollision();
	}
	
	private void updatePosition(){
		y += speed;
	}
	
	private void updateCollision(){
		collision.setRect(x, y, width, height);
	}
	
	public void draw(Graphics graphics){
		graphics.setColor(Color.cyan);
		graphics.fillRect(x,y,width,height);
	}
	
	public Rectangle2D getCollision(){
		return collision;
	}
	
	public int[] getUpgrade(){
		return upgrade;
	}

}
