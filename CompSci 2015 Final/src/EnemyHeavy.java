 
public class EnemyHeavy extends Enemy{

	public EnemyHeavy(int x, int y) {
		super(x, y);
		
		setWidth(75);
		setHeight(75);
		setShootCooldown(15);
		setSpeed(0); //TEST re(1)
	}
	
}
