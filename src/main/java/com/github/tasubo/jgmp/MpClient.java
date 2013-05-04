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

    private class NonInteractiveDecorator implements Sendable {
        private final Sendable sendable;

        public NonInteractiveDecorator(Sendable sendable) {
            this.sendable = sendable;
        }

        @Override
        public String getText() {
            return "&ni=1" + sendable.toString();
        }
    }

    public static MpClientBuilderStart withTrackingId(String trackingId) {
        return new MpClientBuilderStart(trackingId);
    }

    public void send(Sendable sendable) {
        if (cacheBuster) {
            sendable = new CacheBusterWrapper(sendable);
        }
        Parametizer parametizer = new Parametizer("tid", trackingId, "cid", clientId, "anonymizingIp", anonymizingIp);
        Sendable with = appendingDecorator.with(sendable);
        httpRequester.send("http://www.google-analytics.com/collect?v=1" + parametizer.getText() + with.getText());
    }

    public void sendNonInteractive(Sendable sendable) {
        Sendable niSendable = new NonInteractiveDecorator(sendable);
        send(niSendable);
    }

    public static final class MpClientBuilderStart {
        private final String trackingId;

        private MpClientBuilderStart(String trackingId) {
            this.trackingId = trackingId;
        }

        public MpClientBuilder withClientId(String clientId) {
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

    private class CacheBusterWrapper implements Sendable {

        private final String text;

        public CacheBusterWrapper(Sendable sendable) {
            int token = new Random().nextInt();
            this.text = sendable.getText() + "&z=" + token;
        }

        @Override
        public String getText() {
            return text;
        }
    }
}
