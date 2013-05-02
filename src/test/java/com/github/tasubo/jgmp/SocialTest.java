package com.github.tasubo.jgmp;

import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.junit.Assert.assertThat;

public class SocialTest {

    @Test
    public void shouldSendSocialMessage() {

        MpClient client = prepareMpClient();

        Social social = Social.fromNetwork("facebook")
                .action("like")
                .target("http://foo.com");

        client.send(social);

        assertThat(getRequestLog().last(), hasParam("sn").withValue("facebook"));
        assertThat(getRequestLog().last(), hasParam("sa").withValue("like"));
        assertThat(getRequestLog().last(), hasParam("st").withBareValue("http%3A%2F%2Ffoo.com"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitNetwork() {
        Social.fromNetwork(stringWithLength(51));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitAction() {
        Social.fromNetwork("facebook").action(stringWithLength(51));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitTarget() {
        Social.fromNetwork("facebook").action("like").target(stringWithLength(2049));
    }
}
