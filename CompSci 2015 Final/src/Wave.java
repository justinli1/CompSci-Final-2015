
public class Wave {
	public final int SCREENS = (-1) * Game.HEIGHT;
	
	private Enemy[][] waveArray = new Enemy[5][3];
	private int waveNumber;
	private int difficulty;
	private Wave left, right;
	
	public Wave(int waveNumber){
		this.waveNumber = waveNumber;
		this.difficulty = 0;
		
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 3; j++){
				randomizeEnemy(i, j);
			}
		}
		
	}
	
	private void randomizeEnemy(int i, int j){
		int random = (int)(Math.random()*10);
		
		if(random >= 0 && random < 3){
			waveArray[i][j] = new Enemy(i * 50 + 51, j * 50 + SCREENS*waveNumber);
			difficulty += 1;
		}
		
		if(random >= 3 && random < 5){
			waveArray[i][j] = new EnemyHeavy(i * 50 + 51, j * 50 + SCREENS*waveNumber);
			difficulty += 2;
		}
		
		if(random >= 5 && random < 6){
			waveArray[i][j] = new EnemyLight(i * 50 + 51, j * 50 + SCREENS*waveNumber);
			difficulty += 3;
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
	
	
}