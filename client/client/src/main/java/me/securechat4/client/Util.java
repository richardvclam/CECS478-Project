package me.securechat4.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Util {
	
	/**
	 * Returns a concatenated byte-array from byte-array {@code a} and byte-array {@code b}.
	 * @param a is the first byte-array
	 * @param b is the second byte-array
	 * @return a concatenated byte-array
	 */
	public static byte[] concatenate(byte[] a, byte[] b) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(a.length + b.length);
		try {
			outputStream.write(a);
			outputStream.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return outputStream.toByteArray();
	}

}
