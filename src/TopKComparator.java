
public class TopKComparator implements Comparator<DataCount<String>> {

	/** Given two Strings, compares them lexicographically. Returns a 
	 * negative number when the first string occurs first alphabetically,
	 * returns a positive number when the second string occurs first 
	 * alphabetically, returns zero when the two strings are equal. 
	 */

	StringComparator alphabetical = new StringComparator();
	@Override
	public int compare(DataCount<String> c1, DataCount<String> c2) {
		if(c1.count != c2.count)
			return c1.count - c2.count;
		return alphabetical.compare(c2.data, c1.data);
	}
}
