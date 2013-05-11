package com.github.tasubo.jgmp;

public final class Metric extends Custom {

    private final Parametizer parametizer;

    public Metric(int index, int value) {
        this.parametizer = new Parametizer("cm" + index, value);
    }

    public static MetricBuilder withIndex(int index) {
        Ensure.lessOrEqual(200, index);
        return new MetricBuilder(index);
    }

    @Override
    public String getPart() {
        return parametizer.getText();
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
