package com.github.tasubo.jgmp;

import org.junit.Test;

import static com.github.tasubo.jgmp.MpAssert.*;
import static com.github.tasubo.jgmp.Mocks.*;
import static org.junit.Assert.assertThat;

public class CacheBusterTest {

    @Test
    public void shouldAppendDifferentArgsAtTheEnd() {
        MpClient client = MpClient.withTrackingId("TRACKING")
                .withClientId("clientId")
                .withCacheBuster()
                .httpRequester(new MockHttpRequester())
                .create();

        Sendable sendable = prepareSendable();

        client.send(sendable);
        client.send(sendable);

        assertThat(getRequestLog().last(), param("z").isPresent());
        assertThat(getRequestLog().last(), param("z").isLast());
        assertThat(getRequestLog().tenLast(), params("z").allDiffer());
    }

}