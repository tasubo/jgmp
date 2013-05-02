package com.github.tasubo.jgmp;

import java.util.HashSet;

class AppendingDecorator implements Decorating {
    private HashSet<Class<? extends Decorating>> decorators = new HashSet<Class<? extends Decorating>>();

    private StringBuilder appending = new StringBuilder();

    @Override
    public Sendable with(Sendable sendable) {
        return new PrefixSendable(appending.toString(), sendable);
    }

    public Decorating append(Decorating decorating) {
        if (!this.decorators.contains(decorating.getClass())) {
            Sendable with = decorating.with(NullSendable.VALUE);
            appending.append(with.getText());
        }
        return this;
    }

    private class PrefixSendable implements Sendable {
        private final String text;

        public PrefixSendable(String appending, Sendable sendable) {
            text = appending + sendable.getText();
        }

        @Override
        public String getText() {
            return text;
        }
    }
}
