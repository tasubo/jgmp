package com.github.tasubo.jgmp;

public final class Referrer implements Decorating {
    private final CombinedSendable combinedSendable;

    public Referrer(String source) {
        this.combinedSendable = new CombinedSendable("dr", source);
    }

    public static Referrer from(String source) {
        Limits.ensureLength(2048, source);
        return new Referrer(source);
    }

    public Sendable with(Sendable sendable) {
        return combinedSendable.with(sendable);
    }
}
