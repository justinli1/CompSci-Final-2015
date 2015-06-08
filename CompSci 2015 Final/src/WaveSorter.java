
public class WaveSorter {
	public static final int SCREENS = (-1) * Game.HEIGHT;
	
	private Wave head;
	private int currentWave = 0;
	private EnemyBoss boss;
	
	public WaveSorter(int waves){
		for(int i = 0; i < waves; i++){
			insert(head, new Wave());
		}
		
		initializeEnemies(head);
		initializeBoss();
	}
	
	//Uses tree sort to sort randomly generated waves by difficulty
	//places more difficult waves at the end of the stage
	public void insert(Wave root, Wave wave){
		if(head == null)
			head = wave;
		
		if(root == null){
			root = wave;
		}
		else if(root.getLeft() == null && wave.getDifficulty() <= root.getDifficulty())
			root.setLeft(wave);
		else if(root.getRight() == null && wave.getDifficulty() > root.getDifficulty())
			root.setRight(wave);
		
		
		else if(wave.getDifficulty() <= root.getDifficulty())
			insert(root.getLeft(), wave);
		else if(wave.getDifficulty() > root.getDifficulty())
			insert(root.getRight(), wave);
	}
	
	//initializes the enemies inside each wave with inorder traversal
	public void initializeEnemies(Wave root){
		if(root.getLeft() != null)
			initializeEnemies(root.getLeft());
		
		currentWave++;
		Enemy[][] enemies = root.getWaveArray();
		
		for(int i = 0; i < Wave.LENGTH; i++){
			for(int j = 0; j < Wave.WIDTH; j++){
				if(enemies[i][j] != null){
					enemies[i][j].setY(enemies[i][j].getY() + (currentWave * SCREENS) - SCREENS/2);
					Game.enemies.add(enemies[i][j]);
				}
			}
		}
		if(root.getRight() != null)
			initializeEnemies(root.getRight());
	}
	
	//places boss at end of stage
	private void initializeBoss(){
		currentWave++;
		boss = new EnemyBoss(307 - 37 , currentWave*SCREENS - SCREENS/2 );
		Game.enemies.add(boss);
	}
	
	public EnemyBoss getBoss(){
		return boss;
	}
}
