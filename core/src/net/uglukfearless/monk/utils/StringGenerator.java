package net.uglukfearless.monk.utils;

import java.util.Random;

/**
 * Created by Ugluk on 18.12.2016.
 */

public class StringGenerator {

    private static final char [] SYMBOLS_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ".toCharArray();

    private static final Random RANDOM = new Random();

    private static final StringBuilder STRING_BUILDER = new StringBuilder();

    public static String generateRandString(int length) {
        STRING_BUILDER.setLength(0);

        if (length>0) {
            for (int i=0; i<length; i++) {
                STRING_BUILDER.append(SYMBOLS_STRING[RANDOM.nextInt(SYMBOLS_STRING.length)]);
            }
        }
        return STRING_BUILDER.toString();
    }
}
