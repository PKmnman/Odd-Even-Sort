public class OddEvenSortThread extends Thread{

    public int[] numbers;
    public int[] sortedPairs;
    public int numberOfPairs;

    public OddEvenSortThread(int[] numbers, int numberOfPairs) {
        super();

        this.numbers = numbers;
        this.sortedPairs = new int[numberOfPairs*2];
        this.numberOfPairs = numberOfPairs;
    }

    public OddEvenSortThread() {

    }

    public int[] getSortedPairs() {
        return this.sortedPairs;
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
        super.start();
    }

    public void run() {

    }
}
