
public class StringHasher implements Hasher<String>{

	@Override
	public int hash(String e) {
		int sum = 0;
		for(int i = 0; i < e.length(); i++) {
			sum += e.charAt(i);
			sum *= 7;  //can 7 be arbitrary? want a prime number 
		}
		return sum;
	}

}
