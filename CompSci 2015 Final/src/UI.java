import java.awt.Color;
import java.awt.Graphics;


public class UI {
	private Player player;
	
	private int scoreDisplay;
	private int healthDisplay;
	private int powerDisplay;
	private int speedDisplay;
	private int bombDisplay;
	
	private Background bg1;
	private Background fg1;
	
	private int x = 0;
	
	//keeps track of and displays player stats
	//also displays multiple backgrounds
	public UI(Game game, Player player, String directory){
		this.player = player;
		
		bg1 = new Background(directory + "/bg.jpg", 2);
		fg1 = new Background(directory + "/fg.png", 12);
	}
	
	public void update(){
		scoreDisplay = Game.score;
		
		healthDisplay = player.getHealth();
		powerDisplay = player.getPower();
		speedDisplay = player.getSpeedBoost();
		bombDisplay = player.getBombs();
		
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
		
		graphics.drawString("BOMBS", x + 689, 336);
		graphics.drawRect(x + 689, 336, 312, 48);
		graphics.fillRect(x + 689, 336, 104*bombDisplay, 48);
		
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
