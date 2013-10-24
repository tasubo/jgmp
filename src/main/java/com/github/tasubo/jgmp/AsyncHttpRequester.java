package com.github.tasubo.jgmp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An asynchronous implementation of HttpRequester. Needs another HttpRequester
 * to work
 */
public final class AsyncHttpRequester implements HttpRequester {

	private final ExecutorService executorService;

	private final HttpRequester synchronousHttpRequester;

	public AsyncHttpRequester(HttpRequester synchronousHttpRequester, int concurrentRequests) {
		this.synchronousHttpRequester = synchronousHttpRequester;
		this.executorService = Executors.newFixedThreadPool(concurrentRequests);
	}
	
	public void shutdown() {
		
		executorService.shutdownNow();
	}

	@Override
	public void sendGet(final String host, final String payload) {
		
		SenderJob senderJob = new SenderJob(new JobAction() {

			@Override
			public void sendRequest() {
				synchronousHttpRequester.sendGet(host, payload);
			}
		});
		
		executorService.submit(senderJob);
	}

	@Override
	public void sendPost(final String host, final String payload) {
		
		SenderJob senderJob = new SenderJob(new JobAction() {

			@Override
			public void sendRequest() {
				synchronousHttpRequester.sendPost(host, payload);
			}
		});
		
		executorService.submit(senderJob);
	}

	private static final class SenderJob implements Runnable {

		private static final Logger LOGGER = Logger.getLogger(SenderJob.class.getCanonicalName());

		private JobAction jobAction;
		
		public SenderJob(JobAction jobAction) {
			this.jobAction = jobAction;
		}
		
		
		@Override
		public void run() {

			try {
				
				jobAction.sendRequest();
			} catch (Exception e) {

				if (LOGGER.isLoggable(Level.SEVERE)) {
					LOGGER.log(Level.SEVERE, "Caught exception while asynchronously sending request", e);
				}
			}
		}
		
	}
	
	private interface JobAction {
		
		void sendRequest();
	}
}
