package movida.bartoluccipolisena;

public class BubbleSort {

	public BubbleSort() {
		// TODO Auto-generated constructor stub
	}
	public static void bubbleSort(Comparable A[]) {
		for (int i = 1; i < A.length; i++) {
			boolean swap = false;
			for (int j = 1; j <= A.length - i; j++)
				if (A[j - 1].compareTo(A[j]) > 0) {
					Comparable temp = A[j - 1];
					A[j - 1] = A[j];
					A[j] = temp;
					swap = true;
				}
			if (!swap) break;
		}
	}
}
