package com.github.tasubo.jgmp;

import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.hasParam;
import static org.junit.Assert.assertThat;

public class DocumentTest {

    @Test
    public void shouldSendDocumentInfo() {
        MpClient mp = prepareMpClient();
        Sendable sendable = prepareSendable();

        Document document = Document.with()
                .location("http://foo.com/home?a=b")
                .hostname("foo.com")
                .path("/foo")
                .title("Settings")
                .description("High Scores")
                .create();

        mp.send(document.with(sendable));


        assertThat(getRequestLog().last(), hasParam("dl").withBareValue("http%3A%2F%2Ffoo.com%2Fhome%3Fa%3Db"));
        assertThat(getRequestLog().last(), hasParam("dh").withBareValue("foo.com"));
        assertThat(getRequestLog().last(), hasParam("dp").withBareValue("%2Ffoo"));
        assertThat(getRequestLog().last(), hasParam("dt").withBareValue("Settings"));
        assertThat(getRequestLog().last(), hasParam("cd").withBareValue("High%20Scores"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void locationLimit() {
        Document.with().location(stringWithLength(2049));
    }

    @Test(expected = IllegalArgumentException.class)
    public void hostnameLimit() {
        Document.with().hostname(stringWithLength(101));
    }

    @Test(expected = IllegalArgumentException.class)
    public void pathLimit() {
        Document.with().path(stringWithLength(2049));
    }

    @Test(expected = IllegalArgumentException.class)
    public void titleLimit() {
        Document.with().title(stringWithLength(1501));
    }

    @Test(expected = IllegalArgumentException.class)
    public void descriptionLimit() {
        Document.with().description(stringWithLength(2049));
    }
}
