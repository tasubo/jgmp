package com.github.tasubo.jgmp;

import org.junit.Before;
import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class EventTest {

    @Before
    public void clearRequestLog() {
        getRequestLog().clear();
    }

    @Test
    public void shouldSendEvent() {
        MpClient mp = prepareMpClient();

        mp.send(Event.ofCategory("Category").withAction("ButtonClick").labeled("Label").withValue(3).create());

        assertThat(getRequestLog().last(), hasParam("t").withValue("event"));
        assertThat(getRequestLog().last(), hasParam("tid").withValue("UA-XXXX-Y"));
        assertThat(getRequestLog().last(), hasParam("cid").withValue("35009a79-1a05-49d7-b876-2b884d0f825b"));
    }
	
    @Test
    public void shouldSendEventWithoutLabel() {
        MpClient mp = prepareMpClient();

        mp.send(Event.ofCategory("Category").withAction("ButtonClick").withValue(3).create());

		assertThat(getRequestLog().last(), hasNoParam("el"));
		
    }

    @Test
    public void shouldSendEventWithCorrectParameters() {
        MpClient mp = Mocks.prepareMpClient();
        assertThat(mp, notNullValue());

        mp.send(Event.ofCategory("Category").withAction("ButtonClick").labeled("Label").create());

        assertThat(getRequestLog().last(), hasParam("ec").withValue("Category"));
        assertThat(getRequestLog().last(), hasParam("el").withValue("Label"));
        assertThat(getRequestLog().last(), hasParam("ea").withValue("ButtonClick"));

        mp.send(Event.ofCategory("Category").withAction("ButtonClick").labeled("Label").withValue(3).create());
        assertThat(getRequestLog().last(), hasParam("ev").withValue("3"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitActionLength() {
		Event.ofCategory("Category").withAction(stringWithLength(501)).labeled("Label").create();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitCategoryLength() {
        Event.ofCategory(stringWithLength(151)).withAction("ButtonClick").labeled("Label").withValue(3).create();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitLabelLength() {
        Event.ofCategory("Category").withAction("ButtonClick").labeled(stringWithLength(501)).withValue(3).create();
    }

}
