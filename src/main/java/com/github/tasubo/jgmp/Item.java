package com.github.tasubo.jgmp;

import java.math.BigDecimal;

public class Item implements Sendable {
    private Item() {

    }

    @Override
    public String getText() {
        throw new UnsupportedOperationException();
    }

    public static ItemBuilderStart forTransaction(String od564) {
        throw new UnsupportedOperationException();
    }

    public static class ItemBuilder {
        private ItemBuilder() {
        }

        public ItemBuilder priced(BigDecimal bigDecimal) {
            throw new UnsupportedOperationException();
        }

        public ItemBuilder quantity(int i) {
            throw new UnsupportedOperationException();
        }

        public ItemBuilder code(String sku) {
            throw new UnsupportedOperationException();
        }

        public ItemBuilder category(String blue) {
            throw new UnsupportedOperationException();
        }

        public ItemBuilder currencyCode(String eur) {
            throw new UnsupportedOperationException();
        }

        public Item create() {
            throw new UnsupportedOperationException();
        }
    }

    public static class ItemBuilderStart {
        private ItemBuilderStart() {

        }

        public ItemBuilder named(String shoe) {
            throw new UnsupportedOperationException();
        }
    }
}
