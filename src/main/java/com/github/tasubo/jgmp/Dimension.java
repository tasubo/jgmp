package com.github.tasubo.jgmp;

public final class Dimension extends Custom {

    private final CombinedSendable combinedSendable;

    public Dimension(int index, String value) {
        this.combinedSendable = new CombinedSendable("cd" + index, value);
    }

    public static DimensionBuilder withIndex(int index) {
        Limits.ensureLessOrEqual(200, index);
        return new DimensionBuilder(index);
    }

    @Override
    public Sendable with(Sendable sendable) {
        return combinedSendable.with(sendable);
    }

    public final static class DimensionBuilder {
        private final int index;

        public DimensionBuilder(int index) {
            this.index =index;
        }

        public Dimension value(String value) {
            Limits.ensureLength(150, value);
            return new Dimension(index, value);
        }
    }
}
