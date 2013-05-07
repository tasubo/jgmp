package com.github.tasubo.jgmp;

import java.util.*;

public class Mocks {

    private static final HttpRequester REQUESTER = new MockHttpRequester();
    private static final Deque<String> REQUESTS = new ArrayDeque<String>();

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

    public static String stringWithLength(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append("x");
        }
        return builder.toString();
    }

    public static RequestLog getRequestLog() {
        return new RequestLog();
    }

    public static class MockHttpRequester implements HttpRequester {
        @Override
        public void send(String url) {
            REQUESTS.add(url);
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
    }
}
