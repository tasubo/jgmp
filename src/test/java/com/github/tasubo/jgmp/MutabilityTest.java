package com.github.tasubo.jgmp;

import org.junit.Test;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

public class MutabilityTest {
    @Test
    public void appShouldBeImmutable() {
        assertImmutable(App.class);
        assertImmutable(App.AppBuilder.class);
    }

    @Test
    public void eventShouldBeImmutable() {
        assertImmutable(Event.class);
        assertImmutable(Event.EventBuilder.class);
    }

    @Test
    public void campaignShouldBeImmutable() {
        assertImmutable(Campaign.class);
        assertImmutable(Campaign.CampaignBuilder.class);
    }


    @Test
    public void dimensionShouldBeImmutable() {
        assertImmutable(Dimension.class);
        assertImmutable(Dimension.DimensionBuilder.class);
    }

    @Test
    public void documentShouldBeImmutable() {
        assertImmutable(Document.class);
        assertImmutable(Document.DocumentBuilder.class);
    }

    @Test
    public void errorShouldBeImmutable() {
        assertImmutable(Error.class);
        assertImmutable(Error.ErrorBuilder.class);
    }

    @Test
    public void metricShouldBeImmutable() {
        assertImmutable(Metric.class);
        assertImmutable(Metric.MetricBuilder.class);
    }

    @Test
    public void referrerShouldBeImmutable() {
        assertImmutable(Referrer.class);
    }

    @Test
    public void sessionShouldBeImmutable() {
        assertImmutable(Session.class);
    }

    @Test
    public void socialShouldBeImmutable() {
        assertImmutable(Social.class);
        assertImmutable(Social.SocialBuilder.class);
    }

    @Test
    public void systemInfoShouldBeImmutable() {
        assertImmutable(SystemInfo.class);
        assertImmutable(SystemInfo.SystemInfoBuilder.class);
    }

    @Test
    public void timingShouldBeImmutable() {
        assertImmutable(Timing.class);
        assertImmutable(UserTiming.class);
        assertImmutable(Timing.UserTimingBuilder.class);
    }

    @Test
    public void transactionShouldBeImmutable() {
        assertImmutable(Transaction.class);
        assertImmutable(Transaction.TransactionBuilder.class);
    }
}
