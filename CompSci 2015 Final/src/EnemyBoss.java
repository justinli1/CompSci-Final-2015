import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class EnemyBoss extends Enemy {

	public EnemyBoss(int x, int y) {
		super(x, y);
		
		setWidth(200);
		setHeight(200);	
		setShootCooldown(4);
		setHealth(30);
		setSpeed(0);
		
		try {
			setSprite(ImageIO.read(new File("res/enemy/boss.png")));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		
	}

}
