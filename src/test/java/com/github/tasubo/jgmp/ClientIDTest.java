package com.github.tasubo.jgmp;

import org.junit.Before;
import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.hasParam;
import static com.github.tasubo.jgmp.MpAssert.param;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ClientIDTest {
    @Before
    public void clearRequestLog() {
        getRequestLog().clear();
    }
    
    @Test
    public void clientIdShouldWorkWhenInputIsEmpty() {
        ClientID seeded = ClientID.seeded("", "");
        
        assertThat(seeded.getPart(), is("&cid=d41d8cd9-8f00-b204-e980-0998ecf8427e"));
    }
    
    @Test
    public void clientIdShouldWorkWithLongUserAgentsAndProperIp() {
       String userAgent = "Mozilla/5.0 (X11; Linux x86_64)"
        + " AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.69 Safari/537.17";
       String ip = "129.215.58.48";
       
        ClientID seeded = ClientID.seeded(ip, userAgent);
    }
    
    @Test
    public void clientIdShouldWorkOnNullInput() {
        ClientID seeded = ClientID.seeded(null, null);
        
        assertThat(seeded.getPart(), is("&cid=d41d8cd9-8f00-b204-e980-0998ecf8427e"));
    }

    @Test
    public void shouldCreateClientIdFromSeededValues() {
        ClientID seeded = ClientID.seeded("192.168.0.1", "Mozilla 22");

        assertThat(seeded.getPart(), is("&cid=338f7f94-e46c-18f7-bdb9-41e25287f6f8"));

        Ensure.isUuid(seeded.getPart().substring(5));
    }

    @Test
    public void shouldSendHitWithSeededClientId() {
        Sendable sendable = prepareSendable();
        MpClient mp = prepareMpClientWithoutClientID();

        ClientID seeded = ClientID.seeded("192.168.0.1", "Mozilla 22");
        mp.send(sendable.with(seeded));

        assertThat(getRequestLog().last(), param("cid").appearsOnce());
        assertThat(getRequestLog().last(), hasParam("cid").withValue("338f7f94-e46c-18f7-bdb9-41e25287f6f8"));
    }
}
