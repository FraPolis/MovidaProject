package movida.bartoluccipolisena;
import movida.commons.Movie;

public class MyBnode{
	
	private static int t = 3;  //variable to determine order of tree

	int count; // number of keys in node

	Movie key[];  // array of key values

	MyBnode child[]; //array of references

	boolean leaf; //is node a leaf or not

	MyBnode parent;  //parent of current node.
	
	
	public MyBnode() {
		
	}
	
	public MyBnode(int t, MyBnode parent){
		
		this.parent = parent; //assign parent

		key = new Movie[2*t - 1];  // array of proper size

		child = new MyBnode[2*t]; // array of refs proper size

		leaf = true; // everynode is leaf at first;

		count = 0; //until we add keys later.
	}

	
	public Movie getValue(int index){
		return key[index];
	}


	public MyBnode getChild(int index){
		return child[index];
	}

}
