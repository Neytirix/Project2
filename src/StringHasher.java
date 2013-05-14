
public class StringHasher implements Hasher<String>{

	/**
	 * Given a String, returns a positive integer that can be used in 
	 * the hash function
	 */
	
	@Override
	public int hash(String e) {
		int sum = 0;
		for(int i = 0; i < e.length(); i++) {
			sum += e.charAt(i);
			sum *= 7;  //7 is just an arbitrary prime number
		}
		return Math.abs(sum);
	}

}
