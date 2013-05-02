package com.github.tasubo.jgmp;

public interface Decorating {
    Sendable with(Sendable sendable);
}
