package com.github.tasubo.jgmp;

public class JavaConnectionRequester implements HttpRequester {
    @Override
    public void send(String url) {
        System.out.println("Request made to: " + url);
    }
}
