package com.github.tasubo.jgmp;

import org.junit.Before;
import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.hasParam;
import static com.github.tasubo.jgmp.MpAssert.param;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GeneralApiTest {

    @Before
    public void clearRequestLog() {
        getRequestLog().clear();
    }

    @Test
    public void shouldGetClient() {
        MpClient mp = MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .withCacheBuster()
                .noCacheBuster()
                .anonymizingIp()
                .noAnonymizingIp()
                .usePost()
                .useSsl()
                .usePlainHttp()
                .useGet()
                .create();

        assertThat(mp, notNullValue());
    }

    @Test
    public void shouldUseSsl() {
        Sendable sendable = prepareSendable();
        MpClient mp = MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .httpRequester(new MockHttpRequester())
                .useSsl()
                .create();

        mp.send(sendable);

        assertThat(getRequestLog().last(), startsWith("https://www.google-analytics.com/collect?"));
    }

    @Test
    public void shouldUsePlainOnRevert() {
        Sendable sendable = prepareSendable();
        MpClient mp = MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .httpRequester(new MockHttpRequester())
                .useSsl()
                .usePlainHttp()
                .create();

        mp.send(sendable);

        assertThat(getRequestLog().last(), startsWith("http://www.google-analytics.com/collect?"));
    }

    @Test
    public void shouldBeAbleToUsePost() {

        HttpRequester requester = mock(HttpRequester.class);

        Sendable sendable = prepareSendable();
        MpClient mp = MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .httpRequester(requester)
                .usePost()
                .create();

        mp.send(sendable);

        verify(requester).sendPost(anyString(), anyString());
    }

    @Test
    public void shouldUseGetAfterChangingItBackAfterPost() {

        HttpRequester requester = mock(HttpRequester.class);

        Sendable sendable = prepareSendable();
        MpClient mp = MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .httpRequester(requester)
                .usePost()
                .useGet()
                .create();

        mp.send(sendable);

        verify(requester).sendGet(anyString(), anyString());
        verify(requester, never()).sendPost(anyString(), anyString());
    }


    @Test
    public void shouldSendSimpleMessage() {
        Sendable sendable = prepareSendable();
        MpClient mp = prepareMpClient();

        mp.send(sendable);

        assertThat(getRequestLog().last(), notNullValue());
        assertThat(getRequestLog().last(), startsWith("http://www.google-analytics.com/collect?"));
        assertThat(getRequestLog().last(), hasParam("v"));
        assertThat(getRequestLog().last(), hasParam("cid").withValue("35009a79-1a05-49d7-b876-2b884d0f825b"));
        assertThat(getRequestLog().last(), hasParam("v").withValue("1"));
        assertThat(getRequestLog().last(), not(hasParam("ni")));
    }

    @Test
    public void shouldProperlyEncodeUtf() {
        Sendable sendable = prepareSendable();
        MpClient mp = prepareMpClient();

        Document document = Document.with()
                .description("ąčęėįšųū90-ž%^&*")
                .create();

        mp.send(sendable.with(document));

        assertThat(getRequestLog().last(), hasParam("cd").withBareValue("%C4%85%C4%8D%C4%99%C4%97%C4%AF%C5%A1%C5%B3%C5%AB90-%C5%BE%25%5E%26*"));
    }

    @Test
    public void shouldDecorateGlobalParams() {

        Campaign campaign = prepareCampaign();
        SystemInfo systemInfo = prepareSystemInfo();

        MpClient mp = MpClient.withTrackingId("").withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
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

        MpClient mp = MpClient.withTrackingId("").withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
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
        mp.send(sendable.with(NonInteractive.hit()));

        assertThat(getRequestLog().last(), hasParam("ni").withValue("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldAcceptOnlyValidUUIDForClientId() {
        MpClient.withTrackingId("U-NN-NN")
                .withClientId("something");

    }

    @Test
    public void shouldBeAbleToAnonymizeIp() {
        Sendable sendable = prepareSendable();
        MpClient mp = MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .httpRequester(new MockHttpRequester())
                .anonymizingIp()
                .create();

        mp.send(sendable);

        assertThat(getRequestLog().last(), hasParam("aip").withValue("1"));
    }

    @Test
    public void canTurnOffAnonymization() {
        Sendable sendable = prepareSendable();
        MpClient mp = MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .httpRequester(new MockHttpRequester())
                .anonymizingIp()
                .noAnonymizingIp()
                .create();

        mp.send(sendable);

        assertThat(getRequestLog().last(), not(param("aip").isPresent()));
    }
}
