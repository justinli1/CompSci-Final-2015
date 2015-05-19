
public class EnemyHeavy extends Enemy{

	public EnemyHeavy(int x, int y, Player player) {
		super(x, y, player);
		
		setWidth(75);
		setHeight(75);
		setShootCooldown(15);
		setSpeed(1);
	}
	
}
