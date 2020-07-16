package movida.bartoluccipolisena;

public class HeapSort {

	public HeapSort() {
		
	}

	private static void deleteMax(Comparable S[], int c) {
		if (c <= 0) return;
		S[1] = S[c];
		c--;
		fixHeap(S, c, 1);
	}
	
	
	private static void heapify(Comparable S[], int n, int i) {
		if (i > n) return;
		heapify(S, n, 2 * i);
		heapify(S, n, 2 * i + 1);
		fixHeap(S, n, i);
	}
	
	public static void heapSort(Comparable S[]) {
		heapify(S, S.length - 1, 1);
		for (int c = (S.length - 1); c > 0; c--) {
			Comparable k = findMax(S);
			deleteMax(S, c);
			S[c] = k;
		}
	}
	
	private static void fixHeap(Comparable S[], int c, int i) {
		int max = 2 * i;
		if (2 * i > c) return;
		if (2 * i + 1 <= c && S[2 * i].compareTo(S[2 * i + 1]) < 0)
			max = 2 * i + 1;
		if (S[i].compareTo(S[max]) < 0) {
			Comparable temp = S[max];
			S[max] = S[i];
			S[i] = temp;
			fixHeap(S, c, max);
		}
	}
	
	private static Comparable findMax(Comparable S[]) {
		return S[1];
	}


}
