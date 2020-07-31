package movida.bartoluccipolisena;
import java.util.*;
import movida.commons.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MovidaCore implements IMovidaDB, IMovidaSearch,IMovidaConfig{
	StrutturaDati movies; //creation of data structure
	Movie[] MyallMoviesSorted;

	
 //----IMovidaDB----//
	
 @Override
	public void loadFromFile(File f){		
		try {
			String[] tmp = null;
			Scanner scanner = new Scanner(f);
				while(scanner.hasNextLine()) {
					String data = scanner.nextLine();				
					tmp = data.split(":");
					String title = tmp[1].trim();
					
					data = scanner.nextLine();
					tmp = data.split(":");
					int year = Integer.parseInt(tmp[1].trim());
					
					data = scanner.nextLine();	
					tmp = data.split(":");
					String directorName = tmp[1].trim();
					Person director = new Person(directorName);
					
					data = scanner.nextLine();
					tmp = data.split(":");
					String[] tmp1 = tmp[1].split(",");
	
					HashSet <String> unico = new HashSet<String>();//gestire gli elementi del cast(no duplicati)
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
					tmp = data.split(":");
					int votes = Integer.parseInt(tmp[1].trim());
					
					if(scanner.hasNextLine()== true) {
						scanner.nextLine();
					}
					Movie movie = new Movie(title,year,votes,cast,director);
					Movie ris = ((StrutturaDati) movies).search(movie.getTitle());
					if(ris != null) {
						((StrutturaDati) movies).delete(movie.getTitle());
					}
					((StrutturaDati) movies).insert(movie,movie.getTitle());
					
				}

				scanner.close();
			}catch(FileNotFoundException e){
			      System.out.println("An error occurred.");
			}catch(MovidaFileException m){
				  m.getMessage();
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

	@Override
	public int countMovies() {
		return((StrutturaDati) movies).getMovies().length;
	}

	@Override
	public int countPeople() {
		return this.getAllPeople().length;
	}

	@Override
	public boolean deleteMovieByTitle(String title) {
		Movie[] allMovies = movies.getMovies();
		for(int i = 0; i < allMovies.length; i++) {
			Movie movie = allMovies[i];
			if(movie.getTitle().equalsIgnoreCase(title)) {
				System.out.println("I am deleting the movie : "+allMovies[i]);
				movies.delete(title);
				return true;
			}
		}
		return false;
	}
	
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

	@Override
	public Movie[] getAllMovies() {
		Movie[] moviesReturned = movies.getMovies();
		return moviesReturned;		
	}

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
	
    public boolean setMap(MapImplementation m){
    	if(m==MapImplementation.BTree ){
    		this.movies = new MyBtree();
            return true;
        }else if(m==MapImplementation.ListaNonOrdinata){
            this.movies=new MyLinkedList();
            return true;
        }else {
        	System.out.println("Implementation not found");
        }
        return false;
        
    }
    
    public boolean setSort(SortingAlgorithm a){
    	 MyallMoviesSorted = movies.getMovies();
    	if(a == SortingAlgorithm.BubbleSort ){
    		BubbleSort bubble = new BubbleSort();
    		bubble.bubbleSort(MyallMoviesSorted);
            return true;
        }else if(a == SortingAlgorithm.HeapSort){
            HeapSort heap = new HeapSort();
            heap.sort(MyallMoviesSorted);
            return true;
        }
		return false;
    }
    
    public Movie[] getMyAllMoviesSorted() {
    	return MyallMoviesSorted;
    }
    
 //----IMovidaSearch----//
    
	@Override
	public Movie[] searchMoviesByTitle(String title) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
    	for(int i = 0; i < MyallMoviesSorted.length; i++) {
    		Movie movie = MyallMoviesSorted[i];
    		if(movie.getTitle().contains(title)) {
    			moviesSearched.add(movie);
    		}
    	}
		Movie[] searched = new Movie[moviesSearched.size()];
		for(int i = 0; i < searched.length; i++) {
			searched[i] = moviesSearched.get(i);
		}
		return searched;
	
	}

	@Override
	public Movie[] searchMoviesInYear(Integer year) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
    	for(int i = 0; i < MyallMoviesSorted.length; i++) {
    		Movie movie = MyallMoviesSorted[i];
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

	@Override
	public Movie[] searchMoviesDirectedBy(String name) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
    	for(int i = 0; i < MyallMoviesSorted.length; i++) {
    		Movie movie = MyallMoviesSorted[i];
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

	@Override
	public Movie[] searchMoviesStarredBy(String name) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
		for(int i = 0; i < MyallMoviesSorted.length; i++) {
			Movie movie = MyallMoviesSorted[i];
			for(int j = 0; j < movie.getCast().length; j++) {
				if(movie.getCast()[j].getName().equals(name)) {
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
	
	@Override
	public Movie[] searchMostVotedMovies(Integer N) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
		for(int i = 0; i < MyallMoviesSorted.length; i++) {
			Movie movie = MyallMoviesSorted[i];
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
	
	@Override
	public Movie[] searchMostRecentMovies(Integer N) {
		ArrayList<Movie> moviesSearched = new ArrayList<Movie>();
		for(int i = 0; i < MyallMoviesSorted.length; i++) {
			Movie movie = MyallMoviesSorted[i];
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

	@Override
	public Person[] searchMostActiveActors(Integer N) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String toString() {
		return ((StrutturaDati) movies).toString();
	}
	
//IMovidaCollaborations


	

}
