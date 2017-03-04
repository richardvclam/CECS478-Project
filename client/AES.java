import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AES {
	
	private static int BIT_SIZE = 256;
	
	private SecretKey key;
	
	public AES() {
		KeyGenerator keygen = null;
		try {
			keygen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keygen.init(BIT_SIZE);
		key = keygen.generateKey();
	}
	
	public AES(SecretKey key) {
		this.key = key;
	}
	
	public String encrypt(String message) {
		IvParameterSpec iv = new IvParameterSpec(32);
		return "";
	}

}
