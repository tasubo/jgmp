package com.github.tasubo.jgmp;

public final class Metric extends Custom {

    private final CombinedSendable combinedSendable;

    public Metric(int index, int value) {
        this.combinedSendable = new CombinedSendable("cm" + index, value);
    }

    @Override
    public Sendable with(Sendable sendable) {
        return combinedSendable.with(sendable);
    }

    public static MetricBuilder withIndex(int index) {
        Limits.ensureLessOrEqual(200, index);
        return new MetricBuilder(index);
    }

    public final static class MetricBuilder {
        private final int index;

        public MetricBuilder(int index) {
            this.index = index;
        }

        public Custom value(int value) {
            return new Metric(index, value);
        }
    }
}
