package com.github.tasubo.jgmp;

public final class App implements Decorating {

    private final Parametizer parametizer;

    public App(AppBuilder b) {
        parametizer = new Parametizer("an", b.name, "av", b.version);
    }

    @Override
    public Sendable with(Sendable sendable) {
        return new CombinedSendable(parametizer).with(sendable);
    }

    public static AppBuilder named(String name) {
        Ensure.length(100, name);
        return new AppBuilder(name);
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
