import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	//screen dimensions and variables
	static final int WIDTH = 1024;
	static final int HEIGHT = WIDTH / 4 * 3; //4:3 aspect ratio
	private JFrame frame;

	//game updates per second
	static final int UPS = 60;
	
	//variables for the thread
	private Thread thread;
	private boolean running;
	
	//used for drawing items to the screen
	private Graphics2D graphics;
	private Input input = new Input();

	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public static ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
		
	private UI ui;
	private int stage;
	public static int score;
	
	public static Player player;
	public static EnemyBoss boss;
	
	private Scores scores;
	
	public static final int GAME_MENU = 3, GAME_PLAY = 0, GAME_SCORES = 1, GAME_END = 2;
	private int gameState = GAME_MENU;
	private int menuSelection = GAME_PLAY;
	
	//initialize game objects, load media(pics, music, etc)
	public void init() {
		player = new Player(307, Game.HEIGHT);
		
		WaveSorter waveSorter = new WaveSorter(5);
		boss = waveSorter.getBoss();
		
		scores = new Scores("res/scores.txt");
		
		stage = 1;
		ui = new UI(this, player, "res/stage" + stage);
		score = 0;
		
	}
	
	//update game objects
	public void update() {
		
		if(gameState == GAME_MENU){
			updateMenu();
		}
		if(gameState == GAME_PLAY)
			updateGame();
		
		if(gameState == GAME_END){
			System.exit(0);
		}
		
		if(gameState == GAME_SCORES)
			updateScores();
		
	}
	
	//updates menu buttons
	private void updateMenu(){
		
		if(input.getKey(KeyEvent.VK_UP) && menuSelection > 0){
			menuSelection--;
			input.key.put(KeyEvent.VK_UP, false);
		}
		else if(input.getKey(KeyEvent.VK_DOWN) && menuSelection < 2){
			menuSelection++;
			input.key.put(KeyEvent.VK_DOWN, false);
		}
		
		if(input.getKey(KeyEvent.VK_Z)){
			gameState = menuSelection;
		}
	}
	
	//updates game objects
	private void updateGame(){
		if(stage == 3){
			gameState = GAME_MENU;
		}
		
		player.update(input);
		
		if(player.getHealth() <= 0){
			player = new Player(307, Game.HEIGHT);
			ui.setPlayer(player);
			player.setInvincibility(1);
			score -= 2000;
		}
		
		if(boss.getHealth() <= 0){
			startNewStage();
		}
		
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).update();
		}
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update();
		}
		
		for(int i = 0; i < powerUps.size(); i++){
			powerUps.get(i).update();
		}
		
		ui.update();
	}
	
	//resets objects for new stage
	private void startNewStage(){
		stage++;
		ui = new UI(this, player, "res/stage" + stage);
		WaveSorter waveSorter = new WaveSorter(5);
		boss = waveSorter.getBoss();
	}
	
	//sorts score ranks
	private void updateScores(){
		scores.setRanks(scores.head);
		scores.rank = 0;
	}
	
	//draw things to the screen
	public void draw() {
		graphics.setColor(Color.gray);
		graphics.fillRect(0,0, WIDTH, HEIGHT);
		
		if(gameState == GAME_MENU){
			drawMenu();
		}
		
		else if(gameState == GAME_SCORES){
			drawScores();
		}
		
		else if(gameState == GAME_PLAY){
			drawGame();
		}
	}
	
	//menu buttons
	private void drawMenu(){
		graphics.setColor(Color.blue);
		
		graphics.fillRect(50, 50, 200, 100);
		graphics.fillRect(50, 200, 200, 100);
		graphics.fillRect(50, 350, 200, 100);
		
		graphics.setColor(Color.red);
		graphics.fillRect(50, 50 + 150*menuSelection, 200, 100);
		
		graphics.setColor(Color.black);
		graphics.drawString("PLAY" ,75, 75);
		graphics.drawString("HIGHSCORES", 75, 225);
		graphics.drawString("EXIT", 75, 375);
	}
	
	//displays highscores in order
	private void drawScores(){
		for(int i = 0; i < scores.size; i++){
			graphics.setColor(Color.red);
			graphics.drawString(i+1 + " " + scores.names[i] + " " + scores.scores[i], 100, 100 + 50*i);
		}
	}
	
	//draws all game objects
	private void drawGame(){
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		ui.draw(graphics);
		
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).draw(graphics);
		}
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(graphics);
		}
		
		for(int i = 0; i < powerUps.size(); i++){
			powerUps.get(i).draw(graphics);
		}
		
		graphics.setColor(Color.green);
		player.draw(graphics);
		
		graphics.setColor(Color.red);
		graphics.drawString("X: " + input.mx + " Y: " + input.my, input.mx, input.my);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.add(game); //game is a component because it extends Canvas
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);		
		game.start();
	}
	
	public Game() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		
		frame = new JFrame();

		//KEYBOARD and MOUSE handling code goes here
		addKeyListener(input);
		addMouseMotionListener(input);
	}

	//starts a new thread for the game
	public synchronized void start() {
		thread = new Thread(this, "Game");
		running = true;
		thread.start();
	}
	
	//main game loop
	public void run() {
		init();
		long startTime = System.nanoTime();
		double ns = 1000000000.0 / UPS;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		long secondTimer = System.nanoTime();
		while(running) {
			long now = System.nanoTime();
			delta += (now - startTime) / ns;
			startTime = now;
			while(delta >= 1) {
				update();
				delta--;
				updates++;
			}
			render();
			frames++;
			
			if(System.nanoTime() - secondTimer > 1000000000) {
				this.frame.setTitle(updates + " ups  ||  " + frames + " fps");
				secondTimer += 1000000000;
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy(); //method from Canvas class
		
		if(bs == null) {
			createBufferStrategy(3); //creates it only for the first time the loop runs (trip buff)
			return;
		}
		
		graphics = (Graphics2D)bs.getDrawGraphics();
		draw();
		graphics.dispose();
		bs.show();
	}
	
	//stops the game thread and quits
		public synchronized void stop() {
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
}
