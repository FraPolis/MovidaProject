package movida.bartoluccipolisena;
import java.util.*;
import movida.commons.*;
import java.io.File;
import java.io.FileNotFoundException;

import static java.util.stream.Collectors.toMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MovidaCore implements IMovidaDB, IMovidaSearch,IMovidaConfig,IMovidaCollaborations{
	StrutturaDati movies = new MyBtree() ; //creation of  default data structure
	Movie[] myAllMoviesSorted; //array of movies sorted by the two implemented algorithms (BubbleSort / Heapsort)
	MyGraph graph; //creation of a graph
	Sort s = new BubbleSort(); ////creation of  default sorting algorithm
	String loadedFile = "";

 //----IMovidaDB----//
	
 @Override
	public void loadFromFile(File f){		
		try {
			loadedFile = f.getName();
			String[] tmp = null;
			Scanner scanner = new Scanner(f);
			if (f.length() == 0) { 
				System.out.println(" File vuoto"); 
				return;
				}
				while(scanner.hasNextLine()) {
					String data = scanner.nextLine();
					if(!Utils.checkData(data, "Title", 2, ":")) {
						throw new MovidaFileException();
					}
					tmp = data.split(":");
					String title = tmp[1].trim();
					
					data = scanner.nextLine();
					if(!Utils.checkData(data, "Year", 2, ":")) {
						throw new MovidaFileException();
					}
					tmp = data.split(":");
					int year = Integer.parseInt(tmp[1].trim());
					
					data = scanner.nextLine();
					if(!Utils.checkData(data, "Director", 2, ":")) {
						throw new MovidaFileException();
					}
					tmp = data.split(":");
					String directorName = tmp[1].trim();
					Person director = new Person(directorName);
					
					data = scanner.nextLine();
					if(!Utils.checkData(data, "Cast", 2, ":")) {
						throw new MovidaFileException();
					}
					tmp = data.split(":");
					String[] tmp1 = tmp[1].split(",");
	
					HashSet <String> unico = new HashSet<String>(); //handle cast duplicates
					for(int i=0; i<tmp1.length; i++) {
						unico.add(tmp1[i].trim());							
					}
					Person[] cast = new Person[unico.size()];				
					Iterator<String> ele = unico.iterator();
					int i = 0;
				    while (ele.hasNext()) {
				    	 cast[i] = new Person(ele.next());	
				    	 i++;
				     }			        	
					
					data = scanner.nextLine();
					if(!Utils.checkData(data, "Votes", 2, ":")) {
						throw new MovidaFileException();
					}
					tmp = data.split(":");
					int votes = Integer.parseInt(tmp[1].trim());
					if(scanner.hasNextLine()== true) {
						scanner.nextLine();
					}
					Movie movie = new Movie(title,year,votes,cast,director);
					graph.movieCollaborations(movie); //loading graph
					Movie ris = ((StrutturaDati) movies).search(movie.getTitle());
					if(ris != null) {
						((StrutturaDati) movies).delete(movie.getTitle());
					}
					((StrutturaDati) movies).insert(movie,movie.getTitle());
					
				}	
				updateMyAllMoviesSorted();
				scanner.close();
				
			}catch(FileNotFoundException e){
			      System.out.println("An error occurred, file not found");
			}catch(MovidaFileException m){
				  System.out.println(m.getMessage());
			}
		}
	
	@Override
	public void saveToFile(File f) {
		BufferedWriter writer = null;
		String newline = "\n";
		try {
			writer = new BufferedWriter(new FileWriter(f));
			Movie[] moviesReturned = movies.getMovies();
			for(int i=0; i<moviesReturned.length;i++) {
				Movie movie = moviesReturned[i];
				writer.write("Title: "+movie.getTitle()+"\n");
				writer.write("Year: "+movie.getYear()+"\n");
				writer.write("Director: "+movie.getDirector()+"\n");
				writer.write("Cast: ");
				for(int j=0; j<movie.getCast().length; j++) {
					if(j == movie.getCast().length-1) {
						writer.write(movie.getCast()[j].getName());
					}else {
						writer.write(movie.getCast()[j].getName()+", ");
					}
				}
				if(i == moviesReturned.length -1) {
					newline = "";
				}
				writer.write("\n");
				writer.write("Votes: "+movie.getVotes()+newline);
				writer.write(newline);
			}
			writer.close();
		}catch(MovidaFileException m) {
			m.getMessage();
		}catch(IOException  e) {
			e.getMessage();
		}
		
	}
	
	
	@Override
	public void clear() {
		 ((StrutturaDati) movies).clear();
	}
	
	// Ok
	@Override
	public int countMovies() {
		return((StrutturaDati) movies).getMovies().length;
	}

	// Ok
	@Override
	public int countPeople() {
		return this.getAllPeople().length;
	}

	
	// Capitalizza le prime lettere che seguono uno spazio
	// Es.  blade runner -> Blade Runner
	private static String capitalize(String title){
		Scanner in = new Scanner(title);
		String line = in.nextLine();
		String upper_case_line = ""; 
		Scanner lineScan = new Scanner(line); 
		while(lineScan.hasNext()) {
			String word = lineScan.next(); 
			upper_case_line += Character.toUpperCase(word.charAt(0)) + word.substring(1) + " "; 
		}
		in.close();
		lineScan.close();
		
		return (upper_case_line.trim()); 
	}

	// Cancella film da Movies, ma non da AllMySortedMovies
	// SOLUZIONE: funzione che aggiorna AllMySortedMovies
	
	// Errore se si usa il nome di un film scritto in minuscolo, riga 157 Movida Core
	// Riga 49 di MyLinkedList, remove non funziona se si cerca di eliminare qualcosa che non c'è
	// SOLUZIONE: Capitalize first letter of every word
	
	@Override
	public boolean deleteMovieByTitle(String title) {
		Movie[] allMovies = movies.getMovies();
		for(int i = 0; i < allMovies.length; i++) {
			Movie movie = allMovies[i];
			if(movie.getTitle().equalsIgnoreCase(title)) {
				System.out.println("I am deleting the movie : "+allMovies[i]);
				movies.delete(capitalize(title));
				return true;
			}
		}
		return false;
	}
	
	// Ok
	@Override
	public Movie getMovieByTitle(String title) {
		Movie[] moviesReturned = movies.getMovies();
		for(int i = 0; i < moviesReturned.length; i++) {
			Movie movie = moviesReturned[i];
			if(movie.getTitle().equalsIgnoreCase(title)) {
				return movie;
			}
		}
		return null;
	}

	// Ok
	@Override
	public Person getPersonByName(String name) {
		Movie[] moviesReturned = movies.getMovies();
		for(int i = 0; i < moviesReturned.length; i++) {
			Movie movie = moviesReturned[i];
			if(movie.getDirector().getName().equalsIgnoreCase(name)) {
				return movie.getDirector();
			}
			for(int j = 0; j < movie.getCast().length; j++) {
				if(movie.getCast()[j].getName().equalsIgnoreCase(name)){
					return movie.getCast()[j];
				}
			}			
		}
		return null;
	}
	
	// Ok
	@Override
	public Movie[] getAllMovies() {
		Movie[] moviesReturned = movies.getMovies();
		return moviesReturned;		
	}

	// Ok
	@Override
	public Person[] getAllPeople() {
		HashSet<String> people = new HashSet<String>();
		Movie[] moviesReturned = movies.getMovies();
		int k = 0;
		for(int i = 0;i < moviesReturned.length; i++) {
			Movie movie = moviesReturned[i];
			for(int j = 0; j < movie.getCast().length; j++) {
				people.add(movie.getCast()[j].getName());
			}
			people.add(movie.getDirector().getName());
		}
		Person[] allPeople = new Person[people.size()];
		Iterator<String> peopleIter = people.iterator();
		while(peopleIter.hasNext()) {
			allPeople[k] = new Person(peopleIter.next());	
	    	 k++;
		}
		return allPeople;
	}
	

 //----IMovidaConfig----//
	
	// Ok
    public boolean setMap(MapImplementation m){
    	graph = new MyGraph(); 
    	if(m==MapImplementation.BTree){
    		this.movies = new MyBtree();
    		if (loadedFile != "") {
    			this.loadFromFile(new File(loadedFile));
    		}
    		return true;
        }else if(m==MapImplementation.ListaNonOrdinata){
            this.movies=new MyLinkedList();
            if (loadedFile != "") {
    			this.loadFromFile(new File(loadedFile));
    		}
            return true;
        }else {
        	System.out.println("Implementation not found");
        }
        return false;
        
    }
    
    // Ok
    public boolean setSort(SortingAlgorithm a){
    	if(a == SortingAlgorithm.BubbleSort ){
    		s = new BubbleSort();
            return true;
        }else if(a == SortingAlgorithm.HeapSort){
    		s = new HeapSort();
            return true;
        }
		return false;
    }
    
    // Ok
    public Movie[] getMyAllMoviesSorted() {
    	return myAllMoviesSorted;
    }
    
    private void updateMyAllMoviesSorted(){
    	this.myAllMoviesSorted = movies.getMovies();
    	this.s.sort(this.myAllMoviesSorted);
  
    }
    
 //----IMovidaSearch----//
    
    // Trova il film anche se eliminato, myAllMoviesSorted non viene aggiornato quando si eliminano film
    // SOLUZIONE: aggiornata myAllMoviesSorted quando si elimina un elemento
    
    // Trova il film a partire da una sottostringa, quale specifica adottare?
    
	@Override
	public Movie[] searchMoviesByTitle(String title) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
    	for(int i = 0; i < myAllMoviesSorted.length; i++) {
    		Movie movie = myAllMoviesSorted[i];
    		if(movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
    			moviesSearched.add(movie);
    		}
    	}
		Movie[] searched = new Movie[moviesSearched.size()];
		for(int i = 0; i < searched.length; i++) {
			searched[i] = moviesSearched.get(i);
		}
		return searched;
	}

	// Ok
	@Override
	public Movie[] searchMoviesInYear(Integer year) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
    	for(int i = 0; i < myAllMoviesSorted.length; i++) {
    		Movie movie = myAllMoviesSorted[i];
    		if(movie.getYear().equals(year)) {
    			moviesSearched.add(movie);
    		}
    	}
		Movie[] searched = new Movie[moviesSearched.size()];
		for(int i = 0; i < searched.length; i++) {
			searched[i] = moviesSearched.get(i);
		}
		return searched;
	}

	// Ok
	@Override
	public Movie[] searchMoviesDirectedBy(String name) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
    	for(int i = 0; i < myAllMoviesSorted.length; i++) {
    		Movie movie = myAllMoviesSorted[i];
    		if(movie.getDirector().getName().equalsIgnoreCase(name)) {
    			moviesSearched.add(movie);
    		}
    	}
		Movie[] searched = new Movie[moviesSearched.size()];
		for(int i = 0; i < searched.length; i++) {
			searched[i] = moviesSearched.get(i);
		}
		return searched;
	}

	// Ricerca case sensi
	@Override
	public Movie[] searchMoviesStarredBy(String name) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
		for(int i = 0; i < myAllMoviesSorted.length; i++) {
			Movie movie = myAllMoviesSorted[i];
			for(int j = 0; j < movie.getCast().length; j++) {
				if(movie.getCast()[j].getName().equalsIgnoreCase(name)) {
					moviesSearched.add(movie);
				}
			}
		}
		Movie[] searched = new Movie[moviesSearched.size()];
		for(int i = 0; i < searched.length; i++) {
			searched[i] = moviesSearched.get(i);
		}
		return searched;
	}
	
	// Ok
	@Override
	public Movie[] searchMostVotedMovies(Integer N) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
		for(int i = 0; i < myAllMoviesSorted.length; i++) {
			Movie movie = myAllMoviesSorted[i];
			moviesSearched.add(movie);
		}
		Collections.sort(moviesSearched,(movie1,movie2)->movie2.getVotes() - movie1.getVotes());
		if(N <= moviesSearched.size()) {
			Movie[] searched = new Movie[N];
			for(int i = 0; i<N; i++) {
				searched[i] = moviesSearched.get(i); 
			}
			return searched;
		}else {
			Movie[] searched = new Movie[moviesSearched.size()];
			for(int i = 0; i<searched.length; i++) {
				searched[i] = moviesSearched.get(i);
			}
			return searched;
		}
	}
	
	// Ok
	@Override
	public Movie[] searchMostRecentMovies(Integer N) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
		for(int i = 0; i < myAllMoviesSorted.length; i++) {
			Movie movie = myAllMoviesSorted[i];
			moviesSearched.add(movie);
		}
		Collections.sort(moviesSearched,(movie1,movie2)->movie2.getYear() - movie1.getYear());
		if(N <= moviesSearched.size()) {
			Movie[] searched = new Movie[N];
			for(int i = 0; i<N; i++) {
				searched[i] = moviesSearched.get(i);
			}
			return searched;
		}else {
			Movie[] searched = new Movie[moviesSearched.size()];
			for(int i = 0; i<searched.length; i++) {
				searched[i] = moviesSearched.get(i); 
			}
			return searched;
		}
	}

	// Ok
	@Override
	public Person[] searchMostActiveActors(Integer N) {
		Person[] allActors = getAllActors();
		Map <String,Integer> actors = new HashMap<String,Integer>();
		for(int i = 0; i < allActors.length; i++) {
			String actorName = allActors[i].getName();
			Integer count = 1;
			if(actors.containsKey(actorName)) {
				count = actors.get(actorName);
				count++;
			}
			actors.put(actorName, count);
		}
		 Map<String, Integer>  sorted = actors //sorting hashMap by value(decreasing order)
		            .entrySet()
		            .stream()
		            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		            .collect(
		                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
		                    LinkedHashMap::new));
		 
			Person[] mostActiveActors = null;
			if(N <= sorted.size()) {
				mostActiveActors = new Person[N];
			}else {
				mostActiveActors = new Person[sorted.size()];
			}
			
			int i = 0;
			Iterator<String> tmpIter = sorted.keySet().iterator();	
			while(i < N && tmpIter.hasNext()) {
				String name = tmpIter.next();
				Person p = new Person(name);
				mostActiveActors[i] = p;
				i++;	
			}
			return mostActiveActors;		
	}
	
	// Ok
	public Person[] getAllActors() {  //return array of all actors (with duplicates)
		ArrayList<Person> actors = new ArrayList<Person>();
		Movie[] moviesReturned = movies.getMovies();
		for(int i = 0;i < moviesReturned.length; i++) {
			Movie movie = moviesReturned[i];
			for(int j = 0; j < movie.getCast().length; j++) {
				actors.add(movie.getCast()[j]);
			}
		}
		
		Person[] allActors = new Person[actors.size()];
		for(int i = 0; i < allActors.length; i++) {
			allActors[i] = actors.get(i);
		}
		
		return allActors;
	}

	// Ok
	@Override
	public String toString() {
		return ((StrutturaDati) movies).toString();
	}
	
	 //----IMovidaCollaboration---//
	
	@Override
	public Person[] getDirectCollaboratorsOf(Person actor) {
		return graph.getDirectCollaborators(actor);
	}

    
	@Override
	public Person[] getTeamOf(Person actor) {
		 return graph.getTeam(actor);
		
	}

	@Override
	public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor) {
		return graph.maximizeCollaborationsInTheTeam(actor);
	}

}
