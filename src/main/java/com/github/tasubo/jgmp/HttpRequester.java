package com.github.tasubo.jgmp;

public interface HttpRequester {
    void sendGet(String host, String payload);

    void sendPost(String host, String payload);
}
