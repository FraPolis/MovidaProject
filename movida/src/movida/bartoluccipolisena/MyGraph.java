package movida.bartoluccipolisena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import movida.commons.Collaboration;
import movida.commons.Movie;
import movida.commons.Person;
import java.util.*;


	public class MyGraph {
		
		// Data structure for items
	    private HashMap<String, ArrayList<Collaboration>> graph;

	    // Constructor
	    MyGraph(){
	       this.graph = new HashMap<String,ArrayList<Collaboration>>();
	    }

	    
	    public Person[] getDirectCollaborators(Person actor){
	        ArrayList<Collaboration> collaborations = graph.get(actor.getName());
	       if(collaborations!=null) {
		        Person[] collaborators = new Person[collaborations.size()];
		        int i=0;
		        for(Collaboration c : collaborations){
		            collaborators[i] = c.getActorB();
		            i++;
		        }
		        System.out.println("Direct collaborators of "+actor.getName()+" are :");
		        return collaborators;
	       } 
	       return null;
	    }
	    
	    public Person[] getTeam(Person actor){ // visita in ampiezza
	        HashSet<String> found = new HashSet<>();
	        ArrayList<Person> team = new ArrayList<>();
	        ArrayDeque<Person> queue = new ArrayDeque<>();
	        found.add(actor.getName());
	        team.add(actor);
	        queue.add(actor);
	        while (!queue.isEmpty()){ 
	            Person u = queue.poll();
	            ArrayList<Collaboration> lista = graph.get(u.getName());
	            if(lista != null) {
		            for(Collaboration c: lista){
		                Person v = c.getActorB();
		                if(found.contains(v.getName())==false){ 
		                	found.add(v.getName());
		                    team.add(v);
		                    queue.add(v);
		                }
		            }
	            }else return null;

	        }
	        
	        Person[] actorTeam = new Person[team.size()];
	        for(int i = 0; i < actorTeam.length; i++) {
	        	actorTeam[i] = team.get(i);
			}
	        System.out.println("Team of "+actor.getName()+" consist of :");
	        return actorTeam;
	    }

	    
	    class SortCollaborations implements Comparator<Collaboration> { //sort collaborations in decreasing order
	        public int compare(Collaboration a, Collaboration b) {
	            return b.getScore().compareTo(a.getScore());
	        }
	    }
	    
	    public Collaboration[] maximizeCollaborationsInTheTeam(Person actor){  // Maximum Spanning Tree
	        HashSet<Person> actorsFound = new HashSet<>();
	        ArrayList<Collaboration> collabs = new ArrayList<>();
	        PriorityQueue<Collaboration> q = new PriorityQueue<Collaboration>(new SortCollaborations());
	        for(Collaboration c: this.graph.get(actor.getName())){
	            q.add(c);                               
	        }
	        while (!q.isEmpty()){
	            Collaboration e = q.poll();//get collaboration with maximum score
	            
	            if(!actorsFound.contains(e.getActorB())){
	            	actorsFound.add(e.getActorA());
	            	actorsFound.add(e.getActorB());
	                collabs.add(e);
	                for(Collaboration c:this.graph.get(e.getActorB().getName())){
	                    if(!actorsFound.contains(c.getActorB())){
	                        q.add(c);
	                    }
	                }
	            }
	        }
	        Collaboration[] collaborationsInTeam = new Collaboration[collabs.size()];
	        for(int i = 0; i < collaborationsInTeam.length; i++) {
	        	collaborationsInTeam[i] = collabs.get(i);
			}
	        System.out.println("Maximum Collaboration in the team of "+actor.getName()+" consist of :");
	        System.out.println(" ");
	        return collaborationsInTeam;

	    }

	    public void movieCollaborations(Movie m){ //get Movie collaborations
	        for(Person currentActor: m.getCast()){
	            if (!graph.containsKey(currentActor.getName())){
	                graph.put(currentActor.getName(),new ArrayList<>());
	            }
	            for (Person actor: m.getCast()){      
	                if(!currentActor.equals(actor)){  
	                    ArrayList<Collaboration> collaborations= graph.get(currentActor.getName());
	                    Collaboration collab = new Collaboration(currentActor,actor);
	                    if(collaborations.contains(collab)){
	                        int index = collaborations.indexOf(collab);
	                        collaborations.get(index).addMovie(m);
	                    }else{
	                        collab.addMovie(m);
	                        collaborations.add(collab);
	                    }
	                }
	            }
	        }
	    }
	}
