package com.github.tasubo.jgmp;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class MpAssert {
    public static HasParamMatcher hasParam(String v) {
        return new HasParamMatcher(v);
    }

    static class HasParamMatcher extends BaseMatcher<String> {

        private final String param;

        public HasParamMatcher(String value) {
            this.param = value;
        }

        @Override
        public boolean matches(Object item) {
            if (param.equals("v")) {
                return item.toString().indexOf("?v") > -1;
            }
            return item.toString().indexOf("&" + param) > -1;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("present param: " + param);
        }

        public Matcher<? super String> withValue(String s) {
            return new HasParamValueMatcher(param, s);
        }

        public Matcher<? super String> withBareValue(String value) {
            return new HasParamValueMatcher(param, value, true);
        }
    }

    static class HasParamValueMatcher extends BaseMatcher<String> {

        private final String param;
        private final String value;
        private final boolean bare;

        public HasParamValueMatcher(String param, String value) {
            this.param = param;
            this.value = value;
            this.bare = false;
        }

        public HasParamValueMatcher(String param, String value, boolean bare) {
            this.param = param;
            this.value = value;
            this.bare = bare;
        }

        @Override
        public boolean matches(Object item) {

            String paramSearch = "&" + param;

            if (param.equals("v")) {
                paramSearch = "?v";
            }

            if (item.toString().indexOf(paramSearch) == -1) {
                return false;
            }


            String encoded = value;

            if (!bare) {
                encoded = URLParamEncoder.encode(value);
            }

            if (item.toString().indexOf(paramSearch + "=" + encoded) == -1) {
                return false;
            }


            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Havent found param: " + param + ", with value: " + value);
        }
    }
}
