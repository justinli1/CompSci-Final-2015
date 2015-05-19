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
		
	}
	
}
