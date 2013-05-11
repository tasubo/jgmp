package com.github.tasubo.jgmp;

public final class UserTiming implements Sendable {

    private final Parametizer parametizer;

    UserTiming(Parametizer parametizer) {
        this.parametizer = parametizer;
    }

    @Override
    public String getText() {
        return parametizer.getText();
    }

    @Override
    public Sendable with(Decorating app) {
        return new Combine(this).with(app);
    }
}
