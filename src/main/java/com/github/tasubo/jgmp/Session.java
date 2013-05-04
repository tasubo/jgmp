package com.github.tasubo.jgmp;

public final class Session implements Decorating {
    private final CombinedSendable combinedSendable;

    private Session(String state) {
        this.combinedSendable = new CombinedSendable("sc", state);
    }

    public static Session start() {
        return new Session("start");
    }

    public Sendable with(Sendable sendable) {
        return combinedSendable.with(sendable);
    }

    public static Session end() {
        return new Session("end");
    }
}
