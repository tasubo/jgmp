package com.github.tasubo.jgmp;

import java.math.BigDecimal;

public final class Item implements Sendable {
    private final Parametizer parametizer;

    public Item(ItemBuilder b) {
        this.parametizer = new Parametizer("t", "item", "ti", b.transactionId,
                "in", b.itemName,
                "ip", b.price,
                "iq", b.quantity,
                "ic", b.code,
                "iv", b.category,
                "cu", b.currencyCode);
    }

    @Override
    public String getText() {
        return parametizer.getText();
    }

    public static ItemBuilderStart forTransaction(String transactionId) {
        Ensure.nonEmpty(transactionId);
        return new ItemBuilderStart(transactionId);
    }

    public static class ItemBuilder {

        private final String transactionId;
        private final String itemName;
        private final BigDecimal price;
        private final Integer quantity;
        private final String code;
        private final String category;
        private final String currencyCode;

        private ItemBuilder(String transactionId, String itemName) {
            this.transactionId = transactionId;
            this.itemName = itemName;
            price = null;
            quantity = null;
            code = null;
            category = null;
            currencyCode = null;
        }

        private ItemBuilder(String transactionId, String itemName, BigDecimal price, Integer quantity, String code, String category, String currencyCode) {
            this.transactionId = transactionId;
            this.itemName = itemName;
            this.price = price;
            this.quantity = quantity;
            this.code = code;
            this.category = category;
            this.currencyCode = currencyCode;
        }

        public ItemBuilder priced(BigDecimal price) {
            return new ItemBuilder(transactionId, itemName, price, quantity, code, category, currencyCode);
        }

        public ItemBuilder quantity(int quantity) {
            return new ItemBuilder(transactionId, itemName, price, quantity, code, category, currencyCode);
        }

        public ItemBuilder code(String code) {
            Ensure.length(500, code);
            return new ItemBuilder(transactionId, itemName, price, quantity, code, category, currencyCode);
        }

        public ItemBuilder category(String category) {
            Ensure.length(500, category);
            return new ItemBuilder(transactionId, itemName, price, quantity, code, category, currencyCode);
        }

        public ItemBuilder currencyCode(String currencyCode) {
            Ensure.length(10, currencyCode);
            return new ItemBuilder(transactionId, itemName, price, quantity, code, category, currencyCode);
        }

        public Item create() {
            return new Item(this);
        }
    }

    public final static class ItemBuilderStart {

        private final String transactionId;

        private ItemBuilderStart(String transactionId) {
            this.transactionId = transactionId;
        }

        public ItemBuilder named(String itemName) {
            Ensure.nonEmpty(itemName);
            Ensure.length(500, itemName);
            return new ItemBuilder(transactionId, itemName);
        }
    }
}
