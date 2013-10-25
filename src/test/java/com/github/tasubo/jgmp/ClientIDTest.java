package com.github.tasubo.jgmp;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ClientIDTest {
    @Test
    public void shouldCreateClientIdFromSeededValues() {
        ClientID seeded = ClientID.seeded("192.168.0.1", "Mozilla 22");

        assertThat(seeded.getPart(), is("&cid=338f7f94-e46c-18f7-bdb9-41e25287f6f8"));
    }
}
