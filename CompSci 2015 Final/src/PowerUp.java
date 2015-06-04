import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PowerUp {
	private int x, y;
	private int width = 20, height = 20;
	
	private Rectangle2D collision;
	private int speed = 2;
	private int[] upgrade = new int[5];
	
	private BufferedImage sprite;
	
	public PowerUp(int x, int y, int random) {
		this.x = x;
		this.y = y;
		
		upgrade[random] = 1;
		
		try {
			setSprite(ImageIO.read(new File("res/upg/upg.png")));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
		
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
		graphics.setColor(Color.black);
		graphics.drawImage(sprite, x, y, null);
		
		String str = null;
		if(upgrade[0] == 1)
			str = "HEALTH";
		else if(upgrade[1] == 1)
			str = "POWER";
		else if(upgrade[2] == 1)
			str = "SPEED";
		else if(upgrade[3] == 1)
			str = "BOMB";
		else if(upgrade[4] == 1)
			str = "INVINCIBILITY";
		graphics.drawString(str, x, y);
	}
	
	public Rectangle2D getCollision(){
		return collision;
	}
	
	public int[] getUpgrade(){
		return upgrade;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

}
