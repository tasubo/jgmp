package com.github.tasubo.jgmp;

public final class Error implements Sendable {
    private final Parametizer parametizer;

    private Error(String description, Integer fatal) {
        this.parametizer = new Parametizer("exd", description, "exf", fatal);
    }

    @Override
    public String getText() {
        return parametizer.getText();
    }

    public static ErrorBuilder withDescription(String description) {
        Ensure.length(150, description);
        return new ErrorBuilder(description);
    }

    public final static class ErrorBuilder {
        private final String description;
        private final Integer fatal;

        private ErrorBuilder(String description) {
            this.description = description;
            this.fatal = null;
        }

        public ErrorBuilder(String description, Integer fatal) {
            this.description = description;
            this.fatal = fatal;
        }

        public ErrorBuilder fatal() {
            Integer fatal = 1;
            return new ErrorBuilder(description, fatal);
        }

        public Error create() {
            return new Error(description, fatal);
        }
    }
}
