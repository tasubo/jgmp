package com.github.tasubo.jgmp;

public final class Event implements Sendable {
    private final String category;
    private final String label;
    private final String action;
    private final Integer value;
    private final Parametizer parametizer;

    private Event(String category, String label, String action) {
        this.action = action;
        this.label = label;
        this.category = category;
        this.value = null;
        parametizer = new Parametizer("t", "event", "ec", this.category, "ea", this.action, "el", this.label, "ev", value);
    }

    private Event(String category, String label, String action, int value) {
        this.category = category;
        this.label = label;
        this.action = action;
        this.value = value;
        parametizer = new Parametizer("t", "event", "ec", this.category, "ea", this.action, "el", this.label, "ev", this.value);
    }

    public static EventBuilder of(String category, String label) {
        Ensure.length(150, category);
        Ensure.length(500, label);
        return new EventBuilder(category, label);
    }

    @Override
    public String getText() {
        return parametizer.getText();
    }


    public static class EventBuilder {
        private final String category;
        private final String label;

        private EventBuilder(String category, String label) {
            this.category = category;
            this.label = label;
        }

        public Event action(String action) {
            Ensure.length(500, action);
            return new Event(category, label, action);
        }

        public Event action(String action, int value) {
            return new Event(category, label, action, value);
        }
    }
}
