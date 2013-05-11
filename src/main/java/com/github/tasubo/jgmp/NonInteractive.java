package com.github.tasubo.jgmp;

public final class NonInteractive implements Decorating {

    private NonInteractive() {
    }

    public static NonInteractive hit() {
        return new NonInteractive();
    }

    @Override
    public String getPart() {
        return "&ni=1";
    }
}