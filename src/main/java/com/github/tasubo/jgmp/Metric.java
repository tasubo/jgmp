package com.github.tasubo.jgmp;

public class Metric extends Custom {
    @Override
    public Sendable with(Sendable sendable) {
        throw new UnsupportedOperationException();
    }

    public static MetricBuilder withIndex(int index) {
        throw new UnsupportedOperationException();
    }

    public static class MetricBuilder {
        public Custom value(int value) {
            throw new UnsupportedOperationException();
        }
    }
}
