package backEndQuickBank.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	public static final String BANK_MAIL="mehdi.ouqas1999@gmail.com";
	public static final Random RANDOM = new SecureRandom();
	public static final String ALPHABET ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static String generateString(int lenght) {
		StringBuilder userId = new  StringBuilder();
		for(int i=0 ; i<lenght ; i++) {
			userId.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		
		return new String(userId);
	}
}