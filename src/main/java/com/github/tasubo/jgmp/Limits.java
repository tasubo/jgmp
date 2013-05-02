package com.github.tasubo.jgmp;

public class Limits {
    public static void ensureLength(int length, String version) {
        if (version.length() > length) {
            throw new IllegalArgumentException("String is too long. Should be at most " + length);
        }
    }
}
