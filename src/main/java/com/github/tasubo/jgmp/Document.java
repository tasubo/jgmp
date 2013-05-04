package com.github.tasubo.jgmp;

public final class Document implements Decorating {
    private final Parametizer parametizer;

    private Document(Parametizer parametizer) {
        this.parametizer = parametizer;
    }

    public Sendable with(Sendable sendable) {
        return new CombinedSendable(parametizer).with(sendable);
    }

    public static DocumentBuilder with() {
        return new DocumentBuilder();
    }

    public final static class DocumentBuilder {
        private final String location;
        private final String hostname;
        private final String path;
        private final String title;
        private final String description;

        private DocumentBuilder() {
            this.location = null;
            this.hostname = null;
            this.path = null;
            this.title = null;
            this.description = null;
        }

        private DocumentBuilder(String location, String hostname, String path, String title, String description) {
            this.location = location;
            this.hostname = hostname;
            this.path = path;
            this.title = title;
            this.description = description;
        }

        public DocumentBuilder location(String location) {
            Limits.ensureLength(2048, location);
            return new DocumentBuilder(location, hostname, path, title, description);
        }

        public DocumentBuilder hostname(String hostname) {
            Limits.ensureLength(100, hostname);
            return new DocumentBuilder(location, hostname, path, title, description);
        }

        public DocumentBuilder path(String path) {
            Limits.ensureLength(2048, path);
            return new DocumentBuilder(location, hostname, path, title, description);
        }

        public DocumentBuilder title(String title) {
            Limits.ensureLength(1500, title);
            return new DocumentBuilder(location, hostname, path, title, description);
        }

        public DocumentBuilder description(String description) {
            Limits.ensureLength(2048, description);
            return new DocumentBuilder(location, hostname, path, title, description);
        }

        public Document create() {
            Parametizer parametizer = new Parametizer("dl", location, "dh", hostname,
                    "dp", path, "dt", title, "cd", description);
            Document document = new Document(parametizer);
            return document;
        }
    }
}
