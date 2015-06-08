 
public class Wave {
	public static final int LENGTH = 5;
	public static final int WIDTH = 3;
	
	private Enemy[][] waveArray = new Enemy[LENGTH][WIDTH];
	private int waveNumber;
	private int difficulty;
	private Wave left, right;
	
	
	//waves are nodes that hold difficulty
	public Wave(){
		this.difficulty = 0;
		
		for(int i = 0; i < LENGTH; i++){
			for(int j = 0; j < WIDTH; j++){
				randomizeEnemy(i, j);
			}
		}
		
	}
	
	//randomly generates enemies and assigns a difficulty to them
	private void randomizeEnemy(int i, int j){
		int random = (int)(Math.random()*10);
		
		if(random >= 0 && random < 3){
			waveArray[i][j] = new Enemy((i * 120) + 51, j * 50);
			difficulty += 2;
		}
		
		else if(random >= 3 && random < 4){
			waveArray[i][j] = new EnemyHeavy((i * 120) + 51, j * 50);
			difficulty += 3;
		}
		
		else if(random >= 4 && random < 5){
			waveArray[i][j] = new EnemyLight((i * 120) + 51, j * 50);
			difficulty += 1;
		}
		
		else
			waveArray[i][j] = null;
	}

	public Enemy[][] getWaveArray() {
		return waveArray;
	}

	public void setWaveArray(Enemy[][] waveArray) {
		this.waveArray = waveArray;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public Wave getLeft() {
		return left;
	}

	public void setLeft(Wave left) {
		this.left = left;
	}

	public Wave getRight() {
		return right;
	}

	public void setRight(Wave right) {
		this.right = right;
	}
	
	public int getWaveNumber(){
		return waveNumber;
	}
	
	public void setWaveNumber(int waveNumber){
		this.waveNumber = waveNumber;
	}
}