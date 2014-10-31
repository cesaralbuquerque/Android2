package com.synergy.util;

public class Test {

	public static void main(String[] args) throws Exception {
		/*
		 * CheckOutConnection coc = new CheckOutConnection();
		 * coc.setClientAccnum("936949"); coc.setFormPrice((float)19.99);
		 * coc.setFormPeriod(15); coc.setFormRebills(99);
		 * coc.setClientSubacc("0000"); coc.setFormName("144cc");
		 * coc.setFormRecurringPeriod(30);
		 * coc.setFormRecurringPrice((float)19.99);
		 * //coc.setClientId(client.getId().toString()); //coc.setQuantity(q);
		 * System.out.println(coc.getCheckOutUrl());
		 */

		final byte[] encrypt = LocalEncrypter.encrypt("fabiano@test.com");
		final String str = new String(encrypt);
		System.out.println(str);
		System.out.println(LocalEncrypter.decrypt(encrypt));
		System.out.println(LocalEncrypter.decryptString("GDTqzFI3Lx2PKSCPCjOzzg=="));
		
		
//		String a ="\n\n            fgdfgdgdfgsfd    \n\n\n\n";
//		System.out.println(a);
//		System.out.println("==========");
//		System.out.println(a.trim());
	}

}
