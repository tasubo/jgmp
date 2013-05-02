package com.github.tasubo.jgmp;

public class Document implements Decorating {
    private Parametizer parametizer;

    public Sendable with(Sendable sendable) {
        return new CombinedSendable(parametizer).with(sendable);
    }

    public static DocumentBuilder with() {
        return new DocumentBuilder();
    }

    public static class DocumentBuilder {
        private String location;
        private String hostname;
        private String path;
        private String title;
        private String description;

        private DocumentBuilder() {
        }

        public DocumentBuilder location(String url) {
            Limits.ensureLength(2048, url);
            this.location = url;
            return this;
        }

        public DocumentBuilder hostname(String hostname) {
            Limits.ensureLength(100, hostname);
            this.hostname = hostname;
            return this;
        }

        public DocumentBuilder path(String path) {
            Limits.ensureLength(2048, path);
            this.path = path;
            return this;
        }

        public DocumentBuilder title(String title) {
            Limits.ensureLength(1500, title);
            this.title = title;
            return this;
        }

        public DocumentBuilder description(String description) {
            Limits.ensureLength(2048, description);
            this.description = description;
            return this;
        }

        public Document create() {
            Document document = new Document();
            document.parametizer = new Parametizer("dl", location, "dh", hostname,
                    "dp", path, "dt", title, "cd", description);
            return document;
        }
    }
}
