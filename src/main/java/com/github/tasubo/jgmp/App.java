package com.github.tasubo.jgmp;

public class App implements Decorating {

    private Parametizer parametizer;

    @Override
    public Sendable with(Sendable sendable) {
        return new CombinedSendable(parametizer).with(sendable);
    }

    public static AppBuilder named(String name) {
        Limits.ensureLength(100, name);
        return new AppBuilder(name);
    }

    public static class AppBuilder {

        private String name;
        private String version;

        private AppBuilder() {

        }

        private AppBuilder(String name) {
            this.name = name;
        }

        public AppBuilder name(String name) {
            Limits.ensureLength(100, name);
            this.name = name;
            return this;
        }

        public AppBuilder version(String version) {
            Limits.ensureLength(100, version);
            this.version = version;
            return this;
        }

        public App create() {
            App app = new App();
            app.parametizer = new Parametizer("an", name, "av", version);
            return app;
        }
    }
}
