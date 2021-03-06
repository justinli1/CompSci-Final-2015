import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

 
public class EnemyHeavy extends Enemy{

	public EnemyHeavy(int x, int y) {
		super(x, y);
		
		setWidth(75);
		setHeight(75);
		setShootCooldown(21);
		setSpeed(1);
		setHealth(3);
		
		try {
			setSprite(ImageIO.read(new File("res/enemy/heavy.png")));
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
	
}
