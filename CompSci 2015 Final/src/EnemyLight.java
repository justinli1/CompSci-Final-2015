


public class EnemyLight extends Enemy{

	public EnemyLight(int x, int y, Player player) {
		super(x, y, player);
		
		setWidth(25);
		setHeight(25);	
		setShootCooldown(8);
		setSpeed(4);
	}
		
}
