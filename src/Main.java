import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class Main {

	public static CyclicBarrier phaseBarrier;


	public static void main(String[] args) {


		Random rand = new Random();

		int[] array = new int[30];  //creates an array of numbers to sort
		for (int i = 0; i < array.length; i++) {
			array[i] = rand.nextInt();
		}


		SortThread[] threadArray = new SortThread[4];  //creates threads
		int startIndex = 0;
		for (int i = 0; i < threadArray.length; i++) {
			threadArray[i] = new SortThread(array, startIndex, (array.length/2) / threadArray.length);
		}

		phaseBarrier = new CyclicBarrier(threadArray.length);

		//Sets names of each thread and starts threads
		for (int i = 0; i < threadArray.length; i++) {
			threadArray[i].setName((i+1) + "");
			threadArray[i].start();
		}

		//Joins threads
		try {
			for (int i = 0; i < threadArray.length; i++) {
				threadArray[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

	}


}
