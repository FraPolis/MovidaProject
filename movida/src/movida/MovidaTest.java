package movida;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import movida.bartoluccipolisena.MovidaCore;

import movida.commons.*;
public class MovidaTest {
	
	public static void main(String [] args) {
		MovidaCore movida = new MovidaCore();
		//movida.setMap(MapImplementation.ListaNonOrdinata);
		movida.setMap(MapImplementation.BTree);
		//movida.setSort(SortingAlgorithm.BubbleSort);
		movida.loadFromFile(new File("esempio-formato-dati.txt"));
		System.out.println(movida.toString());
		//movida.saveToFile(new File("esempio-formato-dati-write.txt"));
		//System.out.println(movida.toString());
		//movida.clear();
		//System.out.println(movida.toString());
		//System.out.println("Total number of movies : "+movida.countMovies());
		//System.out.println("Arrays of all people : "+Arrays.toString(movida.getAllPeople()));
		//System.out.println("Total number of peoples : "+movida.countPeople());
		//Movie m1 = movida.getMovieByTitle("Pulp Fiction");
		//System.out.println(m1);
		
		//Person p1 = movida.getPersonByName("Robert De Niro");
		//System.out.println(p1.getName());
		//System.out.println("Array of all movies :"+Arrays.toString(movida.getAllMovies()));
		//System.out.println(" ");
		//System.out.println(movida.deleteMovieByTitle("Taxi Driver"));
		//System.out.println(movida.toString());
		Movie[] all = movida.getAllMovies();
		System.out.println(" ");
		System.out.println(Arrays.toString(all));
	}
}
