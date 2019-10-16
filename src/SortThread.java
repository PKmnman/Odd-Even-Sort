import java.util.concurrent.BrokenBarrierException;

public class SortThread extends Thread{

    public int[] numbers;
    public int[] sortedPairs;
    public int numberOfPairs;

    private int startIndex;
    private int endIndex;
    private boolean sorted;

    public SortThread(int[] numberArray, int startIndex, int numOfPairs) {
        this.numbers = numberArray;
        this.sortedPairs = new int[numOfPairs*2];
        this.numberOfPairs = numOfPairs;
		this.startIndex = startIndex;
        this.endIndex = (startIndex + (numberOfPairs * 2));
    }

    public void sortPairs(int startIndex) {
        for (int i = 0; i < numberOfPairs; i++) {
            if (numbers[startIndex-1] > numbers[startIndex]) {  //if the first number is larger
                sortedPairs[startIndex-1] = numbers[startIndex];
                sortedPairs[startIndex] = numbers[startIndex-1];
            } else {
                sortedPairs[startIndex-1] = numbers[startIndex-1];
                sortedPairs[startIndex] = numbers[startIndex];
            }
            startIndex += 2;
        }
    }

    public synchronized void start() {
    	sorted = false;
        super.start();
    }

    public void run() {
		while (!sorted){

			boolean sorted = true;
			int temp = 0;

			//Perform Bubble Sort (Odd Pass)
			for (int i = startIndex; i < endIndex && i < numbers.length - 1; i += 2){
				if(numbers[i] > numbers[i+1]){
					temp = numbers[i];
					numbers[i] = numbers[i+1];
					numbers[i+1] = temp;
					sorted = false;
				}
			}

			//Use CyclicBarrier to synchronize phases
			//Wait until all threads have reached the even phase
			try {
				Main.phaseBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}

			for (int i = startIndex + 1; i < endIndex && i < numbers.length - 1; i += 2){
				if(numbers[i] > numbers[i+1]){
					temp = numbers[i];
					numbers[i] = numbers[i+1];
					numbers[i+1] = temp;
					sorted = false;
				}
			}

			//Wait until all threads have completed the even phase
			try {
				Main.phaseBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}

		}
    }
}
