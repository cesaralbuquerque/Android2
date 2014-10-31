package com.synergy.app;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * A simple text cipher to encrypt/decrypt a string.
 */
public class TestCipher {
	private static byte[] linebreak = {}; // Remove Base64 encoder default
	// linebreak
	private static String secret = "tvnw63ufg9gh5392"; // secret key length must
	// be 16
	private static SecretKey key;
	private static Cipher cipher;
	private static Base64 coder;

	static {
		try {
			key = new SecretKeySpec(secret.getBytes(), "AES");
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
			coder = new Base64(32, linebreak, true);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static synchronized String encrypt(String plainText) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(plainText.getBytes());
		return new String(coder.encode(cipherText));
	}

	public static synchronized String decrypt(String codedText) throws Exception {
		byte[] encypted = coder.decode(codedText.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decrypted = cipher.doFinal(encypted);
		return new String(decrypted);
	}

	public static void main(String[] args) throws Exception {
		String plainText = "2010 starts new decade.";
		String encrypted = encrypt(plainText);
		// System.out.println(encrypted);

		String decrypted = decrypt(encrypted);
		// System.out.println(plainText + "," + decrypted);
		plainText = "vmail,1_1296340678911,-1";
		
		System.out.println(encrypt("2_1294196514523"));
		encrypted = encrypt(plainText);
		System.out.println("Encrypted: http://www.synergychat.com:9091/vmail?view=" + encrypted);

		decrypted = decrypt(encrypted);
		System.out.println("Original: http://www.synergychat.com:9091/chat?op=dvm&vid=" + plainText);
	}

}
