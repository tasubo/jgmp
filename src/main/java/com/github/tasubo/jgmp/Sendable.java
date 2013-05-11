package com.github.tasubo.jgmp;

public interface Sendable {
    String getText();

    Sendable with(Decorating app);
}
