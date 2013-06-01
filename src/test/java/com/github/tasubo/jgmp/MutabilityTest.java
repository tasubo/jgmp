package com.github.tasubo.jgmp;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.mutabilitydetector.MutableReasonDetail;

import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

public class MutabilityTest {
    @Test
    public void clientShouldBeImmutable() {
        assertImmutable(AppendingDecorator.class);
        Matcher<MutableReasonDetail> alsoImmutable
                = provided(
                HttpRequester.class, MpClient.Prefix.class,
                MpClient.Sender.class, Decorating.class
        ).isAlsoImmutable();
        assertInstancesOf(MpClient.class,
                areImmutable(),
                alsoImmutable);
    }

    @Test
    public void clientBuilderShouldBeImmutable() {
        Matcher<MutableReasonDetail> alsoImmutable
                = provided(
                HttpRequester.class, MpClient.Prefix.class,
                MpClient.Sender.class, MpClient.Sender.GET.class,
                Decorating.class
        ).isAlsoImmutable();

        assertInstancesOf(MpClient.MpClientBuilder.class,
                areImmutable(),
                alsoImmutable);
    }

    @Test
    public void appShouldBeImmutable() {
        assertImmutable(App.class);
        assertImmutable(App.AppBuilder.class);
    }

    @Test
    public void appviewShouldBeImmutable() {
        assertImmutable(AppView.class);
    }

    @Test
    public void pageviewShouldBeImmutable() {
        assertImmutable(PageView.class);
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

    @Test
    public void itemShouldBeImmutable() {
        assertImmutable(Item.class);
        assertImmutable(Item.ItemBuilder.class);
        assertImmutable(Item.ItemBuilderStart.class);
    }
}
