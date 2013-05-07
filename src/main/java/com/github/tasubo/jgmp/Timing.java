package com.github.tasubo.jgmp;

public final class Timing implements Sendable {
    private final Parametizer parametizer;

    public Timing(Timing firstTiming, Timing secondTiming) {
        parametizer = firstTiming.parametizer.and(secondTiming.parametizer, "t");
    }

    public static UserTimingBuilder user() {
        return new UserTimingBuilder();
    }

    private Timing(String label, int time) {
        this.parametizer = new Parametizer("t", "timing", label, time);
    }

    @Override
    public String getText() {
        return parametizer.getText();
    }

    public static Timing pageLoad(int time) {
        return new Timing("plt", time);
    }

    public static Timing dnsLookup(int time) {
        return new Timing("dns", time);
    }

    public static Timing pageDownload(int time) {
        return new Timing("pdt", time);
    }

    public static Timing redirectResponse(int time) {
        return new Timing("rrt", time);
    }

    public static Timing tcpConnect(int time) {
        return new Timing("tcp", time);
    }

    public static Timing serverResponse(int time) {
        return new Timing("srt", time);
    }

    public Timing and(Timing timing) {
        return new Timing(this, timing);
    }

    public final static class UserTimingBuilder {
        private final String category;
        private final String variableName;
        private final String label;
        private final Integer timing;

        private UserTimingBuilder(String category, String variableName, String label, Integer timing) {
            this.category = category;
            this.variableName = variableName;
            this.label = label;
            this.timing = timing;
        }

        private UserTimingBuilder() {
            this.category = null;
            this.variableName = null;
            this.label = null;
            this.timing = null;
        }

        public UserTimingBuilder category(String category) {
            Ensure.length(150, category);
            return new UserTimingBuilder(category, variableName, label, timing);
        }

        public UserTimingBuilder name(String variableName) {
            Ensure.length(500, variableName);
            return new UserTimingBuilder(category, variableName, label, timing);
        }

        public UserTimingBuilder label(String label) {
            Ensure.length(500, label);
            return new UserTimingBuilder(category, variableName, label, timing);
        }

        public UserTimingBuilder time(int timing) {
            return new UserTimingBuilder(category, variableName, label, timing);
        }

        public UserTiming create() {
            Parametizer parametizer = new Parametizer("t", "timing", "utc", category, "utv", variableName, "utt", timing, "utl", label);
            return new UserTiming(parametizer);
        }
    }
}
