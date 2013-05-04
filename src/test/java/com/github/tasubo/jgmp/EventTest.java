package com.github.tasubo.jgmp;

import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class EventTest {

    @Test
    public void shouldSendEvent() {
        MpClient mp = prepareMpClient();

        mp.send(Event.of("Category", "Label").action("ButtonClick", 3));

        assertThat(getRequestLog().last(), hasParam("t").withValue("event"));
        assertThat(getRequestLog().last(), hasParam("tid").withValue("UA-XXXX-Y"));
        assertThat(getRequestLog().last(), hasParam("cid").withValue("35009a79-1a05-49d7-b876-2b884d0f825b"));
    }


    @Test
    public void shouldSendEventWithCorrectParameters() {
        MpClient mp = Mocks.prepareMpClient();
        assertThat(mp, notNullValue());

        mp.send(Event.of("Category", "Label").action("ButtonClick"));

        assertThat(getRequestLog().last(), hasParam("ec").withValue("Category"));
        assertThat(getRequestLog().last(), hasParam("el").withValue("Label"));
        assertThat(getRequestLog().last(), hasParam("ea").withValue("ButtonClick"));

        mp.send(Event.of("Category", "Label").action("ButtonClick", 3));
        assertThat(getRequestLog().last(), hasParam("ev").withValue("3"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitActionLength() {
        Event.of("Category", "Label").action(stringWithLength(501));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitCategoryLength() {
        Event.of(stringWithLength(151), "Label");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitLabelLength() {
        Event.of("Category", stringWithLength(501));
    }

}
