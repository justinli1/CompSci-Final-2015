import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

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
	
	private Player player;
	
	//initialize game objects, load media(pics, music, etc)
	public void init() {
		player = new Player(200, Game.HEIGHT);
		
		Enemy enemy = new Enemy(300, 0, player); //test
		enemies.add(enemy); //test
		
		Enemy enemyLight = new EnemyLight(200, 0, player);
		enemies.add(enemyLight); //test
		
		Enemy enemyHeavy = new EnemyHeavy(400, 0, player); //test
		enemies.add(enemyHeavy); //test
		
		ui = new UI(this, player);
		score = 0;
		
	}
	
	//update game objects
	public void update() {
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
