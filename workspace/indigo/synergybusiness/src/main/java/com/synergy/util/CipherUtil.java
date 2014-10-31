package com.synergy.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CipherUtil {

	private static Base64 coder;
	// linebreak
	private static byte[] linebreak = {}; // Remove Base64 encoder default
	private static String secret = "tvnw63ufg9gh5392"; // secret key length must
	// be 16
	private static SecretKey key;
	private static Cipher cipher;

	static {
		try {
//			secret = SynergyConfig.instance().getUrlEncryptSecret();
//			if (secret == null) {
//				throw new IllegalStateException("SecretKey property is null: synergy.urlencrypt.secret");
//			}
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
}
