package com.github.tasubo.jgmp;


import java.util.Random;

public final class MpClient {
    private final String trackingId;
    private final String clientId;
    private final Decorating cacheBuster;
    private final Decorating anonymizingIp;
    private final AppendingDecorator appendingDecorator;
    private final Sender sender;
    private final Prefix prefix;

    public MpClient(MpClientBuilder b) {
        this.trackingId = b.trackingId;
        this.anonymizingIp = b.anonymizingIp;
        this.cacheBuster = b.cacheBuster;
        this.clientId = b.clientId;
        this.appendingDecorator = b.appendingDecorator;
        this.sender = b.sender;
        this.prefix = b.prefix;
    }

    public static MpClientBuilderStart withTrackingId(String trackingId) {
        return new MpClientBuilderStart(trackingId);
    }

    public void send(Sendable sendable) {
        sendable = sendable.with(anonymizingIp);
        sendable = sendable.with(cacheBuster);
        Parametizer parametizer = new Parametizer("tid", trackingId, "cid", clientId);
        Sendable with = sendable.with(appendingDecorator);
        String host = prefix.get() + "www.google-analytics.com/collect";
        String payload = "v=1" + parametizer.getText() + with.getText();
        sender.send(host, payload);
    }

    public static final class MpClientBuilderStart {
        private final String trackingId;

        private MpClientBuilderStart(String trackingId) {
            this.trackingId = trackingId;
        }

        public MpClientBuilder withClientId(String clientId) {
            Ensure.isUuid(clientId);
            return new MpClientBuilder(trackingId, clientId);
        }
    }

    public static final class MpClientBuilder {
        private final String trackingId;
        private final String clientId;
        private final AppendingDecorator appendingDecorator;
        private final HttpRequester httpRequester;
        private final Decorating cacheBuster;
        private final Decorating anonymizingIp;
        private final Sender sender;
        private final Prefix prefix;

        public MpClientBuilder(String trackingId, String clientId) {
            this.trackingId = trackingId;
            this.clientId = clientId;
            this.appendingDecorator = new AppendingDecorator();
            this.httpRequester = new JavaGetConnectionRequester();
            this.cacheBuster = new NullDecorating();
            this.anonymizingIp = new NullDecorating();
            this.sender = new Sender.GET(httpRequester);
            this.prefix = new Prefix.PLAIN();
        }

        public MpClientBuilder(String trackingId, String clientId, AppendingDecorator appendingDecorator,
                               HttpRequester httpRequester, Decorating cacheBuster, Decorating anonymizingIp,
                               Sender sender, Prefix prefix) {
            this.trackingId = trackingId;
            this.clientId = clientId;
            this.appendingDecorator = appendingDecorator;
            this.httpRequester = httpRequester;
            this.cacheBuster = cacheBuster;
            this.anonymizingIp = anonymizingIp;
            this.sender = sender;
            this.prefix = prefix;
        }

        public MpClientBuilder withCacheBuster() {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, new CacheBuster(), anonymizingIp, sender, prefix);
        }

        public MpClientBuilder anonymizingIp() {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, cacheBuster, new AnonymizingIp(), sender, prefix);
        }

        public MpClient create() {
            MpClient client = new MpClient(this);
            return client;
        }

        public MpClientBuilder using(Decorating decorating) {
            AppendingDecorator localDecorator = new AppendingDecorator(appendingDecorator, decorating);
            return new MpClientBuilder(trackingId, clientId, localDecorator, httpRequester, cacheBuster, anonymizingIp, sender, prefix);
        }


        public MpClientBuilder httpRequester(HttpRequester httpRequester) {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, cacheBuster, anonymizingIp, sender.with(httpRequester), prefix);
        }

        public MpClientBuilder noCacheBuster() {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, new NullDecorating(), anonymizingIp, sender, prefix);
        }

        public MpClientBuilder noAnonymizingIp() {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, cacheBuster, new NullDecorating(), sender, prefix);
        }

        public MpClientBuilder usePost() {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, cacheBuster, anonymizingIp, new Sender.POST(httpRequester), prefix);
        }

        public MpClientBuilder useSsl() {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, cacheBuster, anonymizingIp, sender, new Prefix.SSL());
        }

        public MpClientBuilder usePlainHttp() {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, cacheBuster, anonymizingIp, sender, new Prefix.PLAIN());
        }

        public MpClientBuilder useGet() {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, cacheBuster, anonymizingIp, new Sender.GET(httpRequester), prefix);
        }
    }

    static abstract class Prefix {
        abstract String get();

        static final class SSL extends Prefix {

            @Override
            String get() {
                return "https://";
            }
        }

        static final class PLAIN extends Prefix {

            @Override
            String get() {
                return "http://";
            }
        }
    }

    static abstract class Sender {

        abstract void send(String host, String payload);

        abstract Sender with(HttpRequester httpRequester);

        static final class GET extends Sender {
            private final HttpRequester httpRequester;

            GET(HttpRequester httpRequester) {
                this.httpRequester = httpRequester;
            }

            @Override
            void send(String host, String payload) {
                httpRequester.sendGet(host, payload);
            }

            @Override
            Sender with(HttpRequester httpRequester) {
                return new GET(httpRequester);
            }
        }

        static final class POST extends Sender {
            private final HttpRequester httpRequester;

            POST(HttpRequester httpRequester) {
                this.httpRequester = httpRequester;
            }

            @Override
            void send(String host, String payload) {
                httpRequester.sendPost(host, payload);
            }

            @Override
            Sender with(HttpRequester httpRequester) {
                return new POST(httpRequester);
            }
        }
    }

    private static final class NullDecorating implements Decorating {

        @Override
        public String getPart() {
            return "";
        }
    }

    private static final class AnonymizingIp implements Decorating {

        @Override
        public String getPart() {
            return "&aip=1";
        }
    }

    private static final class CacheBuster implements Decorating {

        @Override
        public String getPart() {
            int token = new Random().nextInt(Integer.MAX_VALUE);
            String text = "&z=" + token;
            return text;
        }
    }
}
