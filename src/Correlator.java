import java.io.IOException;


public class Correlator {

	public static void main(String[] args) {
		DataCounter<String> counter1 = new BinarySearchTree<String>(new StringComparator());
		
		DataCounter<String> counter2 = new BinarySearchTree<String>(new StringComparator());
		if (args.length != 3) {
			System.out.println("Usage: [ -b | -a | -m | -h ] <filename1> <filename2>");
		}
		
		if(args[0].equals("-b")) {
			counter1 = new BinarySearchTree<String>(new StringComparator());
			counter2 = new BinarySearchTree<String>(new StringComparator());
		} else if (args[0].equals("-a")) {
			counter1 = new AVLTree<String>(new StringComparator());
			counter2 = new AVLTree<String>(new StringComparator());
		} else if (args[0].equals("-m")) {
			counter1 = new MoveToFrontList<String>(new StringComparator());
			counter2 = new MoveToFrontList<String>(new StringComparator());
		} else if (args[0].equals("-h")) {
			counter1 = new Hashtable<String>(new StringComparator(), new StringHasher());
			counter2 = new Hashtable<String>(new StringComparator(), new StringHasher());
		} else {
			System.out.println("Incorrect argument. Usage: [ -b | -a | -m | -h ] ");
			System.exit(1);
		}
		int sum1 = parseAndCount(args[1],counter1);
		int sum2 = parseAndCount(args[2],counter2);
		 DataCount<String>[] counts1 = WordCount.getCountsArray(counter1);
		 DataCount<String>[] counts2 = WordCount.getCountsArray(counter2);
	}
	
	private static int parseAndCount(String file, DataCounter<String> counter) {
		int sum = 0;
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			while (word != null) {
				counter.incCount(word);
				sum++;
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
		}
		return sum;
	}
}
