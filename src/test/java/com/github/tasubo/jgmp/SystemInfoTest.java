package com.github.tasubo.jgmp;

import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.hasParam;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class SystemInfoTest {

    @Test
    public void shouldBeAbleToSendSystemInfoEvents() {

        MpClient mpClient = prepareMpClient();
        Sendable sendable = prepareSendable();

        SystemInfo systemInfo = SystemInfo.with()
                .screenResolution(800, 600)
                .viewport(640, 480)
                .documentEncoding("UTF-8")
                .colorBits(24)
                .userLanguage("en-us")
                .javaEnabled()
                .flashVersion("10 1 r103")
                .create();

        mpClient.send(systemInfo.with(sendable));

        assertThat(getRequestLog().last(), hasParam("ec").withValue("Category"));


        assertThat(getRequestLog().last(), hasParam("sr").withValue("800x600"));
        assertThat(getRequestLog().last(), hasParam("vp").withValue("640x480"));
        assertThat(getRequestLog().last(), hasParam("de").withValue("UTF-8"));
        assertThat(getRequestLog().last(), hasParam("sd").withValue("24-bits"));
        assertThat(getRequestLog().last(), hasParam("ul").withValue("en-us"));
        assertThat(getRequestLog().last(), hasParam("je").withValue("1"));
        assertThat(getRequestLog().last(), hasParam("fl").withValue("10 1 r103"));
    }

    @Test
    public void shouldSkipNullFilledValues() {

        MpClient mpClient = prepareMpClient();
        Sendable sendable = prepareSendable();

        SystemInfo systemInfo = SystemInfo.with()
                .screenResolution(800, 600)
                .documentEncoding("UTF-8")
                .flashVersion("10 1 r103")
                .create();

        mpClient.send(systemInfo.with(sendable));

        assertThat(getRequestLog().last(), hasParam("ec").withValue("Category"));


        assertThat(getRequestLog().last(), not(hasParam("vp")));
        assertThat(getRequestLog().last(), not(hasParam("sd")));
        assertThat(getRequestLog().last(), not(hasParam("ul")));
        assertThat(getRequestLog().last(), not(hasParam("je")));
    }


    @Test(expected = IllegalArgumentException.class)
    public void paramValidateDocumentEncoding() {
        SystemInfo.with()
                .documentEncoding(stringWithLength(21))
                .create();

    }

    @Test(expected = IllegalArgumentException.class)
    public void paramValidateUserLanguage() {
        SystemInfo.with()
                .userLanguage(stringWithLength(21))
                .create();

    }

    @Test(expected = IllegalArgumentException.class)
    public void paramFlashVersion() {
        SystemInfo.with()
                .flashVersion(stringWithLength(21))
                .create();

    }
}
