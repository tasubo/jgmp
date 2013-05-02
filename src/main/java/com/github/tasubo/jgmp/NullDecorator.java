package com.github.tasubo.jgmp;

class NullDecorator implements Decorating {

    public static final Decorating VALUE = new NullDecorator();

    private NullDecorator() {
    }

    @Override
    public Sendable with(Sendable sendable) {
        return sendable;
    }
}
