package com.github.tasubo.jgmp;

public class Dimension extends Custom {
    public static DimensionBuilder withIndex(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Sendable with(Sendable sendable) {
        throw new UnsupportedOperationException();
    }

    public static class DimensionBuilder {
        public Dimension value(String value) {
            throw new UnsupportedOperationException();
        }
    }
}
