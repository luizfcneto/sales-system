package com.vsoftware.utils;

public class ParseUtils {
	
	public static double parseDouble(String value, String fieldName) throws NumberFormatException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Formato de " + fieldName + " invalido.");
        }
    }
	
	public static int parseInt(String value, String fieldName) throws NumberFormatException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Formato de " + fieldName + " invalido.");
        }
    }

}
