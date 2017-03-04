import javax.crypto.SecretKey;

public class Crypto {
	
	public static void encrypt(String message, String rsaPublicKey) {
		AES aes = new AES();
		
	}
	
	public static void decrypt(String jsonObj, String rsaPrivateKey) {
		
		SecretKey key = null;
		AES aes = new AES(key);
	}

}
