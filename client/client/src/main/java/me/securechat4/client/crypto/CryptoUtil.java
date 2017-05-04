package me.securechat4.client.crypto;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.lambdaworks.crypto.SCrypt;
import com.lambdaworks.crypto.SCryptUtil;

public class CryptoUtil {
	
	/**
	 * Hash the plaintext password and generate an output in the format described.
	 * @param password - password to hash
	 * @return hashed password
	 */
	public static String scrypt(String password) {
		return scrypt(password, 16384, 8, 1);
	}
	
	/**
     * Hash the supplied plaintext password and generate output in the format described
     * in {@link SCryptUtil}.
     *
     * @param passwd    Password.
     * @param N         CPU cost parameter.
     * @param r         Memory cost parameter.
     * @param p         Parallelization parameter.
     *
     * @return The hashed password.
     */
    public static String scrypt(String passwd, int N, int r, int p) {
        try {
            byte[] salt = new byte[16];
            SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);

            byte[] derived = SCrypt.scrypt(passwd.getBytes("UTF-8"), salt, N, r, p, 32);

            String params = Long.toString(log2(N) << 16L | r << 8 | p, 16);

            StringBuilder sb = new StringBuilder((salt.length + derived.length) * 2);
            sb.append("$s0$").append(params).append('$');
            sb.append(Base64.getEncoder().encodeToString(salt)).append('$');
            sb.append(Base64.getEncoder().encodeToString(derived));

            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("JVM doesn't support UTF-8?");
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("JVM doesn't support SHA1PRNG or HMAC_SHA256?");
        }
    }
	
	/**
     * Hash the supplied plaintext password and generate output in the format described
     * in {@link SCryptUtil}.
     *
     * @param password    Password.
     * @param N         CPU cost parameter.
     * @param r         Memory cost parameter.
     * @param p         Parallelization parameter.
     *
     * @return The hashed password.
     */
    public static String scrypt(String password, String saltEnc, int N, int r, int p) {
        try {
        	byte[] salt = Base64.getDecoder().decode(saltEnc);
            byte[] derived = SCrypt.scrypt(password.getBytes("UTF-8"), salt, N, r, p, 32);

            String params = Long.toString(log2(N) << 16L | r << 8 | p, 16);

            StringBuilder sb = new StringBuilder((salt.length + derived.length) * 2);
            sb.append("$s0$").append(params)
            	.append('$').append(saltEnc)
            	.append('$').append(Base64.getEncoder().encodeToString(derived));

            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("JVM doesn't support UTF-8?");
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("JVM doesn't support SHA1PRNG or HMAC_SHA256?");
        }
    }
    
    /**
	 * Converts a Key to a Base64 encoded string.
	 * @param key the Key to encoded
	 * @return a Base64 encoded Key string
	 */
	public static String encodeKeyToString(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	/**
	 * Converts a Base64 encoded string to a SecretKey
	 * @param encodedString the Base64 encoded key string
	 * @param algorithm is the algorithm the key will be used for
	 * @return a SecretKey for a specific algorithm
	 */
	public static SecretKey convertStringToKey(String encodedString, String algorithm) {		
		return new SecretKeySpec(Base64.getDecoder().decode(encodedString), algorithm);
	}

	private static int log2(int n) {
        int log = 0;
        if ((n & 0xffff0000 ) != 0) { n >>>= 16; log = 16; }
        if (n >= 256) { n >>>= 8; log += 8; }
        if (n >= 16 ) { n >>>= 4; log += 4; }
        if (n >= 4  ) { n >>>= 2; log += 2; }
        return log + (n >>> 1);
    }
}
