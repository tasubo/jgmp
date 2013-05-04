package com.github.tasubo.jgmp;


import java.util.Random;

public class MpClient {
    private String trackingId;
    private String clientId;
    private Boolean cacheBuster = false;
    private Boolean anonymizingIp = null;
    private HttpRequester httpRequester;
    private Decorating wrapperSendable;

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

    public static MpClientBuilder withTrackingId(String trackingId) {
        return new MpClientBuilder(trackingId);
    }

    public void send(Sendable sendable) {
        if (cacheBuster) {
            sendable = new CacheBusterWrapper(sendable);
        }
        Parametizer parametizer = new Parametizer("tid", trackingId, "cid", clientId, "anonymizingIp", anonymizingIp);
        Sendable with = wrapperSendable.with(sendable);
        httpRequester.send("http://www.google-analytics.com/collect?v=1" + parametizer.getText() + with.getText());
    }

    public void sendNonInteractive(Sendable sendable) {
        Sendable niSendable = new NonInteractiveDecorator(sendable);
        send(niSendable);
    }

    public static class MpClientBuilder {
        private String trackingId;
        private AppendingDecorator wrapperSendable = new AppendingDecorator();
        private HttpRequester httpRequester = new JavaConnectionRequester();
        private String clientId;
        private boolean cacheBuster;
        private Boolean anonymizingIp;

        private MpClientBuilder(String trackingId) {
            this.trackingId = trackingId;
        }

        public MpClientBuilder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public MpClientBuilder withCacheBuster() {
            this.cacheBuster = true;
            return this;
        }

        public MpClientBuilder anonymizingIp() {
            this.anonymizingIp = true;
            return this;
        }

        public MpClient create() {
            MpClient client = new MpClient();
            client.trackingId = trackingId;
            client.httpRequester = httpRequester;
            client.anonymizingIp = anonymizingIp;
            client.cacheBuster = cacheBuster;
            client.clientId = clientId;
            client.wrapperSendable = wrapperSendable;
            return client;
        }

        public MpClientBuilder using(Decorating decorating) {
                wrapperSendable.append(decorating);
            return this;
        }


        public MpClientBuilder httpRequester(HttpRequester httpRequester) {
            this.httpRequester = httpRequester;
            return this;
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
