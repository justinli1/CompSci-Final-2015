import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Scores {
	
	public Node head;
	public int size = 0;
	public int rank = 0;
	
	public String names[];
	public int scores[];
	
	//uses tree sort to insert and display scores in order
	public Scores(String fileName){
		Scanner sc = null;
		try {
			sc = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(sc.hasNext()){
			String name = sc.next();
			int score = sc.nextInt();
			insert(head, name, score);
			size++;
		}
		
		names = new String[size];
		scores = new int[size];
		
	}
	
	public void insert(Node root ,String name, int score){
		Node newScore = new Node(name, score);
		
		if(head == null)
			head = newScore;
		
		if(root == null){
			root = newScore;
		}
		else if(root.left == null && newScore.score <= root.score)
			root.left = newScore;
		else if(root.right == null && newScore.score > root.score)
			root.right = newScore;
		
		
		else if(newScore.score <= root.score)
			insert(root.left, name, score);
		else if(newScore.score > root.score)
			insert(root.right, name, score);
	}
	
	public void setRanks(Node root){
		if(root.right != null)
			setRanks(root.right);
		
		names[rank] = root.name;
		scores[rank] = root.score;
		rank++;
		
		if(root.left != null)
			setRanks(root.left);
	}
	
}

class Node{
	public int score;
	public String name;

	public Node left,right;
	
	public Node(String name, int score){
		this.score = score;
		this.name = name;
	}
}