package com.github.tasubo.jgmp;

import org.junit.Before;
import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.getRequestLog;
import static com.github.tasubo.jgmp.Mocks.prepareMpClient;
import static com.github.tasubo.jgmp.MpAssert.hasParam;
import static org.junit.Assert.assertThat;

public class AppViewTest {

    @Before
    public void clearRequestLog() {
        getRequestLog().clear();
    }

    @Test
    public void shouldSendTransactionMessage() {

        MpClient client = prepareMpClient();

        AppView appView = AppView.hit();

        client.send(appView);

        assertThat(getRequestLog().last(), hasParam("t").withValue("appview"));

    }
}
