package com.github.tasubo.jgmp;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.*;

import static com.github.tasubo.jgmp.Mocks.*;
import static org.junit.Assert.assertThat;

public class CacheBusterTest {

    @Test
    public void shouldAppendDifferentArgsAtTheEnd() {
        MpClient client = MpClient.withTrackingId("TRACKING")
                .withClientId("clientId")
                .withCacheBuster()
                .httpRequester(new MockHttpRequester())
                .create();

        Sendable sendable = prepareSendable();

        client.send(sendable);
        client.send(sendable);

        assertThat(getRequestLog().last(), param("z").isPresent());
        assertThat(getRequestLog().last(), param("z").isLast());
        assertThat(getRequestLog().tenLast(), params("z").allDiffer());
    }

    private ParamsMatcherBuilder params(String params) {
        return new ParamsMatcherBuilder(params);
    }

    public static ParamMatcherBuilder param(String param) {
        return new ParamMatcherBuilder(param);
    }

    public static class ParamMatcherBuilder {

        private final String paramName;

        public ParamMatcherBuilder(String paramName) {
            this.paramName = paramName;
        }

        public Matcher<? super String> isLast() {
            return new BaseMatcher<String>() {
                @Override
                public boolean matches(Object item) {
                    String url = (String) item;
                    String str = "&" + paramName + "=";

                    if (url.indexOf(str) != url.lastIndexOf("&")) {
                        return false;
                    }

                    return true;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText(paramName + " is last");
                }
            };
        }

        public Matcher<? super String> isPresent() {
            return new BaseMatcher<String>() {
                @Override
                public boolean matches(Object item) {
                    String url = (String) item;

                    if (paramName.equals("v")) {
                        return url.indexOf("?v") > -1;
                    }
                    return item.toString().indexOf("&" + paramName) > -1;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("present param: " + paramName);
                }
            };
        }
    }

    public class ParamsMatcherBuilder {

        private final String paramName;

        public ParamsMatcherBuilder(String params) {
            this.paramName = params;
        }

        public Matcher<? super List<String>> allDiffer() {
            return new BaseMatcher<List<String>>() {
                @Override
                public boolean matches(Object item) {
                    List<String> urls = new ArrayList<String>((List<String>) item);
                    Set<String> values = new HashSet<String>();

                    for (String url : urls) {
                        String value = paramValue(paramName, url);
                        values.add(value);
                    }

                    return values.size() == urls.size();
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("All values are different");
                }
            };
        }

        private String paramValue(String paramName, String url) {
            int start = url.indexOf("&" + paramName) + 1 + paramName.length();
            int end = url.indexOf("&", start);

            if (end < 0) {
                end = url.length() - 1;
            }

            return url.substring(start, end);

        }
    }
}