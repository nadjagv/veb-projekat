package utils;

import java.util.Random;

public class StringGenerator {
	static Random random = new Random();

	public StringGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	public static String generateRandomString(int len) {
	    int leftLimit = 48; // '0'
	    int rightLimit = 122; //  'z'
	    int targetStringLength = len;
	    

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    return generatedString;
	}

}
