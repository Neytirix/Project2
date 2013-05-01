/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/1/2013
 * Project 2
*/

public class StringComparator implements Comparator<String>{

	/** Given two Strings, compares them lexicographically. Returns a 
	 * negative number when the first string occurs first alphabetically,
	 * returns a positive number when the second string occurs first 
	 * alphabetically, returns zero when the two strings are equal. 
	 */
	@Override
	public int compare(String e1, String e2) {
		int length = Math.min(e1.length(), e2.length());
		for (int i = 0; i < length; i++) {
			if (e1.charAt(i) != e2.charAt(i)) {
				return e1.charAt(i) - e2.charAt(i);
			}  
		}
		if (length == e1.length() && length == e2.length()) {
			return 0;
		} else if (length == e1.length()) {
			return -1;
		} else {
			return 1;
		}
	}

}
