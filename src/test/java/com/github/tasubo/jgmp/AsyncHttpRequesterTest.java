package com.github.tasubo.jgmp;

import org.junit.Test;

import static com.github.tasubo.jgmp.Mocks.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class AsyncHttpRequesterTest {

	@Mock
	private HttpRequester httpRequesterMock;
	
	@Before
	public void setUpMock() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void clearRequestLog() {
		getRequestLog().clear();
	}

	@Test
	public void shouldNotBlockMainThread() throws InterruptedException {
		
		/*
		 * each http request takes 50 milliseconds
		 */
		final SlowMockHttpRequester slowMockHttpRequester = new SlowMockHttpRequester();
		
		final AsyncHttpRequester asyncHttpRequester = new AsyncHttpRequester(slowMockHttpRequester, 10);

		MpClient client = MpClient.withTrackingId("UA-XXXX-Y")
				.withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
				.httpRequester(asyncHttpRequester)
				.create();

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
		Thread.sleep(70);

		assertTrue("main thread was blocked for more than 10*10 milliseconds, but requests should have been executed asynchronously", mainThreadDuration <= 20);
		assertEquals(10, getRequestLog().size());
	}
	
	@Test
	public void shouldCallWrappedSendGet() throws InterruptedException {
		
		final AsyncHttpRequester asyncHttpRequester = new AsyncHttpRequester(httpRequesterMock, 10);
		asyncHttpRequester.sendGet("test", "test1");
		
		/*
		 * giving worker threads time to complete so we can check
		 * if requests were performed
		 */
		Thread.sleep(60);
		
		verify(httpRequesterMock).sendGet(eq("test"), eq("test1"));
		verifyNoMoreInteractions(httpRequesterMock);
	}
	
	@Test
	public void shouldCallWrappedSendPost() throws InterruptedException {
		
		final AsyncHttpRequester asyncHttpRequester = new AsyncHttpRequester(httpRequesterMock, 10);
		asyncHttpRequester.sendPost("test", "test1");
		
		/*
		 * giving worker threads time to complete so we can check
		 * if requests were performed
		 */
		Thread.sleep(60);
		
		verify(httpRequesterMock).sendPost(eq("test"), eq("test1"));
		verifyNoMoreInteractions(httpRequesterMock);
	}
}
