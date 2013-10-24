package com.github.tasubo.jgmp;

public interface HttpRequester {
	
	/*
	 * method call can be blocking or non-blocking, depending on implementation
	 */
    void sendGet(String host, String payload);

	/*
	 * method call can be blocking or non-blocking, depending on implementation
	 */
    void sendPost(String host, String payload);
}
