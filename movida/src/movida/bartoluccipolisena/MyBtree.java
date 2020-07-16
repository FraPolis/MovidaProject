package movida.bartoluccipolisena;

import java.util.Stack;

import movida.commons.Movie;

public class MyBtree extends StrutturaDati {

  private int T;

  public class Node {
    int n;
    Movie key[] = new Movie[2 * T - 1];
    Node child[] = new Node[2 * T];
    boolean leaf = true;

    public Movie Find(Movie k) {
      for (int i = 0; i < this.n; i++) {
        if (this.key[i].equals(k)) {
          return key[i];
        }
      }
      return null;
    };
  }

  public MyBtree() {
    T = 3;
    root = new Node();
    root.n = 0;
    root.leaf = true;
  }

  private Node root;

  // Search the key
  private Node Search(Node x, Movie key) {
    int i = 0;
    if (x == null)
      return x;
    for (i = 0; i < x.n; i++) {
    	if (key.equals(x.key[i])) {
            return x;
          }
      if (key.compareTo(x.key[i]) < 1) {
        break;
      }
      
    }
    if (x.leaf) {
      return null;
    } else {
      return Search(x.child[i], key);
    }
  }

  // Split function
  private void Split(Node x, int pos, Node y) {
    Node z = new Node();
    z.leaf = y.leaf;
    z.n = T - 1;
    for (int j = 0; j < T - 1; j++) {
      z.key[j] = y.key[j + T];
    }
    if (!y.leaf) {
      for (int j = 0; j < T; j++) {
        z.child[j] = y.child[j + T];
      }
    }
    y.n = T - 1;
    for (int j = x.n; j >= pos + 1; j--) {
      x.child[j + 1] = x.child[j];
    }
    x.child[pos + 1] = z;

    for (int j = x.n - 1; j >= pos; j--) {
      x.key[j + 1] = x.key[j];
    }
    x.key[pos] = y.key[T - 1];
    x.n = x.n + 1;
  }

  // Insert the key
  public void Insert(final Movie key) {
    Node r = root;
    if (r.n == 2 * T - 1) {
      Node s = new Node();
      root = s;
      s.leaf = false;
      s.n = 0;
      s.child[0] = r;
      Split(s, 0, r);
      _Insert(s, key);
    } else {
      _Insert(r, key);
    }
  }

  // Insert the node
  final private void _Insert(Node x, Movie k) {

    if (x.leaf) {
      int i = 0;
      for (i = x.n - 1; i >= 0 && k.compareTo(x.key[i]) < 1; i--) {
        x.key[i + 1] = x.key[i];
      }
      x.key[i + 1] = k;
      x.n = x.n + 1;
    } else {
      int i = 0;
      for (i = x.n - 1; i >= 0 && k.compareTo(x.key[i]) < 1; i--) {
      }
      ;
      i++;
      Node tmp = x.child[i];
      if (tmp.n == 2 * T - 1) {
        Split(x, i, tmp);
        if (k.compareTo(x.key[i]) < 1) {
          i++;
        }
      }
      _Insert(x.child[i], k);
    }

  }

  public void Show() {
    Show(root);
  }

  private int getPosition(Node[] nodes,Movie key) {
	  for(int i = 0; i < nodes.length; i++) {
		  if(nodes[i].equals(key)) {
			  return i;
		  }
	  }
	  return -1;
  }
  
  private void Remove(Node x, Movie key) {
    Movie pos = x.Find(key);
    int poss = 0;
    if (pos != null) {
      if (x.leaf) {
        int i = 0;
        for (i = 0; i < x.n && !x.key[i].equals(key); i++) {
        }
        ;
        for (; i < x.n; i++) {
          if (i != 2 * T - 2) {
            x.key[i] = x.key[i + 1];
          }
        }
        x.n--;
        return;
      }
      if (!x.leaf) {
    
        Node pred = x.child[getPosition(x.child,pos)];
        Movie predKey = null;
        if (pred.n >= T) {
          for (;;) {
            if (pred.leaf) {
              System.out.println(pred.n);
              predKey = pred.key[pred.n - 1];
              break;
            } else {
              pred = pred.child[pred.n];
            }
          }
          Remove(pred, predKey);
          x.key[getPosition(x.child,pos)] = predKey;
          return;
        }

        Node nextNode = x.child[getPosition(x.child,pos) + 1];
        if (nextNode.n >= T) {
          Movie nextKey = nextNode.key[0];
          if (!nextNode.leaf) {
            nextNode = nextNode.child[0];
            for (;;) {
              if (nextNode.leaf) {
                nextKey = nextNode.key[nextNode.n - 1];
                break;
              } else {
                nextNode = nextNode.child[nextNode.n];
              }
            }
          }
          Remove(nextNode, nextKey);
          x.key[getPosition(x.child,pos)] = nextKey;
          return;
        }

        int temp = pred.n + 1;
        pred.key[pred.n++] = x.key[getPosition(x.child,pos)];
        for (int i = 0, j = pred.n; i < nextNode.n; i++) {
          pred.key[j++] = nextNode.key[i];
          pred.n++;
        }
        for (int i = 0; i < nextNode.n + 1; i++) {
          pred.child[temp++] = nextNode.child[i];
        }

        x.child[getPosition(x.child,pos)] = pred;
        for (int i = getPosition(x.child,pos); i < x.n; i++) {
          if (i != 2 * T - 2) {
            x.key[i] = x.key[i + 1];
          }
        }
        for (int i = getPosition(x.child,pos) + 1; i < x.n + 1; i++) {
          if (i != 2 * T - 1) {
            x.child[i] = x.child[i + 1];
          }
        }
        x.n--;
        if (x.n == 0) {
          if (x == root) {
            root = x.child[0];
          }
          x = x.child[0];
        }
        Remove(pred, key);
        return;
      }
    } else {
      for (poss = 0; poss < x.n; poss++) {
        if (x.key[getPosition(x.child,pos)].compareTo(key) > 1) {
          break;
        }
      }
      Node tmp = x.child[poss];
      if (tmp.n >= T) {
        Remove(tmp, key);
        return;
      }
      if (true) {
        Node nb = null;
        Movie devider = null;

        if (poss != x.n && x.child[getPosition(x.child,pos) + 1].n >= T) {
          devider = x.key[getPosition(x.child,pos)];
          nb = x.child[getPosition(x.child,pos) + 1];
          x.key[getPosition(x.child,pos)] = nb.key[0];
          tmp.key[tmp.n++] = devider;
          tmp.child[tmp.n] = nb.child[0];
          for (int i = 1; i < nb.n; i++) {
            nb.key[i - 1] = nb.key[i];
          }
          for (int i = 1; i <= nb.n; i++) {
            nb.child[i - 1] = nb.child[i];
          }
          nb.n--;
          Remove(tmp, key);
          return;
        } else if (poss != 0 && x.child[getPosition(x.child,pos) - 1].n >= T) {

          devider = x.key[getPosition(x.child,pos) - 1];
          nb = x.child[getPosition(x.child,pos) - 1];
          x.key[getPosition(x.child,pos) - 1] = nb.key[nb.n - 1];
          Node child = nb.child[nb.n];
          nb.n--;

          for (int i = tmp.n; i > 0; i--) {
            tmp.key[i] = tmp.key[i - 1];
          }
          tmp.key[0] = devider;
          for (int i = tmp.n + 1; i > 0; i--) {
            tmp.child[i] = tmp.child[i - 1];
          }
          tmp.child[0] = child;
          tmp.n++;
          Remove(tmp, key);
          return;
        } else {
          Node lt = null;
          Node rt = null;
          boolean last = false;
          if (getPosition(x.child,pos) != x.n) {
            devider = x.key[getPosition(x.child,pos)];
            lt = x.child[getPosition(x.child,pos)];
            rt = x.child[getPosition(x.child,pos) + 1];
          } else {
            devider = x.key[getPosition(x.child,pos) - 1];
            rt = x.child[getPosition(x.child,pos)];
            lt = x.child[getPosition(x.child,pos) - 1];
            last = true;
            poss--;
          }
          for (int i = getPosition(x.child,pos); i < x.n - 1; i++) {
            x.key[i] = x.key[i + 1];
          }
          for (int i = getPosition(x.child,pos) + 1; i < x.n; i++) {
            x.child[i] = x.child[i + 1];
          }
          x.n--;
          lt.key[lt.n++] = devider;

          for (int i = 0, j = lt.n; i < rt.n + 1; i++, j++) {
            if (i < rt.n) {
              lt.key[j] = rt.key[i];
            }
            lt.child[j] = rt.child[i];
          }
          lt.n += rt.n;
          if (x.n == 0) {
            if (x == root) {
              root = x.child[0];
            }
            x = x.child[0];
          }
          Remove(lt, key);
          return;
        }
      }
    }
  }

  public void Remove(Movie key) {
    Node x = Search(root, key);
    if (x == null) {
      return;
    }
    Remove(root, key);
  }

  public void Task(Movie a, Movie b) {
    Stack<Movie> st = new Stack<>();
    FindKeys(a, b, root, st);
    while (st.isEmpty() == false) {
      this.Remove(root, st.pop());
    }
  }

  private void FindKeys(Movie a, Movie b, Node x, Stack<Movie> st) {
    int i = 0;
    for (i = 0; i < x.n && x.key[i].compareTo(b) < 1; i++) {
      if (x.key[i].compareTo(a) > 1) {
        st.push(x.key[i]);
      }
    }
    if (!x.leaf) {
      for (int j = 0; j < i + 1; j++) {
        FindKeys(a, b, x.child[j], st);
      }
    }
  }

  public boolean Contain(Movie k) {
    if (this.Search(root, k) != null) {
      return true;
    } else {
      return false;
    }
  }

  // Show the node
  private void Show(Node x) {
    assert (x == null);
    for (int i = 0; i < x.n; i++) {
      System.out.print(x.key[i] + " ");
    }
    if (!x.leaf) {
      for (int i = 0; i < x.n + 1; i++) {
        Show(x.child[i]);
      }
    }
  }

  @Override
public String toString() {
		if(root == null) {
			return "Albero vuoto";
		}
		Show(root);
		return "";
	}
  
@Override
public void insert(Movie movie, Comparable k) {
	Insert(movie);
	
}

@Override
public void delete(Comparable k) {
	Movie movieToDelete = new Movie(k.toString());
	Remove(movieToDelete);
}

@Override
public Movie search(Comparable k) { //private Node Search(Node x, Movie key)
	Movie movieToSearch = new Movie(k.toString());
	 Node node = Search(root,movieToSearch);
	 if(node != null){
			for(int i = 0; i < node.n; i++) {
				if(node.key[i].getTitle().equals(k)) {
					return node.key[i];
				}
			}
		 }
	return null;
}

@Override
public Movie[] getMovies() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void clear() {
	// TODO Auto-generated method stub
	
}


}
