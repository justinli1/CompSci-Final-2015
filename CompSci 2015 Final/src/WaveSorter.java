
public class WaveSorter {
	public final int SCREENS =  (-1) * Game.HEIGHT; //Screens upwards

	private Wave head;
	
	public WaveSorter(int waves){
		for(int i = 0; i < waves; i++){
			insert(head, new Wave(i));
		}
	}
	
	public void insert(Wave root, Wave wave){
		if(root == null)
			head = wave;
		else if(wave.getDifficulty() < root.getLeft().getDifficulty())
			insert(root.getLeft(), wave);
		else if(wave.getDifficulty() > root.getRight().getDifficulty())
			insert(root.getRight(), wave);
	}
	
}
