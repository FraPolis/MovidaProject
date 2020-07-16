package movida.bartoluccipolisena;


import movida.commons.Movie;

public class MyBtreeOld extends StrutturaDati  {
	
	static int order; // order of tree
	int size = 0; 
	MyBnode root;
	
	
	public MyBtreeOld() {
		this.order = 3;
		root = new MyBnode(order,null);
	}
	
	public MyBnode search(MyBnode root, Movie key){
		int i = 0;
		while(i < root.count && !key.getTitle().equalsIgnoreCase(root.key[i].getTitle())){
			i++;
		}

		if(i < root.count && root.key[i] != null && key.getTitle().equalsIgnoreCase(root.key[i].getTitle())){
			return root;
		}
		
		if(root.leaf){
			return null;
		}else{
			return search(root.getChild(i),key);

		}
	}
	
	public void split(MyBnode x, int i, MyBnode y){
		MyBnode z = new MyBnode(order,null);
		z.leaf = y.leaf;//set boolean to same as y

		z.count = order - 1;//this is updated size

		for(int j = 0; j < order - 1; j++){
			z.key[j] = y.key[j+order]; //copy end of y into front of z

		}
		if(!y.leaf)	{//if not leaf we have to reassign child nodes.
			for(int k = 0; k < order; k++){
				z.child[k] = y.child[k+order]; //reassing child of y
			}
		}

		y.count = order - 1; //new size of y

		for(int j = x.count ; j> i ; j--){	//if we push key into x we have to rearrange child nodes

			x.child[j+1] = x.child[j]; //shift children of x

		}
		x.child[i+1] = z; //reassign i+1 child of x

		for(int j = x.count; j> i; j--)	{
			x.key[j + 1] = x.key[j]; // shift keys
		}
		x.key[i] = y.key[order-1];//finally push value up into root.

		y.key[order-1 ] = null; //erase value where we pushed from

		for(int j = 0; j < order - 1; j++){
			y.key[j + order] = null; //'delete' old values
		}

		x.count ++;  //increase count of keys in x
	}


	public void nonfullInsert(MyBnode x, Movie key)	{
			int i = x.count; //i is number of keys in node x

			if(x.leaf){
				while(i >= 1 && key.compareTo(x.key[i-1]) < 1){//here find spot to put key
					x.key[i] = x.key[i-1];//shift values to make room
					i--;//decrement
				}

				x.key[i] = key;//finally assign value to node
				x.count ++; //increment count of keys in this node now.

			}else{
				int j = 0;
				while(j < x.count  && key.compareTo(x.key[i-1]) > 1){//find spot to recurse on correct child  		
					j++;
				}

				if(x.child[j].count == order*2 - 1){
					split(x,j,x.child[j]);//call split on node x's ith child

					if(key.compareTo(x.key[j]) > 1){
						j++;
					}
				}

				nonfullInsert(x.child[j],key);//recurse
			}
		}

	public void insert(MyBtreeOld t, Movie key){
				size++;//size of BTree
				MyBnode r = t.root;//this method finds the node to be inserted as it goes through this starting at root node.
				if(r.count == 2*order - 1){//if is full
					MyBnode s = new MyBnode(order,null);//new node

					t.root = s;    //\
			    			       // \	
					s.leaf = false;//  \
			    			       //   > this is to initialize node.
					s.count = 0;   //  /
			    			       // /	
					s.child[0] = r;///

					split(s,0,r);//split root

					nonfullInsert(s, key); //call insert method
				}else
					nonfullInsert(r,key);//if its not full just insert it
		}
	

	public void print(MyBnode n){
				for(int i = 0; i < n.count; i++)
				{
					System.out.print(n.getValue(i)+" " );//this part prints root node
				}

				if(!n.leaf){//this is called when root is not leaf;

					for(int j = 0; j <= n.count  ; j++) {//in this loop we recurse to print out tree in preorder 
						if(n.getChild(j) != null) {			
						System.out.println();	  
						print(n.getChild(j));    
						}
					}
				}
			}



	public void SearchPrintNode( MyBtreeOld T,Movie x){
				MyBnode temp= new MyBnode(order,null);
				temp= search(T.root,x);
				if (temp==null){
				System.out.println("The Key does not exist in this tree");
				}else{
				print(temp);
				}
			}		


	 public void deleteKey(MyBtreeOld t, Movie key){
						       
				MyBnode temp = new MyBnode(order,null);//temp Bnode
						
				temp = search(t.root,key);//call of search method on tree for key

				if(temp!= null &&  temp.leaf && temp.count >= order - 1){ //temp.leaf rimuove solo per foglia
					int i = 0;

					while(key.compareTo(temp.getValue(i)) > 1){
						i++;
					}
						for(int j = i; j < 2*order - 2; j++){
							temp.key[j] = temp.getValue(j+1);
						}
						temp.count --;
					
					}
					/*else{
						System.out.println("This node is either not a leaf or has less than order - 1 keys.");
					}*/
			    }
	  
	 
			 
	@Override
	public String toString() {
		if(root == null) {
			return "Albero vuoto";
		}
		print(root);
		return "";
	}
	@Override
	public void insert(Movie movie, Comparable k) {
		insert(this, movie);
	}

	@Override
	public void delete(Comparable k) {
		Movie movieToDelete = new Movie(k.toString());
		deleteKey(this, movieToDelete);
	}

	@Override
	public Movie search(Comparable k) {
		Movie movieToSearch = new Movie(k.toString());
		 MyBnode node = search(root,movieToSearch);
		 if(node != null){
			for(int i = 0; i < node.count; i++) {
				if(node.key[i].getTitle().equals(k)) {
					return node.key[i];
				}
			}
		 }
		 return null;
	}
	
	public int getBtreeSize() {
		return size;
	}
	
	
	@Override
	public Movie[] getMovies() {
		Movie[] listOfMovies = new Movie[getBtreeSize()];
		getMovies(root,listOfMovies,0);
		return listOfMovies;
	}
	
	
	public void getMovies(MyBnode n, Movie[] listOfMovies,int k) {
		
		for(int i = 0; i < n.count; i++){
			listOfMovies[k] = n.getValue(i);
			k++;
			
		}

		if(!n.leaf)	{
		
			for(int j = 0; j <= n.count  ; j++){		
				
				if(n.getChild(j) != null) {			  
					k = getNextPosition(listOfMovies);
					getMovies(n.getChild(j),listOfMovies,k);     
				}
			}
		}
		
	}
	
	public int getNextPosition(Movie[] listOfMovies) {
		int i;
		for(i = 0; i < listOfMovies.length; i++) {
			if(listOfMovies[i] == null) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void clear() {
		Movie[] listOfMovies = getMovies();
		for(int i = 0; i<listOfMovies.length; i++) {
			delete(listOfMovies[i].getTitle());
		}
		root = null;
		
	}

	


}
