package com.github.tasubo.jgmp;

public final class Event implements Sendable {
    private final String category;
    private final String label;
    private final String action;
    private final Integer value;
    private final Parametizer parametizer;

	
	public static EventBuilderStart ofCategory(String category) {
		
        Ensure.length(150, category);
		return new EventBuilderStart(category);
	}

	private Event(EventBuilder eventBuilder) {
		
		this.action = eventBuilder.action;
		this.category = eventBuilder.category;
		this.label = eventBuilder.label;
		this.value = eventBuilder.value;
		
        parametizer = new Parametizer("t", "event", "ec", this.category, "ea", this.action, "el", this.label, "ev", this.value);
	}

    @Override
    public String getText() {
        return parametizer.getText();
    }

    @Override
    public Sendable with(Decorating app) {
        return new Combine(this).with(app);
    }

	public static class EventBuilderStart {
		
		private final String category;
		
		private EventBuilderStart(String category) {
			this.category = category;
		}
		
		public EventBuilder withAction(String action) {
			Ensure.length(500, action);
			return new EventBuilder(category, action, null, null);
		}
	}

    public static class EventBuilder {
        private final String category;
        private final String label;
		private final Integer value;
		private final String action;

        private EventBuilder(String category, String action, String label, Integer value) {
            this.category = category;
            this.label = label;
			this.value = value;
			this.action = action;
        }

		public EventBuilder withValue(Integer value) {
			return new EventBuilder(category, action, label, value);
		}
		
		public EventBuilder labeled(String label) {
			Ensure.length(500, label);
			return new EventBuilder(category, action, label, value);
		}

		public Event create() {
			return new Event(this);
		}
	}
}
