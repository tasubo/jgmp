package com.github.tasubo.jgmp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.hasParam;
import static com.github.tasubo.jgmp.MpAssert.param;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class GeneralApiTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldGetClient() {
        MpClient mp = MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .withCacheBuster()
                .anonymizingIp()
                .create();

        assertThat(mp, notNullValue());
    }

    @Test
    public void shouldSendSimpleMessage() {
        Sendable sendable = prepareSendable();
        MpClient mp = prepareMpClient();

        mp.send(sendable);

        assertThat(getRequestLog().last(), notNullValue());
        assertThat(getRequestLog().last(), startsWith("http://www.google-analytics.com/collect?"));
        assertThat(getRequestLog().last(), hasParam("v"));
        assertThat(getRequestLog().last(), hasParam("v").withValue("1"));
        assertThat(getRequestLog().last(), not(hasParam("ni")));
    }

    @Test
    public void shouldDecorateGlobalParams() {

        Campaign campaign = prepareCampaign();
        SystemInfo systemInfo = prepareSystemInfo();

        MpClient mp = MpClient.withTrackingId("").withClientId("")
                .httpRequester(new MockHttpRequester())
                .using(campaign)
                .using(systemInfo)
                .create();

        Sendable sendable = prepareSendable();
        mp.send(sendable);


        assertThat(getRequestLog().last(), hasParam("vp").withValue("800x600"));
        assertThat(getRequestLog().last(), hasParam("cn").withValue("MockCampaign"));
    }

    @Test
    public void shouldDecorateGlobalParamsOnce() {

        SystemInfo systemInfo = prepareSystemInfo();

        MpClient mp = MpClient.withTrackingId("").withClientId("")
                .httpRequester(new MockHttpRequester())
                .using(systemInfo)
                .using(systemInfo)
                .create();

        Sendable sendable = prepareSendable();
        mp.send(sendable);


        assertThat(getRequestLog().last(), param("vp").appearsOnce());
    }

    @Test
    public void shouldSendNonInteractive() {
        MpClient mp = prepareMpClient();
        Sendable sendable = prepareSendable();
        mp.sendNonInteractive(sendable);

        assertThat(getRequestLog().last(), hasParam("ni").withValue("1"));
    }


}
