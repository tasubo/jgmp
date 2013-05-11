package com.github.tasubo.jgmp;


import java.util.Random;

public final class MpClient {
    private final String trackingId;
    private final String clientId;
    private final Boolean cacheBuster;
    private final Boolean anonymizingIp;
    private final HttpRequester httpRequester;
    private final AppendingDecorator appendingDecorator;

    public MpClient(MpClientBuilder b) {
        this.trackingId = b.trackingId;
        this.httpRequester = b.httpRequester;
        this.anonymizingIp = b.anonymizingIp;
        this.cacheBuster = b.cacheBuster;
        this.clientId = b.clientId;
        this.appendingDecorator = b.appendingDecorator;
    }

    public static MpClientBuilderStart withTrackingId(String trackingId) {
        return new MpClientBuilderStart(trackingId);
    }

    public void send(Sendable sendable) {
        if (cacheBuster) {
            sendable = sendable.with(new CacheBuster());
        }
        Parametizer parametizer = new Parametizer("tid", trackingId, "cid", clientId, "anonymizingIp", anonymizingIp);
        Sendable with = sendable.with(appendingDecorator);
        httpRequester.send("http://www.google-analytics.com/collect?v=1" + parametizer.getText() + with.getText());
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
        private final boolean cacheBuster;
        private final Boolean anonymizingIp;

        public MpClientBuilder(String trackingId, String clientId) {
            this.trackingId = trackingId;
            this.clientId = clientId;
            this.appendingDecorator = new AppendingDecorator();
            this.httpRequester = new JavaGetConnectionRequester();
            this.cacheBuster = false;
            this.anonymizingIp = null;
        }

        public MpClientBuilder(String trackingId, String clientId, AppendingDecorator appendingDecorator,
                               HttpRequester httpRequester, boolean cacheBuster, Boolean anonymizingIp) {
            this.trackingId = trackingId;
            this.clientId = clientId;
            this.appendingDecorator = appendingDecorator;
            this.httpRequester = httpRequester;
            this.cacheBuster = cacheBuster;
            this.anonymizingIp = anonymizingIp;
        }

        public MpClientBuilder withCacheBuster() {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, true, anonymizingIp);
        }

        public MpClientBuilder anonymizingIp() {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, cacheBuster, true);
        }

        public MpClient create() {
            MpClient client = new MpClient(this);
            return client;
        }

        public MpClientBuilder using(Decorating decorating) {
            AppendingDecorator localDecorator = new AppendingDecorator(appendingDecorator, decorating);
            return new MpClientBuilder(trackingId, clientId, localDecorator, httpRequester, cacheBuster, anonymizingIp);
        }


        public MpClientBuilder httpRequester(HttpRequester httpRequester) {
            return new MpClientBuilder(trackingId, clientId, appendingDecorator, httpRequester, cacheBuster, anonymizingIp);
        }
    }

    private class CacheBuster implements Decorating {

        private final String text;

        public CacheBuster() {
            int token = new Random().nextInt();
            this.text = "&z=" + token;
        }

        @Override
        public String getPart() {
            return text;
        }
    }
}
