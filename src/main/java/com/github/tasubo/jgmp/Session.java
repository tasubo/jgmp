package com.github.tasubo.jgmp;

public class Session implements Decorating {
    public static Session start() {
        throw new UnsupportedOperationException();
    }

    public Sendable with(Sendable sendable) {
        throw new UnsupportedOperationException();
    }

    public static Session end() {
        throw new UnsupportedOperationException();
    }
}
