package com.github.tasubo.jgmp;

final class Combine {
    private final String text;

    public Combine(Sendable sendable) {
        text = sendable.getText();
    }

    public Sendable with(Decorating decorating) {
        return new InnerSendable(decorating.getPart());
    }

    class InnerSendable implements Sendable {
        private final String string;

        InnerSendable(String string) {
            this.string = string;
        }

        @Override
        public String getText() {
            Parametizer and = new Parametizer(true, text).and(new Parametizer(true, string));
            return and.getText();
        }

        @Override
        public Sendable with(Decorating app) {
            return new Combine(this).with(app);
        }
    }
}