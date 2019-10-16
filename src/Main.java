import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

	private static SortThread[] sortingThreads;
	static int[] numberArray;

	private static int numOfThreads;
	private static int numOfPairs = 3;
	private static int arraySize = 12;

	public static void main(String[] args) {
		if(arraySize % 2 != 0){
			arraySize++;
		}

		numOfThreads = arraySize / (2 * numOfPairs);

		System.out.printf("Size of Number Set: %d%n", arraySize);
		System.out.printf("Number of Threads: %d%n", numOfThreads);
		System.out.printf("Pairs per Thread: %d%n", numOfPairs);

		//Generate array to sort
		populateNumArray();

		//Print the array before sorting
		System.out.print("\nBefore Sort: ");
		printArray(numberArray);
		//PInitialize the thread array
		sortingThreads = new SortThread[numOfThreads];

		//Initialize the CyclicBarriers used for synchronization
		CyclicBarrier phaseBarrier = new CyclicBarrier(numOfThreads);

		//Populate the thread array
		for (int i = 0; i < sortingThreads.length; i++) {
			sortingThreads[i] = new SortThread(phaseBarrier, numberArray, 0, numOfPairs);
			sortingThreads[i].setName("SortThread #" + (i+1));
		}

		long startTime = System.nanoTime();

		try{
			boolean isSorted = false;
			//Loop until completely sorted
			while (!isSorted) {
				isSorted = true;

				//Odd Phase
				for (int i = 0; i < sortingThreads.length; i++) {
					//Set starting index to odd phase index
					sortingThreads[i] = new SortThread(phaseBarrier, numberArray, i * (numOfPairs * 2), numOfPairs);
					sortingThreads[i].setName("SortThread #" + (i+1));
					sortingThreads[i].start();
				}
				for (SortThread sortingThread : sortingThreads) {
					sortingThread.join();
				}

				//Even Phase
				int startIndex = 1;
				for (int i = 0; i < sortingThreads.length; i++) {
					//Set starting index to even phase index
					sortingThreads[i] = new SortThread(phaseBarrier, numberArray, startIndex, numOfPairs);
					sortingThreads[i].setName("SortThread #" + (i+1));
					startIndex += 2 * numOfPairs;
					sortingThreads[i].start();
				}
				for (SortThread sortingThread : sortingThreads) {
					sortingThread.join();
				}

				//Check if completely sorted
				for (SortThread sortingThread : sortingThreads) {
					if (!sortingThread.isSorted()) {
						isSorted = false;
						break;
					}
				}
			}
		}catch (InterruptedException ignored){ }

		long duration = System.nanoTime() - startTime;

		//Print the array after sorting
		System.out.print("After Sort: ");
		printArray(numberArray);

		System.out.printf("%nDuration: %f seconds", duration / 10e8);

	}

	private static void populateNumArray() {
		//Random number generator
		Random rand = new Random();

		numberArray = new int[arraySize];  //creates an array of numbers to sort
		for (int i = 0; i < numberArray.length; i++) {
			//Put some bounds on the ints to make it slightly more predictable
			numberArray[i] = rand.nextInt(100) + 1;
		}
	}

	public static void printArray(int[] array){
		StringBuilder sb = new StringBuilder("[").append(array[0]);

		for (int i = 1; i < array.length; i++){
			sb.append(", ").append(array[i]);
		}

		sb.append("]");
		System.out.println(sb.toString());
	}


}
