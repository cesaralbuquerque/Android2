package com.synergy.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class UtilOld {

	public static String toString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static String criptografar(String senha) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(senha.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(digest.digest());
		} catch (NoSuchAlgorithmException ns) {
			ns.printStackTrace();
			return senha;
		}
	}

	public static String encrypt(String sign) {

		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(sign.getBytes());
			byte[] hash = md.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10)
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				else
					hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
			sign = hexString.toString();
		} catch (Exception nsae) {
			nsae.printStackTrace();
		}
		return sign;
	}

	public String criptografaSenha(String senha) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
		String s = hash.toString(16);
		if (s.length() % 2 != 0)
			s = "0" + s;
		return s;
	}

}
