package com.github.tasubo.jgmp;

public final class App implements Decorating {

    private final Parametizer parametizer;

    private App(AppBuilder b) {
        parametizer = new Parametizer("an", b.name, "av", b.version);
    }

    public static AppBuilder named(String name) {
        Ensure.length(100, name);
        return new AppBuilder(name);
    }

    @Override
    public String getPart() {
        return parametizer.getText();
    }

    public static class AppBuilder {

        private final String name;
        private final String version;

        private AppBuilder(String name, String version) {
            this.name = name;
            this.version = version;
        }

        private AppBuilder(String name) {
            this.name = name;
            this.version = null;
        }

        public AppBuilder version(String version) {
            Ensure.length(100, version);
            return new AppBuilder(name, version);
        }

        public App create() {
            App app = new App(this);
            return app;
        }
    }
}
