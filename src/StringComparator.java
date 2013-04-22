// Sasha Babayan & Brian Park
// 4/21/2013
// Project 2

class StringComparator implements Comparator<String>{

	@Override
	public int compare(String e1, String e2) {
		int length = Math.min(e1.length(), e2.length());
		for (int i = 0; i < length; i++) {
			if (e1.charAt(i) > e2.charAt(i)) {
				return 1;
			} else if(e1.charAt(i) < e2.charAt(i)) {
				return -1;
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
