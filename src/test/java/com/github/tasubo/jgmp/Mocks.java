package com.github.tasubo.jgmp;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class Mocks {

    private static final HttpRequester REQUESTER = new Mocks.MockHttpRequester();
    private static final Deque<String> REQUESTS = new LinkedBlockingDeque<String>();

    public static SystemInfo prepareSystemInfo() {
        return SystemInfo.with().javaEnabled().viewport(800, 600).create();
    }

    public static Campaign prepareCampaign() {
        return Campaign.named("MockCampaign").create();
    }

    public static Sendable prepareSendable() {
        return Event.of("Category", "Label").action("Action");
    }

    public static MpClient prepareMpClient() {
        return MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .httpRequester(REQUESTER)
                .create();
    }

    public static MpClient prepareMpClientWithoutClientID() {
        return MpClient.withTrackingId("UA-XXXX-Y")
                .httpRequester(REQUESTER)
                .create();
    }

    public static String stringWithLength(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append("x");
        }
        return builder.toString();
    }

    public static Mocks.RequestLog getRequestLog() {
        return new Mocks.RequestLog();
    }

    public static class MockHttpRequester implements HttpRequester {
        @Override
        public void sendGet(String host, String payload) {
            String url = host + "?" + payload;
            REQUESTS.add(url);
        }

        @Override
        public void sendPost(String host, String payload) {
            String url = host + "?" + payload;
            REQUESTS.add(url);
        }
    }
	
	public static class SlowMockHttpRequester implements HttpRequester {
		        
		@Override
        public void sendGet(String host, String payload) {
			sleepQuietly(50);
			
            String url = host + "?" + payload;
            REQUESTS.add(url);
        }

        @Override
        public void sendPost(String host, String payload) {
			sleepQuietly(50);
            String url = host + "?" + payload;
            REQUESTS.add(url);
        }

		private void sleepQuietly(long sleepMillis) {
			try {
				Thread.sleep(sleepMillis);
			} catch (InterruptedException ex) {
				
			}
		}
	}

    public static class RequestLog {
        public String last() {
            return REQUESTS.getLast();
        }

        public List<String> tenLast() {
            List<String> requests = new ArrayList<String>();
            Iterator<String> stringIterator = REQUESTS.descendingIterator();
            for (int i = 0; i < 10 && stringIterator.hasNext(); i++) {
                String next = stringIterator.next();
                requests.add(next);
            }

            return requests;
        }

        public void clear() {
            REQUESTS.clear();
        }
		
		public int size() {
			return REQUESTS.size();
		}
    }
}
