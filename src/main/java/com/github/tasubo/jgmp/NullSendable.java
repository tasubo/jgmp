package com.github.tasubo.jgmp;

class NullSendable implements Sendable {
    public static final Sendable VALUE = new NullSendable();

    private NullSendable() {
    }

    @Override
    public String getText() {
        return "";
    }
}
