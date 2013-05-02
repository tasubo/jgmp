package com.github.tasubo.jgmp;

public class Error implements Sendable {
    @Override
    public String getText() {
        throw new UnsupportedOperationException();
    }

    public static ErrorBuilder withDescription(String description) {
        throw new UnsupportedOperationException();
    }

    public static class ErrorBuilder {
        public ErrorBuilder fatal() {
            throw new UnsupportedOperationException();
        }

        public Error create() {
            throw new UnsupportedOperationException();
        }
    }
}
