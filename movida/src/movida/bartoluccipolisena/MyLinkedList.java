package movida.bartoluccipolisena;

import movida.commons.Movie;

public class MyLinkedList extends StrutturaDati   {

private Record list = null;
int position = 0;

	public final class Record{
		
		public Movie elemento;	  
		public Comparable chiave;
		public Record next;
		public Record prev;
		
		public Record(Movie movie,Comparable k) {
			elemento = movie;
			chiave = k;
			next = prev = null;
		}
	
	}
	
	 public void insert(Movie movie,Comparable k) {
	        Record p = new Record(movie, k);
	        if (list == null)
	            list = p.prev = p.next = p;
	        else {
	            p.next = list.next;
	            list.next.prev = p;
	            list.next = p;
	            p.prev = list;
	        }
	    }
	 
	 public void delete(Comparable k) {
	        Record p = null;
	        if (list != null)
	            for (p = list.next; ; p = p.next){
	                if (p.chiave.equals(k)) {
	                	break;
	                }
	               if (p == list) {
	            	   p = null;
	            	   break;
	               }
	            }	
	        if (p.next == p) {
	        	list = null; 
	        }else {
	            if (list == p) list = p.next;
	            p.next.prev = p.prev;
	            p.prev.next = p.next;
	        }
	    }
	 
	 public Movie search(Comparable k) {
	        if (list == null) return null;
	        for (Record p = list.next; ; p = p.next){
	            if (p.chiave.equals(k)) return p.elemento;
	            if (p == list) return null;
	        }
	    } 

	 public int getListSize() {
		 int size = 0;
		 Record head = list;
		 Record tmp = head.next;
		 size++;
		 while(tmp!=head) {
			 size++;
			 tmp = tmp.next;
		 }
		 return size;
	 }
	 
	 @Override
	 public String toString() {
		 Record head = list;
		 if(head!= null) {
			 String s = "["+head.elemento+ "] ";
			 Record tmp = head.next;
			 while(tmp!= head) {
				 s = s +"[" +tmp.elemento+ "] ";
				 tmp = tmp.next;
			 }
			 return s; 
		 }
		 return "Lista vuota";
	 }

	@Override
	public Movie[] getMovies() {
		Movie[] listOfMovies = new Movie[getListSize()];
		int i = 0;
		  if (list == null) {
			  return null;
		  }
	        for(Record p = list.next; ; p = p.next){
	        	listOfMovies[i] = p.elemento;
	        	i++;	            
	            if (p == list) {
	            	return listOfMovies;
	            }
	        }
	}
	
	public void clear() {
		Movie[] listOfMovies = getMovies();
		for(int i = 0; i<listOfMovies.length; i++) {
			delete(listOfMovies[i].getTitle());
		}
	}

}