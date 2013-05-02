package com.github.tasubo.jgmp;

import java.math.BigDecimal;

public class Transaction implements Sendable {
    @Override
    public String getText() {
        throw new UnsupportedOperationException();
    }

    public static TransactionBuilder withId(String transactionId) {
        throw new UnsupportedOperationException();
    }

    public static class TransactionBuilder {
        public TransactionBuilder affiliation(String affiliation) {
            throw new UnsupportedOperationException();
        }

        public TransactionBuilder revenue(BigDecimal revenue) {
            throw new UnsupportedOperationException();
        }

        public TransactionBuilder shipping(BigDecimal shipping) {
            throw new UnsupportedOperationException();
        }

        public TransactionBuilder tax(BigDecimal tax) {
            throw new UnsupportedOperationException();
        }

        public Transaction create() {
            throw new UnsupportedOperationException();
        }

        public TransactionBuilder currencyCode(String currencyCode) {
            throw new UnsupportedOperationException();
        }
    }
}
