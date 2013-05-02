package com.github.tasubo.jgmp;

import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.junit.Assert.assertThat;

public class SessionTest {


    @Test
    public void shouldStartSession() {

        MpClient mp = prepareMpClient();
        Sendable sendable = prepareSendable();

        Session session = Session.start();

        mp.send(session.with(sendable));

        assertThat(getRequestLog().last(), hasParam("sc").withValue("start"));
    }

    @Test
    public void shouldEndSession() {

        MpClient mp = prepareMpClient();
        Sendable sendable = prepareSendable();

        Session session = Session.end();

        mp.send(session.with(sendable));

        assertThat(getRequestLog().last(), hasParam("sc").withValue("end"));
    }

}
