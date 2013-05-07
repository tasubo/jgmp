package com.github.tasubo.jgmp;

import org.junit.Test;

import java.math.BigDecimal;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.junit.Assert.assertThat;

public class ItemTest {

    @Test
    public void shouldSendItemMessage() {

        MpClient client = prepareMpClient();

        Item item = Item.forTransaction("OD564").named("Shoe")
                .priced(new BigDecimal("3.50"))
                .quantity(4)
                .code("SKU47")
                .category("Blue")
                .currencyCode("EUR")
                .create();

        client.send(item);

        assertThat(getRequestLog().last(), hasParam("t").withValue("item"));
        assertThat(getRequestLog().last(), hasParam("ti").withValue("OD564"));
        assertThat(getRequestLog().last(), hasParam("in").withValue("Shoe"));
        assertThat(getRequestLog().last(), hasParam("ip").withValue("3.50"));
        assertThat(getRequestLog().last(), hasParam("iq").withValue("4"));
        assertThat(getRequestLog().last(), hasParam("ic").withValue("SKU47"));
        assertThat(getRequestLog().last(), hasParam("iv").withValue("Blue"));
        assertThat(getRequestLog().last(), hasParam("cu").withValue("EUR"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitCurrencyCode() {
        Item.forTransaction("OD564").named("Shoe")
                .currencyCode(stringWithLength(11))
                .create();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitName() {
        Item.forTransaction("OD564").named(stringWithLength(501))
                .create();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitCategory() {
        Item.forTransaction("OD564").named("Shoe")
                .category(stringWithLength(501))
                .create();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldLimitItemCode() {
        Item.forTransaction("OD564").named("Shoe")
                .code(stringWithLength(501))
                .create();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRequireTransactionID() {
        Item.forTransaction("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRequireItemName() {
        Item.forTransaction("OD564").named("");
    }

}
