package com.github.tasubo.jgmp;

public final class Social implements Sendable {
    private final Parametizer parametizer;

    public Social(String network, String action, String target) {
        parametizer = new Parametizer("t", "social", "sn", network, "sa", action, "st", target);
    }

    @Override
    public String getText() {
        return parametizer.getText();
    }

    public static SocialBuilder fromNetwork(String network) {
        Ensure.length(50, network);
        return new SocialBuilder(network);
    }

    public final static class SocialBuilder {
        private final String network;

        private SocialBuilder(String network) {
            this.network = network;
        }

        public SocialBuilderAction action(String action) {
            Ensure.length(50, action);
            return new SocialBuilderAction(network, action);
        }

        public final class SocialBuilderAction {
            private final String network;
            private final String action;

            private SocialBuilderAction(String network, String action) {
                this.network = network;
                this.action = action;
            }

            public Social target(String target) {
                Ensure.length(2048, target);
                return new Social(network, action, target);
            }
        }
    }


}
