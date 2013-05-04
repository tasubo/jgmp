package com.github.tasubo.jgmp;


import org.junit.Test;

public class GoogleEndpointIntegrationTest {

    private final String clientId = "82be3540-b4b5-11e2-9e96-0800200c9a66";

    @Test
    public void shouldSendHitToGoogle() {
        MpClient mpClient = MpClient.withTrackingId("UA-40659159-1")
                .withClientId(clientId)
                .withCacheBuster()
                .create();

        App app = App.named("jGMP integration test").create();

        mpClient.send(app.with(Event.of("Test", "Integration").action("testhit")));
    }

    @Test
    public void shouldSendClientInformation() {
        App app = App.named("jGMP integration test")
                .version("0.1337")
                .create();

        SystemInfo systemInfo = SystemInfo.with()
                .colorBits(24)
                .screenResolution(800, 600)
                .userLanguage("lt_LT")
                .documentEncoding("UTF-8")
                .javaEnabled()
                .create();

        MpClient mpClient = MpClient.withTrackingId("UA-40659159-1")
                .withClientId(clientId)
                .withCacheBuster()
                .using(systemInfo)
                .using(app)
                .create();

        mpClient.send(Timing.pageLoad(23));
    }

    @Test
    public void shouldSendAnotherExample() {
        App app = App.named("jGMP integration test")
                .version("0.8008")
                .create();

        MpClient mpClient = MpClient.withTrackingId("UA-40659159-1")
                .withClientId(clientId)
                .withCacheBuster()
                .using(app)
                .create();

        Decorating referrer = Referrer.from("http://localhost/");
        UserTiming userTiming = Timing.user().name("test").time(4).create();

        mpClient.send(referrer.with(userTiming));
    }
}
