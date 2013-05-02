package com.github.tasubo.jgmp;

public class Timing implements Sendable {
    public static UserTimingBuilder user() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getText() {
        throw new UnsupportedOperationException();
    }

    public static Timing pageLoad(int time) {
        throw new UnsupportedOperationException();
    }

    public static Timing dnsLookup(int time) {
        throw new UnsupportedOperationException();
    }

    public static Timing pageDownload(int time) {
        throw new UnsupportedOperationException();
    }

    public static Timing redirectResponse(int time) {
        throw new UnsupportedOperationException();
    }

    public static Timing tcpConnect(int time) {
        throw new UnsupportedOperationException();
    }

    public static Timing serverResponse(int time) {
        throw new UnsupportedOperationException();
    }

    public Timing and(Timing dnsLookup) {
        throw new UnsupportedOperationException();
    }

    public static class UserTimingBuilder {
        public UserTimingBuilder category(String category) {
            throw new UnsupportedOperationException();
        }

        public UserTimingBuilder name(String lookup) {
            throw new UnsupportedOperationException();
        }

        public UserTimingBuilder label(String label) {
            throw new UnsupportedOperationException();
        }

        public UserTimingBuilder time(int timing) {
            throw new UnsupportedOperationException();
        }

        public UserTiming create() {
            throw new UnsupportedOperationException();
        }
    }
}
