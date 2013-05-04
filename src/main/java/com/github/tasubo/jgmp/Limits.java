package com.github.tasubo.jgmp;

public class Limits {
    public static void ensureLength(int length, String version) {
        if (version.length() > length) {
            throw new IllegalArgumentException("String is too long. Should be at most " + length);
        }
    }

    public static void ensureLessOrEqual(int size, int value) {
        if (value > size) {
            throw new IllegalArgumentException("String is too long. Should be at most " + size);
        }
    }

    public static void requireNonEmpty(String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            throw new IllegalArgumentException("Variable should not be empty");
        }
    }
}
