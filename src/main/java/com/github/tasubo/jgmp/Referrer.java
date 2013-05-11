package com.github.tasubo.jgmp;

public final class Referrer implements Decorating {
    private final Parametizer parametizer;

    private Referrer(String source) {
        this.parametizer = new Parametizer("dr", source);
    }

    public static Referrer from(String source) {
        Ensure.length(2048, source);
        return new Referrer(source);
    }

    @Override
    public String getPart() {
        return parametizer.getText();
    }
}
