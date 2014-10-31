package com.synergy.util;

import java.util.regex.Pattern;

public final class StringValidator {

	public static boolean isUrlStringValid(String url) {
		return Pattern.matches("([a-z]|[A-Z]|[0-9]|_)*", url);
	}

}
