import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
	int x, y;
	int width = 50, height = 50;
	
	Player playerReference;
	
	public Enemy(int x, int y, Player player) {
		this.x = x;
		this.y = y;
		this.playerReference = player;
	}
	
	public void update() {
		Bullet test = new Bullet(x, y, playerReference.getX(), playerReference.getY());
		Game.bullets.add(test);
	}
	
	public void draw(Graphics graphics){
		graphics.setColor(Color.red);
		graphics.fillRect(x, y, width, height);
	}
}
