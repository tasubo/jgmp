package com.github.tasubo.jgmp;

public final class AppView implements Sendable {

    private final Parametizer parametizer = new Parametizer("t", "appview");

    private AppView() {
    }

    @Override
    public String getText() {
        return parametizer.getText();
    }

    public static AppView hit() {
        return new AppView();
    }
}
