package com.github.tasubo.jgmp;

public final class PageView implements Sendable {

    private final Parametizer parametizer = new Parametizer("t", "pageview");

    private PageView() {
    }

    @Override
    public String getText() {
        return parametizer.getText();
    }

    public static PageView hit() {
        return new PageView();
    }
}
