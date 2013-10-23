package com.github.tasubo.jgmp;

import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static com.github.tasubo.jgmp.MpAssert.*;
import static org.junit.Assert.*;
import org.junit.Before;

public class AsyncRequestTest {

	@Before
	public void clearRequestLog() {

		getRequestLog().clear();
	}

	@Test
	public void shouldNotBlockMainThread() throws InterruptedException {

		/*
		 * each http request takes 50 milliseconds
		 */
		AsyncMpClient client = MpClient.withTrackingId("UA-XXXX-Y")
				.withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
				.httpRequester(new SlowMockHttpRequester())
				.createAsyncClient(10);

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < 10; i++) {

			Social social = Social.fromNetwork("facebook")
					.action("like")
					.target("http://foo.com");

			client.send(social);
		}

		long endTime = System.currentTimeMillis();
		long mainThreadDuration = endTime - startTime;

		/*
		 * giving worker threads time to complete so we can check
		 * if requests were performed
		 */
		Thread.sleep(60);

		assertTrue("main thread was blocked for more than 10 milliseconds, but requests should have been executed asynchronously", mainThreadDuration <= 10);
		assertEquals(10, getRequestLog().size());
	}
}
