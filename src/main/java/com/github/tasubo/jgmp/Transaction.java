package com.github.tasubo.jgmp;

import java.math.BigDecimal;

public final class Transaction implements Sendable {
    private final Parametizer parametizer;

    private Transaction(TransactionBuilder b) {
        this.parametizer = new Parametizer("t", "transaction",
                "ti", b.transactionId,
                "ta", b.affiliation,
                "tr", b.revenue,
                "ts", b.shipping,
                "tt", b.tax,
                "cu", b.currencyCode);
    }

    @Override
    public String getText() {
        return parametizer.getText();
    }

    public static TransactionBuilder withId(String transactionId) {
        Ensure.length(500, transactionId);
        Ensure.nonEmpty(transactionId);
        return new TransactionBuilder(transactionId);
    }

    public final static class TransactionBuilder {
        private final String transactionId;
        private final String affiliation;
        private final BigDecimal revenue;
        private final BigDecimal shipping;
        private final BigDecimal tax;
        private final String currencyCode;

        private TransactionBuilder(String transactionId) {
            this.transactionId = transactionId;
            this.affiliation = null;
            this.revenue = null;
            this.shipping = null;
            this.tax = null;
            this.currencyCode = null;
        }

        private TransactionBuilder(String transactionId, String affiliation, BigDecimal revenue,
                                   BigDecimal shipping, BigDecimal tax, String currencyCode) {
            this.transactionId = transactionId;
            this.affiliation = affiliation;
            this.revenue = revenue;
            this.shipping = shipping;
            this.tax = tax;
            this.currencyCode = currencyCode;
        }

        public TransactionBuilder affiliation(String affiliation) {
            Ensure.length(500, affiliation);
            return new TransactionBuilder(transactionId, affiliation, revenue, shipping, tax, currencyCode);
        }

        public TransactionBuilder revenue(BigDecimal revenue) {
            return new TransactionBuilder(transactionId, affiliation, revenue, shipping, tax, currencyCode);
        }

        public TransactionBuilder shipping(BigDecimal shipping) {
            return new TransactionBuilder(transactionId, affiliation, revenue, shipping, tax, currencyCode);
        }

        public TransactionBuilder tax(BigDecimal tax) {
            return new TransactionBuilder(transactionId, affiliation, revenue, shipping, tax, currencyCode);
        }

        public TransactionBuilder currencyCode(String currencyCode) {
            Ensure.length(10, currencyCode);
            return new TransactionBuilder(transactionId, affiliation, revenue, shipping, tax, currencyCode);
        }

        public Transaction create() {
            return new Transaction(this);
        }
    }
}
