import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;




public class EnemyLight extends Enemy{

	public EnemyLight(int x, int y) {
		super(x, y);
		
		setWidth(25);
		setHeight(25);	
		setShootCooldown(8);
		setSpeed(4);
		
		try {
			setSprite(ImageIO.read(new File("res/enemy/light.png")));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
	}
		
}
