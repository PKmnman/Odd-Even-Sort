import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SortThread extends Thread{

	private static ThreadGroup sorting = new ThreadGroup("Sorting");

	private CyclicBarrier phaseBarrier;
    private int[] numbers;
    private int startIndex, endIndex;
    private boolean isSorted;

    public SortThread(CyclicBarrier barrier, int[] numberArray, int startIndex, int numOfPairs) {
        super(sorting, "SortingThread");
    	this.numbers = numberArray;
		this.startIndex = startIndex;
        this.endIndex = (startIndex + (numOfPairs * 2));
        this.phaseBarrier = barrier;
        isSorted = false;
    }

	@Override
	public synchronized void start() {
    	isSorted = false;
		super.start();
	}

	public void run() {
		isSorted = true;
		int temp;

		//Perform Bubble Sort
		for (int i = startIndex; i < endIndex && i < numbers.length - 1; i += 2){
			if(numbers[i] > numbers[i+1]){
				temp = numbers[i];
				numbers[i] = numbers[i+1];
				numbers[i+1] = temp;
				isSorted = false;
			}
		}

		try {
			phaseBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}

    }

    public synchronized int getStartIndex(){
    	return this.startIndex;
    }

    public void setStartIndex(int index){
    	this.startIndex = index;
    }

    public synchronized boolean isSorted(){
    	return this.isSorted;
    }

}
