package movida;
import java.io.File;
import java.util.Arrays;

import movida.bartoluccipolisena.MovidaCore;

import movida.commons.*;
public class MovidaTest {
	
	public static void main(String [] args) {
		MovidaCore movida = new MovidaCore();
		movida.setMap(MapImplementation.BTree);
		//movida.setMap(MapImplementation.BTree);
		
		movida.setSort(SortingAlgorithm.HeapSort);
		movida.setSort(SortingAlgorithm.BubbleSort);
		
	
		movida.loadFromFile(new File("esempio-formato-dati.txt"));
		
		
		
		System.out.println(movida.toString());

	
		
		
		//movida.setSort(SortingAlgorithm.HeapSort);	
		//System.out.println(Arrays.toString(movida.getMyAllMoviesSorted())); //sort by BubbleSort or Heapsort
		//System.out.println(" ");
		//System.out.println(movida.toString());
		
		//---Testing IMovidaDB methods---//
		
		//movida.saveToFile(new File("test-write.txt"));
		//System.out.println(movida.toString());
		//movida.clear();
		//System.out.println(movida.toString());
		//System.out.println("Total number of movies : "+movida.countMovies());
		//System.out.println("Arrays of all people : "+Arrays.toString(movida.getAllPeople()));
		//System.out.println("Total number of peoples : "+movida.countPeople());
		//Movie m1 = movida.getMovieByTitle("Who Framed Roger Rabbit");
		//System.out.println(m1);
		
		//Person p1 = movida.getPersonByName("Harrison Ford");
		//System.out.println(p1.getName());
		//System.out.println("Array of all movies :"+Arrays.toString(movida.getAllMovies()));
		//System.out.println(" ");
		/*System.out.println(movida.deleteMovieByTitle("Raiders of the Lost Ark"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("Indiana Jones and the Temple of Doom"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("Scarface"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("The Sixth Sense"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("Taxi Driver"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("Back to the Future"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("Star Wars Episode V"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("Air Force One"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("Star Wars Episode IV"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("Pulp Fiction"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("Cape Fear"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("The Fugitive"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("Who Framed Roger Rabbit"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("Die Hard"));
		System.out.println(movida.toString());
		System.out.println(movida.deleteMovieByTitle("What Lies Beneath"));
		System.out.println(movida.toString());*/
		
		//---Testing IMovidaSearch methods---//
		
		//System.out.println(Arrays.toString(movida.searchMoviesByTitle("star wars")));
		//System.out.println(Arrays.toString(movida.searchMoviesInYear(1997)));
		//System.out.println(Arrays.toString(movida.searchMoviesDirectedBy("Steven Spielberg")));
		//System.out.println(Arrays.toString(movida.searchMoviesStarredBy("Harrison Ford")));
		//System.out.println(Arrays.toString(movida.searchMostVotedMovies(50)));
		//System.out.println(Arrays.toString(movida.searchMostRecentMovies(4)));
		//System.out.println(Arrays.toString(movida.searchMostActiveActors(6)));
		
		//---Testing IMovidaCollaborations methods---//
		//Person p1 = new Person("Robert De Niro");
		//Person p2 = new Person("Juliette Lewis");
		//Person p3 = new Person("John Travolta");
		//Person[] ris = movida.getDirectCollaboratorsOf(p2);
		//Person[] ris = movida.getTeamOf(p2);
		//Collaboration[] ris = movida.maximizeCollaborationsInTheTeamOf(p1);
		/*if(ris!=null) {
			for(int i = 0; i < ris.length; i++) {
				System.out.println(ris[i]);
			}
		}else {
			System.out.println("Actor not found");
		}*/

	}
}
