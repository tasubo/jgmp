package com.github.tasubo.jgmp;

import org.junit.Before;
import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class PerformanceTest {

    private MpClient mpClient;
    private Sendable sendable;

    @Before
    public void setUp() {
        mpClient = MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .create();
        sendable = prepareSendable();
    }

    @Test
    public void shouldProcessAtLeast1000HitsPerSecond() {
        int count = 1000;
        long timeStart = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            mpClient.send(sendable);
        }

        long timeEnd = System.currentTimeMillis();
        double lasted = (timeEnd - timeStart + 1) / 1000;
        double countPerSecond = count / lasted;

        System.out.println("countPerSecond: " + countPerSecond);

        assertThat(countPerSecond, greaterThan(1000d));
    }
}
