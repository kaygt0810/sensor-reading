package com.kg.sr.utils;

import java.time.Instant;
import java.time.format.DateTimeParseException;


public class DateUtils {
	public static boolean checkTimestamp(String input) {
		try {
	        Instant instant = Instant.parse(input);
	        
	    } catch (DateTimeParseException e) {
	        return false;
	    }

		return true;
	}
}
