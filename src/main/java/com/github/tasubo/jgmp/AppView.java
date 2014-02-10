package com.github.tasubo.jgmp;

public final class AppView implements Sendable {

    private final Parametizer parametizer;

	
    private AppView(final String contentDescription) {
		this.parametizer = new Parametizer("t", "appview", "cd", contentDescription);
    }

    @Override
    public String getText() {
        return parametizer.getText();
    }

    @Override
    public Sendable with(Decorating app) {
        return new Combine(this).with(app);
    }

    public static AppView hit(final String contentDescription) {
        return new AppView(contentDescription);
    }
}
