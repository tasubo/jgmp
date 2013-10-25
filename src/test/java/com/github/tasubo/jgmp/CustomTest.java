package com.github.tasubo.jgmp;

import org.junit.Before;
import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.junit.Assert.assertThat;

public class CustomTest {

    @Before
    public void clearRequestLog() {
        getRequestLog().clear();
    }

    @Test
    public void shouldSendCustomMetricHit() {
        MpClient client = prepareMpClient();
        Sendable sendable = prepareSendable();

        Custom custom = Metric.withIndex(19).value(47);

        client.send(sendable.with(custom));

        assertThat(getRequestLog().last(), hasParam("t").withValue("event"));
        assertThat(getRequestLog().last(), hasParam("cm19").withValue("47"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitMetricIndexSize() {
        Metric.withIndex(201);
    }


    @Test
    public void shouldSendCustomDimensionHit() {
        MpClient client = prepareMpClient();
        Sendable sendable = prepareSendable();

        Custom custom = Dimension.withIndex(160).value("Sports");

        client.send(sendable.with(custom));

        assertThat(getRequestLog().last(), hasParam("t").withValue("event"));
        assertThat(getRequestLog().last(), hasParam("cd160").withValue("Sports"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitDimensionIndexSize() {
        Dimension.withIndex(201);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitDimensionValueSize() {
        Dimension.withIndex(200).value(stringWithLength(151));
    }
}
