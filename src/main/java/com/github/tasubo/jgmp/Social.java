package com.github.tasubo.jgmp;

public class Social implements Sendable {
    @Override
    public String getText() {
        throw new UnsupportedOperationException();
    }

    public static SocialBuilder fromNetwork(String facebook) {
        throw new UnsupportedOperationException();
    }

    public static class SocialBuilder {
        public SocialBuilderAction action(String like) {
            throw new UnsupportedOperationException();
        }

        public class SocialBuilderAction {
            public Social target(String s) {
                throw new UnsupportedOperationException();
            }
        }
    }


}
