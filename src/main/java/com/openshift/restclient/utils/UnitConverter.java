package com.openshift.restclient.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitConverter {

	private static final Pattern PATTERN = Pattern.compile("(\\d*)([a-zA-z]{1,2})");

	public static long convert(String input) {
		Matcher matcher = PATTERN.matcher(input);
		if (matcher.find()) {
			int number = Integer.parseInt(matcher.group(1));
			int exponent = 1;
			switch (matcher.group(2)) {
				case "m":
					return number;
				case "K":
				case "Ki":
					exponent = 1;
					break;
				case "M":
				case "Mi":
					exponent = 2;
					break;
				case "G":
				case "Gi":
					exponent = 3;
					break;
				case "T":
				case "Ti":
					exponent = 4;
					break;
				case "P":
				case "Pi":
					exponent = 5;
					break;
				case "E":
				case "Ei":
					exponent = 5;
					break;

			}

			return number * Math.round(Math.pow(1024, exponent)) * 1000;
		} else {
			return Long.parseLong(input) * 1000;
		}
	}
}
