import java.awt.Color;
import java.awt.Graphics;


public class UI {
	private Game game;
	private Player player;
	
	private int scoreDisplay;
	private int healthDisplay;
	private int powerDisplay;
	private int speedDisplay;
	
	public UI(Game game, Player player){
		this.game = game;
		this.player = player;
	}
	
	public void update(){
		this.healthDisplay = player.getHealth();
		this.powerDisplay = player.getPower();
		this.speedDisplay = player.getSpeedBoost();
		this.scoreDisplay = game.score;
	}
	
	public void draw(Graphics graphics){
		graphics.setColor(Color.red);
		
		graphics.drawString("SCORE: " + scoreDisplay, 689, 24);
		
		graphics.drawString("HEALTH", 689, 48);
		graphics.drawRect(689, 48, 312, 48);
		graphics.fillRect(689, 48, 104*healthDisplay, 48);
		
		graphics.drawString("POWER", 689, 144);
		graphics.drawRect(689, 144, 312, 48);
		graphics.fillRect(689, 144, 104*powerDisplay, 48);
		
		graphics.drawString("SPEED", 689, 240);
		graphics.drawRect(689, 240, 312, 48);
		graphics.fillRect(689, 240, 104*speedDisplay, 48);
	}
	
}
