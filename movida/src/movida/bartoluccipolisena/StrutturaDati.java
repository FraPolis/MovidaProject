package movida.bartoluccipolisena;

import java.util.Iterator;

import movida.commons.Movie;

public abstract class StrutturaDati  {

	 public abstract void insert(Movie movie,Comparable k);
	 public abstract void delete(Comparable k);
	 public abstract Movie search(Comparable k);
	 public abstract Movie[] getMovies();
	 public abstract void clear();
	 public abstract String toString();



}
