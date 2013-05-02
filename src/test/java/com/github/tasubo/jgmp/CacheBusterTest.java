package com.github.tasubo.jgmp;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.List;

import static com.github.tasubo.jgmp.Mocks.*;
import static org.junit.Assert.assertThat;

public class CacheBusterTest {

    @Test
    public void shouldAppendDifferentArgsAtTheEnd() {
        MpClient client = MpClient.withTrackingId("TRACKING")
                .withClientId("clientId")
                .withCacheBuster()
                .create();

        Sendable sendable = prepareSendable();

        client.send(sendable);
        client.send(sendable);

        assertThat(getRequestLog().last(), param("z").isPresent());
        assertThat(getRequestLog().last(), param("z").isLast());
        assertThat(getRequestLog().tenLast(), params("z").allDiffer());
    }

    private ParamsMatcherBuilder params(String params) {
        throw new UnsupportedOperationException();
    }

    public static ParamMatcherBuilder param(String param) {
        throw new UnsupportedOperationException();
    }

    public static class ParamMatcherBuilder {
        public Matcher<? super String> isLast() {
            throw new UnsupportedOperationException();
        }

        public Matcher<? super String> isPresent() {
            throw new UnsupportedOperationException();
        }
    }

    public class ParamsMatcherBuilder {

        public Matcher<? super List<String>> allDiffer() {
            throw new UnsupportedOperationException();
        }
    }
}