package com.github.tasubo.jgmp;

import org.junit.Before;
import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.junit.Assert.assertThat;

public class TimingTest {

    @Before
    public void clearRequestLog() {
        getRequestLog().clear();
    }

    @Test
    public void shouldSendUserTimingHit() {
        MpClient client = prepareMpClient();

        UserTiming timing = Timing.user()
                .category("category")
                .name("lookup")
                .label("label")
                .time(123)
                .create();

        client.send(timing);

        assertThat(getRequestLog().last(), hasParam("t").withValue("timing"));
        assertThat(getRequestLog().last(), hasParam("utc").withValue("category"));
        assertThat(getRequestLog().last(), hasParam("utv").withValue("lookup"));
        assertThat(getRequestLog().last(), hasParam("utt").withValue("123"));
        assertThat(getRequestLog().last(), hasParam("utl").withValue("label"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitUserTimingLabel() {
        Timing.user().label(stringWithLength(501));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitUserTimingCategory() {
        Timing.user().category(stringWithLength(151));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitUserTimingName() {
        Timing.user().name(stringWithLength(501));
    }


    @Test
    public void shouldSendPageLoadTimeHit() {
        MpClient client = prepareMpClient();

        Timing timing = Timing.pageLoad(3554);

        client.send(timing);

        assertThat(getRequestLog().last(), hasParam("t").withValue("timing"));
        assertThat(getRequestLog().last(), hasParam("plt").withValue("3554"));
    }

    @Test
    public void shouldSendDnsLookupTimeHit() {
        MpClient client = prepareMpClient();

        Timing timing = Timing.dnsLookup(43);

        client.send(timing);

        assertThat(getRequestLog().last(), hasParam("t").withValue("timing"));
        assertThat(getRequestLog().last(), hasParam("dns").withValue("43"));
    }

    @Test
    public void shouldSendPageDownloadTimeHit() {
        MpClient client = prepareMpClient();

        Timing timing = Timing.pageDownload(500);

        client.send(timing);

        assertThat(getRequestLog().last(), hasParam("t").withValue("timing"));
        assertThat(getRequestLog().last(), hasParam("pdt").withValue("500"));
    }

    @Test
    public void shouldSendRedirectResponseTimeHit() {
        MpClient client = prepareMpClient();

        Timing timing = Timing.redirectResponse(500);

        client.send(timing);

        assertThat(getRequestLog().last(), hasParam("t").withValue("timing"));
        assertThat(getRequestLog().last(), hasParam("rrt").withValue("500"));
    }

    @Test
    public void shouldSendTcpConnectTimeHit() {
        MpClient client = prepareMpClient();

        Timing timing = Timing.tcpConnect(500);

        client.send(timing);

        assertThat(getRequestLog().last(), hasParam("t").withValue("timing"));
        assertThat(getRequestLog().last(), hasParam("tcp").withValue("500"));
    }

    @Test
    public void shouldSendServerResponseTimeHit() {
        MpClient client = prepareMpClient();

        Timing timing = Timing.serverResponse(500);

        client.send(timing);

        assertThat(getRequestLog().last(), hasParam("t").withValue("timing"));
        assertThat(getRequestLog().last(), hasParam("srt").withValue("500"));
    }

    @Test
    public void shouldBeAbleToCombineTimings() {
        MpClient client = prepareMpClient();

        Timing serverResponse = Timing.serverResponse(500);
        Timing dnsLookup = Timing.dnsLookup(43);

        client.send(serverResponse.and(dnsLookup));

        assertThat(getRequestLog().last(), hasParam("t").withValue("timing"));
        assertThat(getRequestLog().last(), param("t").appearsOnce());
        assertThat(getRequestLog().last(), hasParam("srt").withValue("500"));
        assertThat(getRequestLog().last(), hasParam("dns").withValue("43"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotHaveDuplicateTimingValues() {
        MpClient client = prepareMpClient();

        Timing dnsLookupFirst = Timing.dnsLookup(500);
        Timing dnsLookup = Timing.dnsLookup(43);

        client.send(dnsLookupFirst.and(dnsLookup));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotHaveDuplicateTimingValues_hierarchical() {
        MpClient client = prepareMpClient();

        Timing first = Timing.dnsLookup(500).and(Timing.pageDownload(2));
        Timing second = Timing.dnsLookup(43).and(Timing.pageLoad(3));

        client.send(first.and(second));
    }
}
