package com.github.tasubo.jgmp;

import org.junit.Before;
import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.junit.Assert.assertThat;

public class AppTest {

    @Before
    public void clearRequestLog() {
        getRequestLog().clear();
    }

    @Test
    public void shouldSendAppInfo() {
        MpClient mp = prepareMpClient();
        Sendable sendable = prepareSendable();

        App app = App.named("My App")
                .version("1.2")
                .create();

        mp.send(sendable.with(app));


        assertThat(getRequestLog().last(), hasParam("an").withValue("My App"));
        assertThat(getRequestLog().last(), hasParam("av").withValue("1.2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void versionNotLongerThan100Bytes() {
        App.named("My App").version(stringWithLength(101));
    }

    @Test(expected = IllegalArgumentException.class)
    public void appNameNotLongerThan100Bytes() {
        App.named(stringWithLength(101));
    }
}
