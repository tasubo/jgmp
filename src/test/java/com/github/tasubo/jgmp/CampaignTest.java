package com.github.tasubo.jgmp;

import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.junit.Assert.assertThat;

public class CampaignTest {


    @Test
    public void shouldSendCampaignEvent() {
        MpClient mp = prepareMpClient();

        Campaign campaign = Campaign.named("Name").source("Source").medium("Medium")
                .keyword("Keyword").content("Content").id("id")
                .adWordsId("adWordsId").displayAdsId("displayAdsId")
                .create();

        Sendable sendable = prepareSendable();

        mp.send(campaign.with(sendable));

        assertThat(getRequestLog().last(), hasParam("cn").withValue("Name"));
        assertThat(getRequestLog().last(), hasParam("cs").withValue("Source"));
        assertThat(getRequestLog().last(), hasParam("cm").withValue("Medium"));
        assertThat(getRequestLog().last(), hasParam("ck").withValue("Keyword"));
        assertThat(getRequestLog().last(), hasParam("cc").withValue("Content"));
        assertThat(getRequestLog().last(), hasParam("ci").withValue("id"));
        assertThat(getRequestLog().last(), hasParam("gclid").withValue("adWordsId"));
        assertThat(getRequestLog().last(), hasParam("dclid").withValue("displayAdsId"));
    }
}
