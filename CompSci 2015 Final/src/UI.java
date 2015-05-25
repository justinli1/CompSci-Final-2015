import java.awt.Color;
import java.awt.Graphics;


public class UI {
	private Player player;
	
	private int scoreDisplay;
	private int healthDisplay;
	private int powerDisplay;
	private int speedDisplay;
	
	private Background bg1;
	private Background fg1;
	
	private int x = 0;
	
	
	public UI(Game game, Player player){
		this.player = player;
		
		bg1 = new Background("res/bg1.jpg", 2);
		fg1 = new Background("res/fg1.png", 12);
	}
	
	public void update(){
		healthDisplay = player.getHealth();
		powerDisplay = player.getPower();
		speedDisplay = player.getSpeedBoost();
		scoreDisplay = Game.score;
		
		bg1.update();
		fg1.update();
	}
	
	public void draw(Graphics graphics){
		bg1.draw(graphics);
		fg1.draw(graphics);
		
		graphics.setColor(Color.red);
		
		graphics.drawString("SCORE: " + scoreDisplay, 689, 24);
		
		graphics.drawString("HEALTH", x + 689, 48);
		graphics.drawRect(x + 689, 48, 312, 48);
		graphics.fillRect(x + 689, 48, 104*healthDisplay, 48);
		
		graphics.drawString("POWER", x + 689, 144);
		graphics.drawRect(x + 689, 144, 312, 48);
		graphics.fillRect(x + 689, 144, 104*powerDisplay, 48);
		
		graphics.drawString("SPEED", x + 689, 240);
		graphics.drawRect(x + 689, 240, 312, 48);
		graphics.fillRect(x + 689, 240, 104*speedDisplay, 48);
	}
	
}
