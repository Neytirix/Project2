/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/14/2013
 * Project 2
*/

public class TopKComparator implements Comparator<DataCount<String>> {

	/** A Comparator for DataCount<String> that sorts two data-counts in the order
	 *  we need for the output of WordCount when topK is chosen: lower frequency 
	 *  comes first and frequency ties are resolved in alphabetical order.
	 *  
	 *  Uses String Comparator
	 */
	StringComparator alphabetical = new StringComparator();
	@Override
	public int compare(DataCount<String> c1, DataCount<String> c2) {
		if(c1.count != c2.count)
			return c1.count - c2.count;
		return alphabetical.compare(c2.data, c1.data);
	}
}
