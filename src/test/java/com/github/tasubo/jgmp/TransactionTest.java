package com.github.tasubo.jgmp;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.junit.Assert.assertThat;

public class TransactionTest {

    @Before
    public void clearRequestLog() {
        getRequestLog().clear();
    }

    @Test
    public void shouldSendTransactionMessage() {

        MpClient client = prepareMpClient();

        Transaction transaction = Transaction.withId("OD564")
                .affiliation("Member")
                .revenue(new BigDecimal("15.47"))
                .shipping(new BigDecimal("3.50"))
                .tax(new BigDecimal("10.20"))
                .currencyCode("EUR")
                .create();

        client.send(transaction);

        assertThat(getRequestLog().last(), hasParam("t").withValue("transaction"));
        assertThat(getRequestLog().last(), hasParam("ti").withValue("OD564"));
        assertThat(getRequestLog().last(), hasParam("ta").withValue("Member"));
        assertThat(getRequestLog().last(), hasParam("tr").withValue("15.47"));
        assertThat(getRequestLog().last(), hasParam("ts").withValue("3.50"));
        assertThat(getRequestLog().last(), hasParam("tt").withValue("10.20"));
        assertThat(getRequestLog().last(), hasParam("cu").withValue("EUR"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitId() {
        Transaction.withId(stringWithLength(501))
                .create();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitAffiliation() {
        Transaction.withId("OD564")
                .affiliation(stringWithLength(501))
                .create();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitCurrencyCode() {
        Transaction.withId("OD564")
                .currencyCode(stringWithLength(11))
                .create();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRequireNotEmptyTransactionID() {
        Transaction.withId("");
    }
}
