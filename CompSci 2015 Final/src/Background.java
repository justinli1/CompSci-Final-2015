import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	
	private int scrollSpeed;
	private int y;
	private BufferedImage background;
	
	public Background(String fileName, int scrollSpeed){
		this.background = null;
		this.y = -768;
		try {
			this.background = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
		this.scrollSpeed = scrollSpeed;
	}
	
	public void update(){
		y += scrollSpeed;
		
		if(y > 0)
			y = -768;
	}
	
	public void draw(Graphics graphics){
		graphics.drawImage(background, 51, y, 614, 1536, null);
	}
}
