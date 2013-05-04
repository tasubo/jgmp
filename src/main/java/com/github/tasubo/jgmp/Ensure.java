package com.github.tasubo.jgmp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Ensure {
//35009a79-1a05-49d7-b876-2b884d0f825b
    private static final Pattern UUID = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");

    public static void length(int length, String string) {
        if (string == null) {
            return;
        }

        if (string.length() > length) {
            throw new IllegalArgumentException("String is too long. Should be at most " + length);
        }
    }

    public static void lessOrEqual(int size, int value) {
        if (value > size) {
            throw new IllegalArgumentException("String is too long. Should be at most " + size);
        }
    }

    public static void nonEmpty(String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            throw new IllegalArgumentException("Variable should not be empty");
        }
    }

    public static void isUuid(String clientId) {
        Matcher matcher = UUID.matcher(clientId);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Should have been valid UUID given, but found: " + clientId);
        }
    }
}
