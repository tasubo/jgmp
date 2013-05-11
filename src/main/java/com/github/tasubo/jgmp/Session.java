package com.github.tasubo.jgmp;

public final class Session implements Decorating {
    private final Parametizer parametizer;

    private Session(String state) {
        this.parametizer = new Parametizer("sc", state);
    }

    public static Session start() {
        return new Session("start");
    }

    public static Session end() {
        return new Session("end");
    }

    @Override
    public String getPart() {
        return parametizer.getText();
    }
}
