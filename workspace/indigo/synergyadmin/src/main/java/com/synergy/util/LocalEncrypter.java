package com.synergy.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class LocalEncrypter {

	// BzJkeqImeVIL7GFXrhVbHEU-ZP3fPTtd

	private static String algorithm = "DESede";
	private static Key key = null;
	private static Cipher cipher = null;
	private static Base64 coder;
	// linebreak
	private static byte[] linebreak = {}; // Remove Base64 encoder default

	private static void setUp() throws Exception {
		// key = KeyGenerator.getInstance(algorithm).generateKey();
		// writeKey((SecretKey) key, new File("test.key"));
		// key = readKey(new File("test.key"));
		coder = new Base64(32, linebreak, true);
		key = new SecretKeySpec(coder.decode("BzJkeqImeVIL7GFXrhVbHEU-ZP3fPTtd"), "DESede");
		cipher = Cipher.getInstance("DESede");
	}

	public static void main(String[] args) throws Exception {
		setUp();
		byte[] encryptionBytes = null;
		// String input = args[0];

		String input = "fabiano@test.com";

		System.out.println("Entered: " + input);
		encryptionBytes = encrypt(input);
		final String strEncrypted = coder.encodeToString(encryptionBytes);
		System.out.println("Encypted: " + strEncrypted);
		System.out.println("Recovered: " + decryptString(strEncrypted));
	}

	public static void writeKey(SecretKey key, File f) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		// Convert the secret key to an array of bytes like this
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
		DESedeKeySpec keyspec = (DESedeKeySpec) keyfactory.getKeySpec(key, DESedeKeySpec.class);
		byte[] rawkey = keyspec.getKey();

		// Write the raw key to the file
		FileOutputStream out = new FileOutputStream(f);
		out.write(rawkey);
		out.close();
	}

	public static SecretKey readKey(File f) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		// Read the raw bytes from the keyfile
		DataInputStream in = new DataInputStream(new FileInputStream(f));
		byte[] rawkey = new byte[(int) f.length()];
		in.readFully(rawkey);
		in.close();

		// Convert the raw bytes to a secret key like this
		DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
		SecretKey key = keyfactory.generateSecret(keyspec);
		return key;
	}

	public static byte[] encrypt(String input) throws Exception {
		if (cipher == null) {
			setUp();
		}
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] inputBytes = input.getBytes();
		return cipher.doFinal(inputBytes);
	}

	public static String decryptString(String encoded) throws Exception {
		if (cipher == null) {
			setUp();
		}
		return decrypt(coder.decode(encoded));
	}

	public static String decrypt(byte[] encryptionBytes) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] recoveredBytes = cipher.doFinal(encryptionBytes);
		String recovered = new String(recoveredBytes);
		return recovered;
	}
}
