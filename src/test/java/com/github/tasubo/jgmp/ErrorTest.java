package com.github.tasubo.jgmp;

import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.junit.Assert.assertThat;

public class ErrorTest {

    @Test
    public void shouldBeAbleToSendExceptionHit() {

        MpClient client = prepareMpClient();
        Error error = Error.withDescription("DatabaseError")
                .fatal()
                .create();

        client.send(error);


        assertThat(getRequestLog().last(), hasParam("exd").withValue("DatabaseError"));
        assertThat(getRequestLog().last(), hasParam("exf").withValue("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitUserTimingLabel() {
        Error.withDescription(stringWithLength(151));
    }
}
