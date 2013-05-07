package com.github.tasubo.jgmp;

import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.getRequestLog;
import static com.github.tasubo.jgmp.Mocks.prepareMpClient;
import static com.github.tasubo.jgmp.MpAssert.hasParam;
import static org.junit.Assert.assertThat;

public class PageViewTest {

    @Test
    public void shouldSendTransactionMessage() {

        MpClient client = prepareMpClient();

        PageView pageView = PageView.hit();

        client.send(pageView);

        assertThat(getRequestLog().last(), hasParam("t").withValue("pageview"));

    }
}
