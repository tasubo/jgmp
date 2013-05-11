package com.github.tasubo.jgmp;

public final class Dimension extends Custom {

    private final Parametizer parametizer;

    private Dimension(int index, String value) {
        this.parametizer = new Parametizer("cd" + index, value);
    }

    public static DimensionBuilder withIndex(int index) {
        Ensure.lessOrEqual(200, index);
        return new DimensionBuilder(index);
    }

    @Override
    public String getPart() {
        return parametizer.getText();
    }

    public final static class DimensionBuilder {
        private final int index;

        public DimensionBuilder(int index) {
            this.index = index;
        }

        public Dimension value(String value) {
            Ensure.length(150, value);
            return new Dimension(index, value);
        }
    }
}
