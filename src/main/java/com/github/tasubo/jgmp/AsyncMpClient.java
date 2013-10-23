package com.github.tasubo.jgmp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AsyncMpClient {

	private final ExecutorService executorService;

	private final MpClient client;

	public AsyncMpClient(MpClient client, int concurrentRequests) {
		this.client = client;
		this.executorService = Executors.newFixedThreadPool(concurrentRequests);
	}

	public void send(Sendable sendable) {
		SenderJob job = new SenderJob(sendable, client);

		executorService.submit(job);
	}
	
	public void shutdown() {
		
		executorService.shutdownNow();
	}

	static final class SenderJob implements Runnable {

		private static final Logger LOGGER = Logger.getLogger(SenderJob.class.getCanonicalName());

		private final Sendable sendable;

		private MpClient mpClient;

		public SenderJob(Sendable sendable, MpClient mpClient) {
			this.sendable = sendable;
			this.mpClient = mpClient;
		}

		@Override
		public void run() {

			try {

				this.mpClient.send(sendable);
			} catch (Exception e) {

				if (LOGGER.isLoggable(Level.SEVERE)) {
					LOGGER.log(Level.SEVERE, "Caught exception while asynchronously sending request", e);
				}
			}
		}
	}
}
