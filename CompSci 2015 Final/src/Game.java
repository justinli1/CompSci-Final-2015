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
	public static int score;
	
	public static Player player;
	
	private Scanner scores;
	
	
	public static final int GAME_MENU = 4, GAME_PLAY = 0, GAME_SCORES = 2, GAME_HELP = 1;
	private int gameState = GAME_MENU;
	private int menuSelection = GAME_PLAY;
	
	//initialize game objects, load media(pics, music, etc)
	public void init() {
		player = new Player(200, Game.HEIGHT);
		
		WaveSorter waveSorter = new WaveSorter(5);

		ui = new UI(this, player);
		score = 0;
		
	}
	
	//update game objects
	public void update() {
		
		if(gameState == GAME_MENU){
			updateMenu();
		}
		
		if(gameState == GAME_PLAY)
			updateGame();
		
	}
	
	private void updateMenu(){
		
		if(input.getKey(KeyEvent.VK_UP) && menuSelection > 0){
			menuSelection--;
			input.key.put(KeyEvent.VK_UP, false);
		}
		if(input.getKey(KeyEvent.VK_DOWN) && menuSelection < 2){
			menuSelection++;
			input.key.put(KeyEvent.VK_DOWN, false);
		}
		
		if(input.getKey(KeyEvent.VK_Z)){
			gameState = menuSelection;
		}
	}
	
	private void updateGame(){
		player.update(input);
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
	
	private void drawMenu(){
		graphics.setColor(Color.blue);
		
		graphics.fillRect(50, 50, 200, 100);
		graphics.fillRect(50, 200, 200, 100);
		graphics.fillRect(50, 350, 200, 100);
		
		graphics.setColor(Color.red);
		graphics.fillRect(50, 50 + 150*menuSelection, 200, 100);
		
		graphics.setColor(Color.black);
		graphics.drawString("PLAY" ,75, 75);
		graphics.drawString("HELP", 75, 225);
		graphics.drawString("HIGHSCORES", 75, 375);
	}
	
	private void drawScores(){
		try {
			scores = new Scanner(new File("res/scores.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("no file");
		}
		
		
		for(int i = 0; i < 5; i++){
			String score = scores.nextLine();
			graphics.setColor(Color.blue);
			
			graphics.drawString(score, 100, 100 + (i*100));
		}
	}
	
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
