package com.github.tasubo.jgmp;


import org.junit.Test;

public class GoogleEndpointIntegrationTest {
    @Test
    public void shouldSendHitToGoogle() {
        MpClient mpClient = MpClient.withTrackingId("UA-40659159-1")
                .withClientId("82be3540-b4b5-11e2-9e96-0800200c9a66")
                .withCacheBuster()
                .create();

        App app = App.named("jGMP integration test").create();

        mpClient.send(app.with(Event.of("Test", "Integration").action("testhit")));
    }
}
