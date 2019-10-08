public class Main {

	public static void main(String[] args) {
		int[] array = new int[30];  //creates an array of numbers to sort
		for (int i = 0; i < array.length; i++) {
			array[i] = (int)Math.random() * 20;
		}

		OddEvenSortThread[] threadArray = new OddEvenSortThread[4];  //creates threads
		for (int i = 0; i < threadArray.length; i++) {
			threadArray[i] = new OddEvenSortThread(array, );
		}




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
