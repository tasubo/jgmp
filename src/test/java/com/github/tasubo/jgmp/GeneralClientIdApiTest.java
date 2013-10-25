package com.github.tasubo.jgmp;

import org.junit.Before;
import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.hasParam;
import static com.github.tasubo.jgmp.MpAssert.param;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class GeneralClientIdApiTest {

    @Before
    public void clearRequestLog() {
        getRequestLog().clear();
    }

    @Test
    public void canCreateMpClientWithoutSpecifyingClientId() {
        MpClient mp = MpClient.withTrackingId("UA-XXXX-Y")
                .create();
    }

    @Test
    public void clientIdCouldBeSpecifiedDuringSending() {
        Sendable sendable = prepareSendable();

        MpClient mp = prepareMpClientWithoutClientID();

        ClientID clientId = ClientID.random();
        mp.send(sendable.with(clientId));

        assertThat(getRequestLog().last(), param("cid").appearsOnce());
        assertThat(getRequestLog().last(), not(hasParam("cid").withValue("35009a79-1a05-49d7-b876-2b884d0f825b")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailOnDoubleClientIdSetting() {
        Sendable sendable = prepareSendable();

        MpClient mp = prepareMpClientWithoutClientID();

        ClientID clientId = ClientID.random();
        mp.send(sendable.with(clientId).with(clientId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void specifyingDoubleClientIdShouldOverride() {
        Sendable sendable = prepareSendable();

        MpClient mp = prepareMpClient();

        ClientID clientId = ClientID.random();
        mp.send(sendable.with(clientId));

    }
}
