package com.github.tasubo.jgmp;

class CombinedSendable {
    private final Parametizer parametizer;

    public CombinedSendable(Object... params) {
        parametizer = new Parametizer(params);
    }

    public CombinedSendable(Parametizer parametizer) {
        this.parametizer = parametizer;
    }

    public Sendable with(Sendable sendable) {
        return new InnerSendable(sendable.getText());
    }

    class InnerSendable implements Sendable {
        private final String string;

        InnerSendable(String string) {
            this.string = string;
        }

        @Override
        public String getText() {
            return parametizer.getText() + string;
        }
    }
}