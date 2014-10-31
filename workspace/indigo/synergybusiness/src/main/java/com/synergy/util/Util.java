package com.synergy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	private static MessageDigest digest;

	private Util() {
	}

	static {
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String toString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static byte[] digest(String password) {
		return digest.digest(password.getBytes());
	}

	// public static String criptografar(String senha) {
	// try {
	//			
	// digest.update(senha.getBytes());
	// BASE64Encoder encoder = new BASE64Encoder();
	// return encoder.encode(digest.digest());
	// } catch (NoSuchAlgorithmException ns) {
	// ns.printStackTrace();
	// return senha;
	// }
	// }

}
