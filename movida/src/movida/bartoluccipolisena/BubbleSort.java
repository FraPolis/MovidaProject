package movida.bartoluccipolisena;

import movida.commons.Movie;

public class BubbleSort   {

	public BubbleSort() {
		// TODO Auto-generated constructor stub
	}
	
	public  void bubbleSort(Movie A[]) {
		for (int i = 1; i < A.length; i++) {
			boolean scambiAvvenuti = false;
			for (int j = 1; j <= A.length - i; j++)
				if (A[j - 1].compareTo(A[j]) > 0) {
					Movie temp = A[j - 1];
					A[j - 1] = A[j];
					A[j] = temp;
					scambiAvvenuti = true;
				}
			if (!scambiAvvenuti) break;
		}
	}
	
}
